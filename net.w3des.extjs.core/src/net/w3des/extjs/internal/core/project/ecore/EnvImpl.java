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

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.model.basic.ExecutionEnvironment;
import net.w3des.extjs.core.model.basic.LibrarySourceType;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.libs.CoreFolderLibrary;
import net.w3des.extjs.internal.core.libs.CoreTouchFolderLibrary;
import net.w3des.extjs.internal.core.libs.CoreTouchZipLibrary;
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
	public String[] getCompatibleVersionNames(IProjectFacet facet) {
		if (facet.equals(ExtJSNature.getExtjsFacet()) && (this.env.getFacet() == null || this.env.getFacet().equals(ExtJSNature.getExtjsFacet().getId()))) {
			return this.env.getVersions().toArray(new String[this.env.getVersions().size()]);
		}
		if (facet.equals(ExtJSNature.getSenchaTouchFacet()) && this.env.getFacet() != null && this.env.getFacet().equals(ExtJSNature.getSenchaTouchFacet().getId())) {
			return this.env.getVersions().toArray(new String[this.env.getVersions().size()]);
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

	@Override
	public boolean isOfType(IProjectFacet facet) {
		if (facet.equals(ExtJSNature.getExtjsFacet())) {
			// backward compatibility, check for null
			return this.env.getFacet() == null || this.env.getFacet().equals(facet.getId());
		}
		return this.env.getFacet() != null && this.env.getFacet().equals(facet.getId());
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
		
		if (facet1.equals(version.getProjectFacet()))
		{
			// extjs facet
			if (this.env.getFacet() != null && !this.env.getFacet().equals(facet1.getId())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
			final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions(facet1));
			if (versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version already added"));
			this.env.getVersions().add(version.getVersionString());
			this.refreshLibContainer();
		}
		
		if (facet2.equals(version.getProjectFacet()))
		{
			// touch facet
			if (this.env.getFacet() == null || !this.env.getFacet().equals(facet2.getId())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
			final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions(facet2));
			if (versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version already added"));
			this.env.getVersions().add(version.getVersionString());
			this.refreshLibContainer();
		}
	}

	@Override
	public void removeCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		
		if (!facet1.equals(version.getProjectFacet()) && !facet2.equals(version.getProjectFacet())) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));

		if (facet1.equals(version.getProjectFacet()))
		{
			// extjs facet
			final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions(facet1));
			if (!versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version not in list"));
			this.env.getVersions().remove(version.getVersionString());
			this.refreshLibContainer();
		}
		if (facet1.equals(version.getProjectFacet()))
		{
			// touch facet
			final List<IProjectFacetVersion> versions = Arrays.asList(this.getCompatibleVersions(facet2));
			if (!versions.contains(version)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Version not in list"));
			this.env.getVersions().remove(version.getVersionString());
			this.refreshLibContainer();
		}
	}

	@Override
	public void setCompatibleVersions(IProjectFacet facet, IProjectFacetVersion[] versions)
			throws CoreException {
		if (versions == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		for (final IProjectFacetVersion version : versions) if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
		this.env.getVersions().clear();
		for (final IProjectFacetVersion version : versions) this.env.getVersions().add(version.getVersionString());
		this.env.setFacet(facet.getId());
		this.refreshLibContainer();
	}

	@Override
	public IExtJSCoreLibrary getCore() throws CoreException {
		if (this.env.getCorePath() == null || this.env.getCorePath() == null) return null;
		switch (this.env.getCoreType()) {
		case FOLDER:
			if (this.isOfType(ExtJSNature.getExtjsFacet())) {
				return new CoreFolderLibrary(this.getName(), this.env.getCorePath());
			}
			return new CoreTouchFolderLibrary(this.getName(), this.env.getCorePath());
		case ZIP_FILE:
			if (this.isOfType(ExtJSNature.getExtjsFacet())) {
				return new CoreZipLibrary(this.getName(), this.env.getCorePath());
			}
			return new CoreTouchZipLibrary(this.getName(), this.env.getCorePath());
		default:
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid core type"));
		}
	}

	@Override
	public void checkCoreZip(File zipFile) throws CoreException {
		if (this.isOfType(ExtJSNature.getExtjsFacet())) {
			new CoreZipLibrary(this.getName(), zipFile.getAbsolutePath()).check();
		}
		else if (this.isOfType(ExtJSNature.getSenchaTouchFacet())) {
			new CoreTouchZipLibrary(this.getName(), zipFile.getAbsolutePath()).check();
		}
	}

	@Override
	public void checkCoreZip(IFile zipFile) throws CoreException {
		if (this.isOfType(ExtJSNature.getExtjsFacet())) {
			new CoreZipLibrary(this.getName(), zipFile.getLocation().toOSString()).check();
		}
		else if (this.isOfType(ExtJSNature.getSenchaTouchFacet())) {
			new CoreTouchZipLibrary(this.getName(), zipFile.getLocation().toOSString()).check();
		}
	}

	@Override
	public void checkCoreFolder(File folder) throws CoreException {
		if (this.isOfType(ExtJSNature.getExtjsFacet())) {
			new CoreFolderLibrary(this.getName(), folder.getAbsolutePath()).check();
		}
		else if (this.isOfType(ExtJSNature.getSenchaTouchFacet())) {
			new CoreTouchFolderLibrary(this.getName(), folder.getAbsolutePath()).check();
		}
	}

	@Override
	public void checkCoreFolder(IFolder folder) throws CoreException {
		if (this.isOfType(ExtJSNature.getExtjsFacet())) {
			new CoreFolderLibrary(this.getName(), folder.getLocation().toOSString()).check();
		}
		else if (this.isOfType(ExtJSNature.getSenchaTouchFacet())) {
			new CoreTouchFolderLibrary(this.getName(), folder.getLocation().toOSString()).check();
		}
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
