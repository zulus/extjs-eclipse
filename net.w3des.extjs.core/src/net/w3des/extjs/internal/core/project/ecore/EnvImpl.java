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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.model.basic.ExecutionEnvironment;
import net.w3des.extjs.core.model.basic.LibrarySourceType;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.libs.CoreFolderLibrary;
import net.w3des.extjs.internal.core.libs.CoreZipLibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class EnvImpl implements IExtJSEnvironment {

	private ExecutionEnvironment env;
	private ECoreLibStorageImpl storage;

	public EnvImpl(ECoreLibStorageImpl storage, ExecutionEnvironment env) {
		this.env = env;
		this.storage = storage;
	}

	@Override
	public String getName() {
		return this.env.getName();
	}

	@Override
	public boolean isBuiltin() {
		return this.env.isBuiltin();
	}

	@Override
	public void setName(String name) throws CoreException {
		if (name == null || name.length() == 0 || name.startsWith("core-")) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		if (this.isBuiltin()) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change name of builtin environments"));
		if (this.storage.hasEnv(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Duplicate name"));
		final String oldName = this.env.getName();
		this.env.setName(name);
		this.storage.notifyNameChange(this.env, oldName, name);
	}

	@Override
	public String[] getCompatibleVersionNames() {
		return this.env.getVersions().toArray(new String[this.env.getVersions().size()]);
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
		this.env.getVersions().add(version.getVersionString());
		this.refreshLibContainer();
	}

	@Override
	public void removeCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (!facet.equals(version.getProjectFacet())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions());
		if (!versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version not in list"));
		this.env.getVersions().remove(version.getVersionString());
		this.refreshLibContainer();
	}

	@Override
	public void setCompatibleVersions(IProjectFacetVersion[] versions)
			throws CoreException {
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		if (versions == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		for (final IProjectFacetVersion version : versions) if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		this.env.getVersions().clear();
		for (final IProjectFacetVersion version : versions) this.env.getVersions().add(version.getVersionString());
		this.refreshLibContainer();
	}

	@Override
	public IExtJSCoreLibrary getCore() throws CoreException {
		if (this.env.getCoreType() == null) return null;
		switch (this.env.getCoreType()) {
		case FOLDER:
			return new CoreFolderLibrary(this.getName(), this.env.getCorePath());
		case ZIP_FILE:
			return new CoreZipLibrary(this.getName(), this.env.getCorePath());
		default:
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid core type"));
		}
	}

	@Override
	public void checkCoreZip(File zipFile) throws CoreException {
		new CoreZipLibrary(this.getName(), zipFile.getAbsolutePath()).check();
	}

	@Override
	public void checkCoreZip(IFile zipFile) throws CoreException {
		new CoreZipLibrary(this.getName(), zipFile.getLocation().toOSString()).check();
	}

	@Override
	public void checkCoreFolder(File folder) throws CoreException {
		new CoreFolderLibrary(this.getName(), folder.getAbsolutePath()).check();
	}

	@Override
	public void checkCoreFolder(IFolder folder) throws CoreException {
		new CoreFolderLibrary(this.getName(), folder.getLocation().toOSString()).check();
	}

	@Override
	public void setCoreLibraryZip(File zipFile) throws CoreException {
		if (zipFile == null) {
			this.env.setCoreType(null);
			this.env.setCorePath(null);
		}
		else {
			this.checkCoreZip(zipFile);
			this.env.setCoreType(LibrarySourceType.ZIP_FILE);
			this.env.setCorePath(zipFile.getAbsolutePath());
		}
		this.refreshLibContainer();
	}

	@Override
	public void setCoreLibraryZip(IFile zipFile) throws CoreException {
		if (zipFile == null) {
			this.env.setCoreType(null);
			this.env.setCorePath(null);
		}
		else {
			this.checkCoreZip(zipFile);
			this.env.setCoreType(LibrarySourceType.ZIP_FILE);
			this.env.setCorePath(zipFile.getLocation().toOSString());
		}
		this.refreshLibContainer();
	}

	@Override
	public void setCoreLibraryFolder(File folder) throws CoreException {
		if (folder == null) {
			this.env.setCoreType(null);
			this.env.setCorePath(null);
		}
		else {
			this.checkCoreFolder(folder);
			this.env.setCoreType(LibrarySourceType.FOLDER);
			this.env.setCorePath(folder.getAbsolutePath());
		}
		this.refreshLibContainer();
	}

	@Override
	public void setCoreLibraryFolder(IFolder folder) throws CoreException {
		if (folder == null) {
			this.env.setCoreType(null);
			this.env.setCorePath(null);
		}
		else {
			this.checkCoreFolder(folder);
			this.env.setCoreType(LibrarySourceType.FOLDER);
			this.env.setCorePath(folder.getLocation().toOSString());
		}
		this.refreshLibContainer();
	}

	@Override
	public String[] getLibraryNames() {
		return this.env.getLibraries().toArray(new String[this.env.getLibraries().size()]);
	}

	@Override
	public IExtJSLibrary[] getLibraries() throws CoreException {
		final List<IExtJSLibrary> result = new ArrayList<IExtJSLibrary>();
		for (final String name : this.getLibraryNames()) {
			final IExtJSLibrary lib = ExtJSCore.getLibraryManager().getLibrary(name);
			if (lib == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Library not found"));
			result.add(lib);
		}
		return result.toArray(new IExtJSLibrary[result.size()]);
	}

	@Override
	public void addLibrary(IExtJSLibrary lib) throws CoreException {
		final List<IExtJSLibrary> result = Arrays.asList(this.getLibraries());
		if (result.contains(lib.getName())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Library already added"));
		this.env.getLibraries().add(lib.getName());
		this.refreshLibContainer();
	}

	@Override
	public void removeLibrary(IExtJSLibrary lib) throws CoreException {
		final List<IExtJSLibrary> result = Arrays.asList(this.getLibraries());
		if (!result.contains(lib.getName())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Library not in list"));
		this.env.getLibraries().remove(lib.getName());
		this.refreshLibContainer();
	}

	@Override
	public void setLibraries(IExtJSLibrary[] lib) throws CoreException {
		this.env.getLibraries().clear();
		for (final IExtJSLibrary l : lib) {
			this.env.getLibraries().add(l.getName());
		}
		this.refreshLibContainer();
	}

}
