/**
 * 
 */
package net.w3des.extjs.internal.core.project;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * Implementation of the extjs project
 * 
 * @author mepeisen
 */
public class ProjectImpl implements IExtJSProject {
	
	private IExtJSIndex index;
	
	private Properties projectProps;

	private boolean saving;
	
	public static IPath PROPS_PATH = new Path(".settings/net.w3des.extjs.core.props");
	
	private static final String KEY_ENVNAME = "env.name";
	
	private static final String KEY_LIBS = "libraries";

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
					file.create(new ByteArrayInputStream(sw.toString().getBytes()), true, new NullProgressMonitor());
				} else {
					file.setContents(new ByteArrayInputStream(sw.toString().getBytes()), true, false, new NullProgressMonitor());
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
	
	private void refreshLibContainer() {
		// TODO
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
		final List<String> libs = Arrays.asList(this.getLibraryNames());
		if (libs.contains(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "project already contains given library"));
		libs.add(name);
		this.projectProps.setProperty(KEY_LIBS, this.join(libs.toArray(new String[libs.size()]), ":"));
		this.save();
		this.refreshLibContainer();
	}

	@Override
	public void removeLibrary(String name) throws CoreException {
		if (name == null || name.length() == 0) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		final List<String> libs = Arrays.asList(this.getLibraryNames());
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
	public void setVersion(IProjectFacetVersion version) throws CoreException {
		final IFacetedProject fProject = ProjectFacetsManager.create(this.getProject());
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (fProject != null && facet != null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid project"));
		if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid project facet version"));
		fProject.installProjectFacet(version, null, new NullProgressMonitor());
	}

}
