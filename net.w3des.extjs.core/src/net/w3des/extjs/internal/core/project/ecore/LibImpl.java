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
package net.w3des.extjs.internal.core.project.ecore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.core.model.basic.ExtJSFactory;
import net.w3des.extjs.core.model.basic.File;
import net.w3des.extjs.core.model.basic.Library;
import net.w3des.extjs.core.model.basic.LibrarySource;
import net.w3des.extjs.core.model.basic.LibrarySourceType;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class LibImpl implements IExtJSLibrary, IExtJSIndex {

	private Library lib;
	private ECoreLibStorageImpl storage;

	public LibImpl(ECoreLibStorageImpl storage, Library lib) {
		this.lib = lib;
		this.storage = storage;
	}

	@Override
	public IExtJSIndex getIndex() {
		return this;
	}

	@Override
	public String getName() {
		return this.lib.getName();
	}

	@Override
	public boolean isBuiltin() {
		return this.lib.isBuiltin();
	}

	@Override
	public void setName(String name) throws CoreException {
		if (name == null || name.length() == 0 || name.startsWith("core-")) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		if (this.isBuiltin()) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change name of builtin libraries"));
		if (this.storage.hasLib(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Duplicate name"));
		final String oldName = this.lib.getName();
		this.lib.setName(name);
		this.storage.notifyNameChange(this.lib, oldName, name);
	}

	@Override
	public String[] getCompatibleVersionNames(IProjectFacet facet) {
		if (facet.equals(ExtJSNature.getExtjsFacet())) {
			return this.lib.getVersions().toArray(new String[this.lib.getVersions().size()]);
		}
		if (facet.equals(ExtJSNature.getExtjsFacet())) {
			return this.lib.getSenchaTouchVersions().toArray(new String[this.lib.getSenchaTouchVersions().size()]);
		}
		return new String[0];
	}

	@Override
	public IProjectFacetVersion[] getCompatibleVersions(IProjectFacet facet) throws CoreException {
		final List<IProjectFacetVersion> result = new ArrayList<IProjectFacetVersion>();
		for (final String version : this.getCompatibleVersionNames(facet)) {
			final IProjectFacetVersion v = facet.getVersion(version);
			if (v == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
			result.add(v);
		}
		return result.toArray(new IProjectFacetVersion[result.size()]);
	}
	
	private void refreshLibContainer() throws CoreException {
		// TODO refresh only the projects that are affected
		for (final IExtJSProject project : ExtJSCore.getProjectManager().getProjects()) {
			project.refreshLibContainer();
		}
	}
	
	@Override
	public void addCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		if (!facet1.equals(version.getProjectFacet()) && !facet2.equals(version.getProjectFacet())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions(version.getProjectFacet()));
		if (versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version already added"));
		if (facet1.equals(version.getProjectFacet())) {
			this.lib.getVersions().add(version.getVersionString());
		}
		else if (facet2.equals(version.getProjectFacet())) {
			this.lib.getSenchaTouchVersions().add(version.getVersionString());
		}
		this.refreshLibContainer();
	}

	@Override
	public void removeCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (!facet1.equals(version.getProjectFacet()) && !facet2.equals(version.getProjectFacet())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions(version.getProjectFacet()));
		if (!versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version not in list"));
		if (facet1.equals(version.getProjectFacet())) {
			this.lib.getVersions().remove(version.getVersionString());
		}
		else if (facet2.equals(version.getProjectFacet())) {
			this.lib.getSenchaTouchVersions().remove(version.getVersionString());
		}
		this.refreshLibContainer();
	}

	@Override
	public void setCompatibleVersions(IProjectFacet facet, IProjectFacetVersion[] versions)
			throws CoreException {
		if (versions == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		for (final IProjectFacetVersion version : versions) if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		if (facet.equals(ExtJSNature.getExtjsFacet())) {
			this.lib.getVersions().clear();
			for (final IProjectFacetVersion version : versions) this.lib.getVersions().add(version.getVersionString());
		}
		else if (facet.equals(ExtJSNature.getSenchaTouchFacet())) {
			this.lib.getSenchaTouchVersions().clear();
			for (final IProjectFacetVersion version : versions) this.lib.getSenchaTouchVersions().add(version.getVersionString());
		}
		this.refreshLibContainer();
	}

	@Override
	public ILibrarySource[] getSources() {
		final List<ILibrarySource> result = new ArrayList<ILibrarySource>();
		for (final LibrarySource source : this.lib.getSources()) {
			switch (source.getType()) {
			case FOLDER:
				result.add(new FolderImpl(source));
				break;
			case JAVASCRIPT_FILE:
				result.add(new FileImpl(source));
				break;
			case ZIP_FILE:
				result.add(new ZipImpl(source));
				break;
			}
		}
		return result.toArray(new ILibrarySource[result.size()]);
	}

	@Override
	public void removeAllSources() throws CoreException {
		this.lib.getSources().clear();
		this.refreshLibContainer();
	}

	@Override
	public void removeSource(ILibrarySource source) throws CoreException {
		LibrarySource ls = null;
		if (source instanceof FolderImpl) {
			ls = ((FolderImpl)source).getSource();
		}
		else if (source instanceof FileImpl) {
			ls = ((FileImpl) source).getSource();
		}
		else if (source instanceof ZipImpl) {
			ls = ((ZipImpl) source).getSource();
		}
		else {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid source type"));
		}
		if (!this.lib.getSources().contains(ls)) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid source"));
		}
		this.lib.getSources().remove(ls);
	}

	@Override
	public ILibrarySource createSourceFile(IPath pathToFile)
			throws CoreException {
		final LibrarySource source = ExtJSFactory.eINSTANCE.createLibrarySource();
		source.setType(LibrarySourceType.JAVASCRIPT_FILE);
		source.setPath(pathToFile.toOSString());
		this.lib.getSources().add(source);
		return new FileImpl(source);
	}

	@Override
	public ILibrarySource createFolder(IPath pathToFolder, String[] exclusions,
			String[] inclusions) throws CoreException {
		final LibrarySource source = ExtJSFactory.eINSTANCE.createLibrarySource();
		source.setType(LibrarySourceType.FOLDER);
		source.setPath(pathToFolder.toOSString());
		if (exclusions != null) {
			for (final String e : exclusions) {
				source.getExclusions().add(e);
			}
		}
		if (inclusions != null) {
			for (final String i : inclusions) {
				source.getInclusions().add(i);
			}
		}
		this.lib.getSources().add(source);
		return new FolderImpl(source);
	}

	@Override
	public ILibrarySource createZip(IPath pathToZip, String[] exclusions,
			String[] inclusions) throws CoreException {
		final LibrarySource source = ExtJSFactory.eINSTANCE.createLibrarySource();
		source.setType(LibrarySourceType.ZIP_FILE);
		source.setPath(pathToZip.toOSString());
		if (exclusions != null) {
			for (final String e : exclusions) {
				source.getExclusions().add(e);
			}
		}
		if (inclusions != null) {
			for (final String i : inclusions) {
				source.getInclusions().add(i);
			}
		}
		this.lib.getSources().add(source);
		return new ZipImpl(source);
	}

	@Override
	public boolean isCompatible(IProjectFacetVersion version) {
		if (version == null) return false;
		return this.lib.getVersions().contains(version.getVersionString());
	}

	@Override
	public Collection<IExtJSFile> getFiles() {
		final List<IExtJSFile> result = new ArrayList<IExtJSFile>();
		for (final LibrarySource src : this.lib.getSources()) {
			for (final File file : src.getFiles()) {
				result.add(new ExtJSFile(file));
			}
		}
		return result;
	}

	@Override
	public void clearIndex() {
		for (final LibrarySource src : this.lib.getSources()) {
			src.getFiles().clear();
		}
	}

	@Override
	public IProject getProject() {
		return null;
	}

}
