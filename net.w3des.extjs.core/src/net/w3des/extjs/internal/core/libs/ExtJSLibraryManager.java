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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.IExtJSLibraryManager;
import net.w3des.extjs.core.api.CoreType;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.project.ecore.ECoreLibStorageImpl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.osgi.framework.Bundle;

public class ExtJSLibraryManager implements IExtJSLibraryManager {

	/** the underlying index storage */
    private ILibraryStorage storage;
    
    private Map<IProjectFacetVersion, IExtJSCoreLibrary> defaultCoreLibs = new HashMap<IProjectFacetVersion, IExtJSCoreLibrary>();
    
    private Map<String, IExtJSCoreLibrary> coreLibs = new HashMap<String, IExtJSCoreLibrary>(); 
    
	public ExtJSLibraryManager() {
    	this.storage = new ECoreLibStorageImpl();
    }

    public void activate() {
    	this.storage.activate();
    	
    	try {
        	// create core envs/libs on demand
    		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
    		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
    		for (final IProjectFacetVersion version : facet1.getVersions()) {
    			final String envName = this.getDefaultEnvName(version);
    			if (!this.storage.hasEnv(envName)) {
    				this.storage.createBuiltinEnv(envName, version.getVersionString(), facet1.getId());
    			}
    			else {
    				this.storage.overwriteVersion(envName, version.getVersionString(), facet1.getId());
    			}
    		}
    		for (final IProjectFacetVersion version : facet2.getVersions()) {
    			final String envName = this.getDefaultEnvName(version);
    			if (!this.storage.hasEnv(envName)) {
    				this.storage.createBuiltinEnv(envName, version.getVersionString(), facet2.getId());
    			}
    			else {
    				this.storage.overwriteVersion(envName, version.getVersionString(), facet2.getId());
    			}
    		}
    		
    		// create built libraries from extension point
    		final IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("net.w3des.extjs.core.runtimelib");
    		for (final IConfigurationElement runtime : elements) {
   				final String fname = runtime.getAttribute("zip");
   				final String facetId = runtime.getAttribute("facet_id");
   				final String facetVersion = runtime.getAttribute("facet_version");
   				final String pluginName = runtime.getContributor().getName();
   				IProjectFacet facet = null;
   				if (facetId.equals(facet1.getId())) {
   					facet = facet1;
   				}
   				else if (facetId.equals(facet2.getId())) {
   					facet = facet2;
   				}
   				else {
   					ExtJSCore.error(NLS.bind("Problems during creating core library. {0} binds runtime lib to invalid facet {1}", new Object[]{pluginName, facetId}));
   					continue;
   				}
   				final IProjectFacetVersion version = facet.getVersion(facetVersion);
   				if (version == null) {
   					ExtJSCore.error(NLS.bind("Problems during creating core library. {0} binds runtime lib to invalid facet version {1}/{2}", new Object[]{pluginName, facetId, facetVersion}));
   					continue;
   				}
   				
   				final String name = getCoreLibName(version, new Path(fname));
   				final Bundle bundle = Platform.getBundle(pluginName);
   				final URL url = FileLocator.resolve(bundle.getEntry(fname));
   				IExtJSCoreLibrary lib = null; 
   				if (facet == facet1) {
   					final CoreZipLibrary zip = new CoreZipLibrary(name, url.toString().substring(6));
   					zip.check();
   					lib = zip;
   				}
   				else {
   					final CoreTouchZipLibrary zip = new CoreTouchZipLibrary(name, url.toString().substring(6));
   					zip.check();
   					lib = zip;
   				}
   				this.coreLibs.put(name, lib);
   				if (!this.defaultCoreLibs.containsKey(version)) {
   					this.defaultCoreLibs.put(version, lib);
   				}
    		}
    	}
    	catch (Exception ex) {
    		ExtJSCore.error(ex);
    	}
    }
    
    private String getCoreLibName(final IProjectFacetVersion version, IPath fileName)
    {
    	String prefix = null;
		if (ExtJSCore.FACET_EXT.equals(version.getProjectFacet().getId())) {
			prefix = "core-extjs-";
		}
		else if (ExtJSCore.FACET_TOUCH.equals(version.getProjectFacet().getId())) {
			prefix = "core-touch-";
		}
		else {
			return null;
		}
		
		int i = 0;
		do
		{
			String name = prefix + fileName.lastSegment() + (i == 0 ? 0 : ("-" + i));
			if (this.coreLibs.containsKey(name)) {
				i++;
			}
			else {
				return name;
			}
		}
		while (true);
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
		if (ExtJSCore.FACET_EXT.equals(version.getProjectFacet().getId())) {
			return "core-extjs-" + version.getVersionString();
		}
		else if (ExtJSCore.FACET_TOUCH.equals(version.getProjectFacet().getId())) {
			return "core-touch-" + version.getVersionString();
		}
		return null;
	}

	@Override
	public IExtJSEnvironment getEnv(String name) {
		return this.storage.getEnv(name, false);
	}

	@Override
	public IExtJSCoreLibrary getDefaultCoreLib(IProjectFacetVersion version) {
		return this.defaultCoreLibs.get(version);
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
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		final List<IProjectFacetVersion> versions1 = new ArrayList<IProjectFacetVersion>();
		final List<IProjectFacetVersion> versions2 = new ArrayList<IProjectFacetVersion>();
		for (final IProjectFacetVersion version : versions) {
			if (version.getProjectFacet().equals(facet1)) {
				versions1.add(version);
			}
			else if (version.getProjectFacet().equals(facet2)) {
				versions2.add(version);
			}
			else {
				throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version"));
			}
		}
		if (versions1.size() > 0 && versions2.size() > 0) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version"));
		}
		final IExtJSEnvironment result = this.storage.getEnv(name, true);
		if (versions1.size() > 0) {
			result.setCompatibleVersions(facet1, versions1.toArray(new IProjectFacetVersion[versions1.size()]));
		}
		if (versions2.size() > 0) {
			result.setCompatibleVersions(facet2, versions2.toArray(new IProjectFacetVersion[versions2.size()]));
		}
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
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final List<IProjectFacetVersion> versions1 = new ArrayList<IProjectFacetVersion>();
		final List<IProjectFacetVersion> versions2 = new ArrayList<IProjectFacetVersion>();
		for (final IProjectFacetVersion version : versions) {
			if (version.getProjectFacet().equals(facet1)) {
				versions1.add(version);
			}
			else if (version.getProjectFacet().equals(facet2)) {
				versions2.add(version);
			}
			else {
				throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version"));
			}
		}
		final IExtJSLibrary result = this.storage.getLib(name, true, false);
		result.setCompatibleVersions(facet1, versions1.toArray(new IProjectFacetVersion[versions1.size()]));
		result.setCompatibleVersions(facet2, versions2.toArray(new IProjectFacetVersion[versions2.size()]));
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
	public IExtJSCoreLibrary checkCore(CoreType type, String path, IProjectFacet facet)
			throws CoreException {
		switch (type) {
		default:
		case NONE:
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid core library"));
		case FOLDER:
			if (facet.equals(ExtJSNature.getExtjsFacet()))
			{
				final CoreFolderLibrary folderLib = new CoreFolderLibrary("test", path);
				folderLib.check();
				return folderLib;
			}
			if (facet.equals(ExtJSNature.getSenchaTouchFacet()))
			{
				final CoreTouchFolderLibrary folderLib = new CoreTouchFolderLibrary("test", path);
				folderLib.check();
				return folderLib;
			}
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid core library"));
		case ZIP:
			if (facet.equals(ExtJSNature.getExtjsFacet()))
			{
				final CoreZipLibrary zipLib = new CoreZipLibrary("test", path);
				zipLib.check();
				return zipLib;
			}
			if (facet.equals(ExtJSNature.getSenchaTouchFacet()))
			{
				final CoreTouchZipLibrary zipLib = new CoreTouchZipLibrary("test", path);
				zipLib.check();
				return zipLib;
			}
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid core library"));
		}
	}

	@Override
	public IExtJSCoreLibrary[] getBuiltinCoreLibraries() {
		return this.coreLibs.values().toArray(new IExtJSCoreLibrary[this.coreLibs.size()]);
	}

	@Override
	public void setDefaultCoreLib(IProjectFacetVersion version,
			IExtJSCoreLibrary coreLib) {
		// TODO Auto-generated method stub
		
	}

}
