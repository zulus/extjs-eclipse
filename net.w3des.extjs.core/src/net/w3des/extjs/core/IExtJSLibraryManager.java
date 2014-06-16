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
package net.w3des.extjs.core;

import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Support for extjs libraries and environments.
 * 
 * <p>
 * The library manager supports build environments and extjs libraries to be bound to an extjs project.
 * </p>
 * 
 * <p>
 * A build environment is identified by name. Normally developers should choose the builtin environments
 * (returned by {@link #getDefaultEnvName(IProjectFacetVersion)}. This ensures the project can be shared
 * to others without additional configuration. But it is possible to create a special user defined
 * environment.
 * </p>
 * 
 * <p>
 * Each environment owns exactly one core library (the extjs itself). This is either the sdk zip file or
 * the folder of the extracted sdk. Developers may set their own core library in workspace properties.
 * If no individual core library is set the code falls back to {@link #getDefaultCoreLib(IProjectFacetVersion)}.
 * The default core lib is provided by extension point {@code net.w3des.extjs.core.library}.
 * </p>
 * 
 * <p>
 * Developers may add extra extjs libraries to execution environments so that they are loaded once the
 * execution environment initializes.
 * </p>
 * 
 * @author mepeisen
 */
public interface IExtJSLibraryManager {

    public void save();
	
	// execution environment
	
	/**
	 * Returns the default environment name for given project facet version
	 * @param version
	 * @return
	 */
	String getDefaultEnvName(IProjectFacetVersion version);
	
	/**
	 * returns the environment by name
	 * @param name
	 * @return environment object or {@code null} if the given environment was not found
	 */
	IExtJSEnvironment getEnv(String name);
	
	/**
	 * returns the default core lib
	 * @param version
	 * @return default core lib or {@code null} if no default library was provided
	 */
	IExtJSCoreLibrary getDefaultCoreLib(IProjectFacetVersion version);
	
	/**
	 * returns the known environments
	 * @return environments
	 */
	IExtJSEnvironment[] getEnvironments();
	
	/**
	 * Creates a user defined environment with given name
	 * @param name environment name (prefix "core-" is not allowed here)
	 * @param versions the compatible extjs versions
	 * @return
	 * @throws CoreException thrown on invalid names or versions
	 */
	IExtJSEnvironment createUserEnv(String name, IProjectFacetVersion[] versions) throws CoreException;
	
	/**
	 * Removes user defined environment
	 * @param env the environment to be deleted
	 * @throws CoreException thrown if the environment is invalid or builtin
	 */
	void removeUserEnv(IExtJSEnvironment env) throws CoreException;
	
	// user libraries
	
	/**
	 * returns s extjs library by name
	 * @param libName
	 * @return
	 */
	IExtJSLibrary getLibrary(String libName);
	
	/**
	 * returns the known extjs libraries
	 * @return
	 */
	IExtJSLibrary[] getLibraries();
	
	/**
	 * Creates a new user defined library
	 * @param name the library name (prefix "core" is not allowed here)
	 * @param versions the compatible extjs versions
	 * @return the new library object
	 * @throws CoreException thrown on invalid name or versions
	 */
	IExtJSLibrary createUserLib(String name, IProjectFacetVersion[] versions) throws CoreException;
	
	/**
	 * Removes a user defined library
	 * @param lib the library to be deleted
	 * @throws CoreException thrown if the library is invalid or builtin
	 */
	void removeUserLib(IExtJSLibrary lib) throws CoreException;

}