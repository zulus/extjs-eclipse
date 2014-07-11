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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.model.basic.ExecutionEnvironment;
import net.w3des.extjs.core.model.basic.ExtJSFactory;
import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.Library;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.libs.ILibraryStorage;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class ECoreLibStorageImpl implements ILibraryStorage {
	
    private Map<String, ExecutionEnvironment> environments;

    private Map<String, Library> libraries;

	@Override
	public void activate() {
        environments = new HashMap<String, ExecutionEnvironment>();
        libraries = new HashMap<String, Library>();
        ExtJSPackage.eINSTANCE.eClass();
        try {
            final Resource resource = getResource();
            if (resource != null) {
                for (final Object o : resource.getContents()) {
                    if (o instanceof ExecutionEnvironment) {
                        final ExecutionEnvironment env = (ExecutionEnvironment) o;
                    	this.environments.put(env.getName(), env);
                    }
                    else if (o instanceof Library) {
                        final Library lib = (Library) o;
                    	this.libraries.put(lib.getName(), lib);
                    }
                }
            }
        } catch (final Throwable e) {
            ExtJSCore.error(e);
        }
	}

	@Override
	public boolean hasEnv(String name) {
		return this.environments.containsKey(name);
	}

	@Override
	public IExtJSEnvironment getEnv(String name, boolean forceCreation) {
		if (!this.environments.containsKey(name) && forceCreation) {
			final ExecutionEnvironment env = ExtJSFactory.eINSTANCE.createExecutionEnvironment();
			env.setName(name);
			env.setBuiltin(false);
			
			environments.put(name, env);
		}
		final ExecutionEnvironment env = environments.get(name);
		return env == null ? null : new EnvImpl(this, env);
	}

	@Override
	public void removeEnv(String name) {
		if (this.environments.containsKey(name)) {
			this.environments.remove(name);
		}
	}

	@Override
	public boolean hasLib(String name) {
		return this.libraries.containsKey(name);
	}

	@Override
	public IExtJSLibrary getLib(String name, boolean forceCreation,
			boolean createBuiltin) {
		if (!this.libraries.containsKey(name) && forceCreation) {
			final Library lib = ExtJSFactory.eINSTANCE.createLibrary();
			lib.setName(name);
			lib.setBuiltin(createBuiltin);
			
			libraries.put(name, lib);
		}
		final Library env = libraries.get(name);
		return env == null ? null : new LibImpl(this, env);
	}

	@Override
	public void removeLibrary(String name) {
		if (this.libraries.containsKey(name)) {
			this.libraries.remove(name);
		}
	}

    private Resource createResource() {
        final java.io.File file = ExtJSCore.getDefault().getStateLocation().append("envlib.extjsproject").toFile(); //$NON-NLS-1$
        try {
            file.createNewFile();
        } catch (IOException e) {
        }

        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("extjsproject", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        final ResourceSet resSet = new ResourceSetImpl();

        return resSet.createResource(URI.createFileURI(file.getAbsolutePath()));

    }

    private Resource getResource() {
        final java.io.File file = ExtJSCore.getDefault().getStateLocation().append("envlib.extjsproject").toFile(); //$NON-NLS-1$

        if (!file.exists()) {
            return null;
        }
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("extjsproject", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        final ResourceSet resSet = new ResourceSetImpl();

        return resSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);

    }

	@Override
	public void save() {
        final Resource resource = createResource();

        for (final Entry<String, ExecutionEnvironment> entry : environments.entrySet()) {
            resource.getContents().add(entry.getValue());
        }
        for (final Entry<String, Library> entry : libraries.entrySet()) {
            resource.getContents().add(entry.getValue());
        }

        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (final IOException e) {
            ExtJSCore.error(e);
        }
	}

	@Override
	public IExtJSEnvironment[] getEnvironments() {
    	final List<IExtJSEnvironment> result = new ArrayList<IExtJSEnvironment>();
    	for (final Map.Entry<String, ExecutionEnvironment> entry : this.environments.entrySet()) {
    		// wrap content
    		result.add(new EnvImpl(this, entry.getValue()));
    	}
        return result.toArray(new IExtJSEnvironment[result.size()]);
	}

	@Override
	public IExtJSLibrary[] getLibraries() {
    	final List<IExtJSLibrary> result = new ArrayList<IExtJSLibrary>();
    	for (final Map.Entry<String, Library> entry : this.libraries.entrySet()) {
    		// wrap content
    		result.add(new LibImpl(this, entry.getValue()));
    	}
        return result.toArray(new IExtJSLibrary[result.size()]);
	}

	public void notifyNameChange(ExecutionEnvironment env, String oldName,
			String name) {
		this.environments.remove(oldName);
		this.environments.put(name, env);
	}

	public void notifyNameChange(Library lib, String oldName, String name) {
		this.libraries.remove(oldName);
		this.libraries.put(name, lib);
	}

	@Override
	public IExtJSEnvironment createBuiltinEnv(String name, String compatibleVersion, String facet) {
		if (!this.environments.containsKey(name)) {
			final ExecutionEnvironment env = ExtJSFactory.eINSTANCE.createExecutionEnvironment();
			env.setName(name);
			env.setBuiltin(true);
			env.setFacet(facet);
			env.getVersions().add(compatibleVersion);
			
			environments.put(name, env);
		}
		final ExecutionEnvironment env = environments.get(name);
		return env == null ? null : new EnvImpl(this, env);
	}

	@Override
	public void overwriteVersion(String envName, String versionString, String facet) {
		final ExecutionEnvironment env = this.environments.get(envName);
		if (env != null && env.isBuiltin()) {
			env.setFacet(facet);
			env.getVersions().clear();
			env.getVersions().add(versionString);
		}
	}

}
