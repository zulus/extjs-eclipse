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
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.core.model.basic.ExtJSFactory;
import net.w3des.extjs.core.model.basic.Library;
import net.w3des.extjs.core.model.basic.LibrarySource;
import net.w3des.extjs.core.model.basic.LibrarySourceType;
import net.w3des.extjs.internal.core.ExtJSCore;

public class LibImpl implements IExtJSLibrary {

	private Library lib;
	private ECoreLibStorageImpl storage;

	public LibImpl(ECoreLibStorageImpl storage, Library lib) {
		this.lib = lib;
		this.storage = storage;
	}

	@Override
	public IExtJSIndex getIndex() {
		// TODO Auto-generated method stub
		return null;
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
	public String[] getCompatibleVersionNames() {
		return this.lib.getVersions().toArray(new String[this.lib.getVersions().size()]);
	}

	@Override
	public IProjectFacetVersion[] getCompatibleVersions() throws CoreException {
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final List<IProjectFacetVersion> result = new ArrayList<IProjectFacetVersion>();
		for (final String version : this.getCompatibleVersionNames()) {
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
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (!facet.equals(version.getProjectFacet())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions());
		if (versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version already added"));
		this.lib.getVersions().add(version.getVersionString());
		this.refreshLibContainer();
	}

	@Override
	public void removeCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (!facet.equals(version.getProjectFacet())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions());
		if (!versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version not in list"));
		this.lib.getVersions().remove(version.getVersionString());
		this.refreshLibContainer();
	}

	@Override
	public void setCompatibleVersions(IProjectFacetVersion[] versions)
			throws CoreException {
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (versions == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		for (final IProjectFacetVersion version : versions) if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		this.lib.getVersions().clear();
		for (final IProjectFacetVersion version : versions) this.lib.getVersions().add(version.getVersionString());
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

}
