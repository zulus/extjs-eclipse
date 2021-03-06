/*******************************************************************************
 * Copyright (c) 2013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.internal.core.project;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.IExtJSLibraryManager;
import net.w3des.extjs.core.Utils;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.api.ProjectType;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

/**
 * Implementation of the extjs project
 * 
 * @author mepeisen
 */
public class ProjectImpl implements IExtJSProject, IExtJSIndex {
	
	private IExtJSIndex index;
	
	private Properties projectProps;

	private boolean saving;
	
	public static IPath PROPS_PATH = new Path(".settings/net.w3des.extjs.core.props");
	
	private static final String KEY_ENVNAME = "env.name";
	
	private static final String KEY_LIBS = "libraries";
	
	private static final String KEY_EXTJS_NAMESPACE = "class.namespace";
	
	private static final String KEY_CLASS_FOLDER = "class.folder";

	/**
	 * @param index
	 */
	public ProjectImpl(IExtJSIndex index) {
		this.index = index;
	}

	@Override
	public IExtJSIndex getIndex() {
		return this.index;
	}

	@Override
	public IProject getProject() {
		return this.index.getProject();
	}
	
	private void initProps() throws CoreException {
		if (this.projectProps == null) {
			final IFile file = this.getProject().getFile(PROPS_PATH);
			this.projectProps = new Properties();
			if (file.exists()) {
				try {
					this.projectProps.load(file.getContents());
				}
				catch (IOException ex) {
					// failed loading props
					this.projectProps = null;
					throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Problems reading extjs project properties", ex));
				}
			}
			
			// set defaults if needed
			if (!this.projectProps.containsKey(KEY_ENVNAME)) {
				this.projectProps.setProperty(KEY_ENVNAME, ExtJSCore.getLibraryManager().getDefaultEnvName(this.getVersion())); 
			}
			if (!this.projectProps.contains(KEY_LIBS)) {
				this.projectProps.setProperty(KEY_LIBS, "");
			}
		}
	}
	
	private void save() throws CoreException {
		if (this.projectProps == null) return;
		
		this.saving = true;
		try {
			final IFile file = this.getProject().getFile(PROPS_PATH);
			if (!file.getParent().exists()) {
				((IFolder)file.getParent()).create(true, true, new NullProgressMonitor());
			}
			final StringWriter sw = new StringWriter();
			try {
				this.projectProps.store(sw, "");
				sw.flush();
				if (file.exists()) {
					file.setContents(new ByteArrayInputStream(sw.toString().getBytes()), true, false, new NullProgressMonitor());
				} else {
					file.create(new ByteArrayInputStream(sw.toString().getBytes()), true, new NullProgressMonitor());
				}
			} catch (IOException ex) {
				throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Problems writing extjs project properties", ex));
			}
		}
		finally {
			this.saving = false;
		}
	}

	@Override
	public String getEnvironmentName() throws CoreException {
		this.initProps();
		return this.projectProps.getProperty(KEY_ENVNAME);
	}
	
	public void refreshLibContainer() throws CoreException {
        final IJavaScriptProject jsProject = JavaScriptCore.create(this.getProject());
        
        final String envName = this.getEnvironmentName() == null ? ExtJSCore.getLibraryManager().getDefaultEnvName(this.getVersion()) : this.getEnvironmentName();
        this.removeCPC(jsProject, new Path(ExtJSCore.JSCPC_ENV_ID), new NullProgressMonitor());
        this.addCPC(jsProject, new Path(ExtJSCore.JSCPC_ENV_ID).append(envName), new NullProgressMonitor());
        
        this.removeCPC(jsProject, new Path(ExtJSCore.JSCPC_LIB_ID), new NullProgressMonitor());
        for (final String name : this.getLibraryNames()) {
        	this.addCPC(jsProject, new Path(ExtJSCore.JSCPC_LIB_ID).append(name), new NullProgressMonitor());
        }
	}

	private void addCPC(IJavaScriptProject jsProject, IPath path, IProgressMonitor monitor) throws JavaScriptModelException {
		final List<IIncludePathEntry> entries = new ArrayList<IIncludePathEntry>(Arrays.asList(jsProject.getRawIncludepath()));
		for (final IIncludePathEntry entry : entries) {
			if (entry.getEntryKind() == IIncludePathEntry.CPE_CONTAINER && path.equals(entry.getPath())) {
				return;
			}
		}
		entries.add(JavaScriptCore.newContainerEntry(path));
		jsProject.setRawIncludepath(entries.toArray(new IIncludePathEntry[entries.size()]), monitor);
	}

	private void removeCPC(IJavaScriptProject jsProject, IPath path, IProgressMonitor monitor) throws JavaScriptModelException {
		final List<IIncludePathEntry> entries = new ArrayList<IIncludePathEntry>(Arrays.asList(jsProject.getRawIncludepath()));
		for (final IIncludePathEntry entry : entries) {
			if (entry.getEntryKind() == IIncludePathEntry.CPE_CONTAINER && path.isPrefixOf(entry.getPath())) {
				entries.remove(entry);
				jsProject.setRawIncludepath(entries.toArray(new IIncludePathEntry[entries.size()]), monitor);
				return;
			}
		}
	}

	@Override
	public void setEnvironmentName(String name) throws CoreException {
		if (name == null || name.length() == 0) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		this.initProps();
		this.projectProps.setProperty(KEY_ENVNAME, name);
		this.save();
		this.refreshLibContainer();
	}

	@Override
	public String[] getLibraryNames() throws CoreException {
		this.initProps();
		final String libs = this.projectProps.getProperty(KEY_LIBS);
		if (libs == null || libs.length() == 0) return new String[0];
		return libs.split(":");
	}
	
	private String join(String[] list, String glue) {
		final StringBuilder builder = new StringBuilder();
		for (final String e : list) {
			if (builder.length() > 0) builder.append(glue);
			builder.append(e);
		}
		return builder.toString();
	}

	@Override
	public void addLibrary(String name) throws CoreException {
		if (name == null || name.length() == 0) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		final List<String> libs = new ArrayList<String>(Arrays.asList(this.getLibraryNames()));
		if (libs.contains(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "project already contains given library"));
		libs.add(name);
		this.projectProps.setProperty(KEY_LIBS, this.join(libs.toArray(new String[libs.size()]), ":"));
		this.save();
		this.refreshLibContainer();
	}

	@Override
	public void removeLibrary(String name) throws CoreException {
		if (name == null || name.length() == 0) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		final List<String> libs = new ArrayList<String>(Arrays.asList(this.getLibraryNames()));
		if (!libs.contains(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "project does not contain given library"));
		libs.remove(name);
		this.projectProps.setProperty(KEY_LIBS, this.join(libs.toArray(new String[libs.size()]), ":"));
		this.save();
		this.refreshLibContainer();
	}

	@Override
	public void setLibraries(String[] libraries) throws CoreException {
		if (libraries == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid names"));
		for (final String name : libraries) if (name == null || name.length() == 0) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		this.initProps();
		this.projectProps.setProperty(KEY_LIBS, this.join(libraries, ":"));
		this.save();
		this.refreshLibContainer();
	}

	public void updateIndex(IExtJSIndex newIndex) {
		this.index = newIndex;
	}

	public void readProperties() {
		// we ignore property changes during saving
		if (!this.saving) {
			this.projectProps = null;
			// will re-read the properties during next invocation of initProps because it is null
		}
	}

	@Override
	public IProjectFacetVersion getVersion() throws CoreException {
		final IFacetedProject fProject = ProjectFacetsManager.create(this.getProject());
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (fProject != null && facet != null) {
			return fProject.getInstalledVersion(facet);
		}
		return null;
	}

	@Override
	public IProjectFacetVersion getTouchVersion() throws CoreException {
		final IFacetedProject fProject = ProjectFacetsManager.create(this.getProject());
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		if (fProject != null && facet != null) {
			return fProject.getInstalledVersion(facet);
		}
		return null;
	}

	@Override
	public void setVersion(IProjectFacetVersion version) throws CoreException {
		final IFacetedProject fProject = ProjectFacetsManager.create(this.getProject());
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		if (fProject == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid project"));
		if (!version.getProjectFacet().equals(facet1) && !version.getProjectFacet().equals(facet2))
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid project facet version"));
		fProject.installProjectFacet(version, null, new NullProgressMonitor());
	}

	@Override
	public boolean isOfType(ProjectType type) throws CoreException {
		final IFacetedProject fProject = ProjectFacetsManager.create(this.getProject());
		final IProjectFacet facetExt = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facetTouch = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		switch (type)
		{
		case ExtJsAndSenchaTouch:
			return fProject.hasProjectFacet(facetExt) && fProject.hasProjectFacet(facetTouch);
		case SenchaExtJS:
			return fProject.hasProjectFacet(facetExt);
		case SenchaTouch:
			return fProject.hasProjectFacet(facetTouch);
		}
		return false;
	}

	@Override
	public Collection<IExtJSFile> getFiles() {
		final List<IExtJSFile> result = new ArrayList<IExtJSFile>();
		result.addAll(this.getIndex().getFiles());
		for (final IExtJSLibrary lib : this.getLibraries()) {
			final IExtJSIndex index = lib.getIndex();
			if (index != null) {
				result.addAll(index.getFiles());
			}
		}
		return result;
	}

	@Override
	public void clearIndex() {
		this.getIndex().clearIndex();
		for (final IExtJSLibrary lib : this.getLibraries()) {
			final IExtJSIndex index = lib.getIndex();
			if (index != null) {
				index.clearIndex();
			}
		}
	}

	@Override
	public IExtJSLibrary[] getLibraries() {
		final IExtJSLibraryManager manager = ExtJSCore.getLibraryManager();
		final List<IExtJSLibrary> result = new ArrayList<IExtJSLibrary>();
		try {
			// env
			String envName = this.getEnvironmentName();
			if (envName == null) {
				final IProjectFacetVersion extjsVersion = getVersion();
				final IProjectFacetVersion touchVersion = getTouchVersion();
				if (extjsVersion != null) {
					envName = manager.getDefaultEnvName(extjsVersion);
				}
				else if (touchVersion != null) {
					envName = manager.getDefaultEnvName(touchVersion);
				}
			}
			if (envName != null) {
				final IExtJSEnvironment env = manager.getEnv(envName);
				if (env != null) {
					if (env.getCore() == null) {
						// fall back to default builtin lib
						for (final IProjectFacetVersion ver : env.getCompatibleVersions(ExtJSNature.getExtjsFacet())) {
							final IExtJSCoreLibrary lib = manager.getDefaultCoreLib(ver);
							if (lib != null) {
								result.add(lib);
								break;
							}
						}
						for (final IProjectFacetVersion ver : env.getCompatibleVersions(ExtJSNature.getSenchaTouchFacet())) {
							final IExtJSCoreLibrary lib = manager.getDefaultCoreLib(ver);
							if (lib != null) {
								result.add(lib);
								break;
							}
						}
					}
					else {
						result.add(env.getCore());
					}
				}
			}
			
			for (final String libName : this.getLibraryNames()) {
				final IExtJSLibrary lib = manager.getLibrary(libName);
				if (lib != null) {
					result.add(lib);
				}
			}
		}
		catch (CoreException ex) {
			ExtJSCore.error(ex);
		}
		return result.toArray(new IExtJSLibrary[result.size()]);
	}

	@Override
	public IExtJSIndex getCumulatedIndex() {
		return this;
	}

	@Override
	public String getExtjsNamespace() throws CoreException {
		this.initProps();
		return this.projectProps.getProperty(KEY_EXTJS_NAMESPACE);
	}

	@Override
	public void setExtjsNamespace(String namespace) throws CoreException {
		this.initProps();
		if (namespace == null || !Utils.PATTERN_JAVASCRIPT_NAMESPACE.matcher(namespace).matches()) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid namespace"));
		}
		this.projectProps.put(KEY_EXTJS_NAMESPACE, namespace);
		this.save();
	}

	@Override
	public IContainer getSourceFolder() throws CoreException {
		this.initProps();
		final String folder = this.projectProps.getProperty(KEY_CLASS_FOLDER);
		if (folder != null) {
			if (folder.length() == 0) {
				return this.index.getProject();
			}
			return this.index.getProject().getFolder(folder);
		}
		return null;
	}

	@Override
	public void setSourceFolder(IContainer folder) throws CoreException {
		this.initProps();
		if (!folder.getProject().equals(this.getIndex().getProject())) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid folder"));
		}
		this.projectProps.put(KEY_CLASS_FOLDER, folder.getProjectRelativePath().toString());
		this.save();
	}

}
