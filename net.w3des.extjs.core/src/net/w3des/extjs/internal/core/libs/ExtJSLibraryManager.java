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
package net.w3des.extjs.internal.core.libs;

import java.util.HashMap;
import java.util.Map;

import net.w3des.extjs.core.IExtJSLibraryManager;
import net.w3des.extjs.core.api.CoreType;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.project.ecore.ECoreLibStorageImpl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class ExtJSLibraryManager implements IExtJSLibraryManager {

	/** the underlying index storage */
    private ILibraryStorage storage;
    
    private Map<String, IExtJSCoreLibrary> coreLibs = new HashMap<String, IExtJSCoreLibrary>();
    
	public ExtJSLibraryManager() {
    	this.storage = new ECoreLibStorageImpl();
    }

    public void activate() {
    	this.storage.activate();
    	
    	try {
        	// create core envs/libs on demand
    		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
    		for (final IProjectFacetVersion version : facet.getVersions()) {
    			final String envName = this.getDefaultEnvName(version);
    			if (!this.storage.hasEnv(envName)) {
    				this.storage.createBuiltinEnv(envName, version.getVersionString());
    			}
    			else {
    				this.storage.overwriteVersion(envName, version.getVersionString());
    			}
    		}
    	}
    	catch (Exception ex) {
    		ExtJSCore.error(ex);
    	}
    }

    @Override
    public void save() {
        this.storage.save();
    }

	@Override
	public String getDefaultEnvName(IProjectFacetVersion version) {
		if (version == null) {
    		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
    		IProjectFacetVersion result = null;
    		// find latest version
    		for (final IProjectFacetVersion version2 : facet.getVersions()) {
    			if (result == null) {
    				result = version2;
    			}
    			else if (result.compareTo(version2) < 0) {
    				result = version2;
    			}
    		}
    		return this.getDefaultEnvName(result);
		}
		return "core-" + version.getVersionString();
	}

	@Override
	public IExtJSEnvironment getEnv(String name) {
		return this.storage.getEnv(name, false);
	}

	@Override
	public IExtJSCoreLibrary getDefaultCoreLib(IProjectFacetVersion version) {
		return this.coreLibs.get(version.getVersionString());
	}

	@Override
	public IExtJSEnvironment[] getEnvironments() {
		return this.storage.getEnvironments();
	}

	@Override
	public IExtJSEnvironment createUserEnv(String name,
			IProjectFacetVersion[] versions) throws CoreException {
		if (name == null || name.length() == 0 || name.startsWith("core-")) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		if (this.storage.hasEnv(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Environment already exists"));
		if (versions == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid versions"));
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		for (final IProjectFacetVersion version : versions) {
			if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version"));
		}
		final IExtJSEnvironment result = this.storage.getEnv(name, true);
		result.setCompatibleVersions(versions);
		return result;
	}

	@Override
	public void removeUserEnv(IExtJSEnvironment env) throws CoreException {
		final IExtJSEnvironment result = this.storage.getEnv(env.getName(), false);
		if (result == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Unknown environment"));
		if (result.isBuiltin()) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot remove builtin environment"));
		this.storage.removeEnv(result.getName());
	}

	@Override
	public IExtJSLibrary getLibrary(String libName) {
		return this.storage.getLib(libName, false, false);
	}

	@Override
	public IExtJSLibrary[] getLibraries() {
		return this.storage.getLibraries();
	}

	@Override
	public IExtJSLibrary createUserLib(String name,
			IProjectFacetVersion[] versions) throws CoreException {
		if (name == null || name.length() == 0 || name.startsWith("core-")) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "invalid name"));
		if (this.storage.hasLib(name)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Library already exists"));
		if (versions == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid versions"));
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		for (final IProjectFacetVersion version : versions) {
			if (!version.getProjectFacet().equals(facet)) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version"));
		}
		final IExtJSLibrary result = this.storage.getLib(name, true, false);
		result.setCompatibleVersions(versions);
		return result;
	}

	@Override
	public void removeUserLib(IExtJSLibrary lib) throws CoreException {
		final IExtJSLibrary result = this.storage.getLib(lib.getName(), false, false);
		if (result == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Unknown library"));
		if (result.isBuiltin()) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot remove builtin library"));
		this.storage.removeLibrary(result.getName());
	}

	@Override
	public IExtJSCoreLibrary checkCore(CoreType type, String path)
			throws CoreException {
		IExtJSCoreLibrary lib = null;
		switch (type) {
		default:
		case NONE:
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid core library"));
		case FOLDER:
			final CoreFolderLibrary folderLib = new CoreFolderLibrary("test", path);
			folderLib.check();
			return folderLib;
		case ZIP:
			final CoreZipLibrary zipLib = new CoreZipLibrary("test", path);
			zipLib.check();
			return zipLib;
		}
	}

}
