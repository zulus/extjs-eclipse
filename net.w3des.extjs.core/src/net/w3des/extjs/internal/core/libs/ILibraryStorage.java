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

import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.ILibrarySource;

public interface ILibraryStorage {
	
	/**
	 * Activates/initializes the storage
	 */
	void activate();
	
	/**
	 * Returns true if the given environment is known in the index
	 * @param name
	 * @return
	 */
	boolean hasEnv(String name);
	
	/**
	 * Returns the environment; creates it on demand
	 * @param name the environment name
	 * @param forceCreation {@code true} to create the environment if it does not exist; {@code false} to return {@code null} instead
	 * @return the environment or {@code null} if it was not found
	 */
	IExtJSEnvironment getEnv(String name, boolean forceCreation);
	
	/**
	 * Returns the environment; creates it on demand
	 * @param name the environment name
	 * @param compatibleVersion the version this env is compatible to
	 * @param facet the facet to be used
	 * @return the environment or {@code null} if it was not found
	 */
	IExtJSEnvironment createBuiltinEnv(String name, String compatibleVersion, String facet);

	/**
	 * Destroys the index for given environment
	 * @param name
	 */
	void removeEnv(String name);
	
	/**
	 * Returns true if the given library is known in the index
	 * @param name
	 * @return
	 */
	boolean hasLib(String name);
	
	/**
	 * Returns the library; creates it on demand
	 * @param name the library name
	 * @param forceCreation {@code true} to create the library if it does not exist; {@code false} to return {@code null} instead
	 * @return the library or {@code null} if it was not found
	 */
	IExtJSLibrary getLib(String name, boolean forceCreation, boolean createBuiltin);

	/**
	 * Overwrites the versions of a builtin library and activates it
	 * @param envName
	 * @param versionString
	 * @param facet
	 */
	void overwriteVersion(String envName, String versionString, String facet);

	/**
	 * Destroys the index for given library
	 * @param name
	 */
	void removeLibrary(String name);
	
	/**
	 * Returns the available environment
	 * @return
	 */
	IExtJSEnvironment[] getEnvironments();
	
	/**
	 * Returns the available libraries
	 * @return
	 */
	IExtJSLibrary[] getLibraries();

	/**
	 * Saves the index
	 */
	void save();

	/**
	 * Sets the default core lib
	 * @param version
	 * @param facet
	 * @param libName
	 */
	void setDefaultCoreLib(String version, String facet, String libName);
	
	/**
	 * Returns the default core lib name
	 * @param version
	 * @param facet
	 * @return libName or null if it is not set
	 */
	String getDefaultCoreLib(String version, String facet);
	
	/**
	 * Returns the file index; creates it on demand
	 * @param filePath the full path to the file (file system path)
	 * @param libName 
	 * @param src 
	 * @param forceCreation {@code true} to create the project if it does not exist; {@code false} to return {@code null} instead
	 * @return the file or {@code null} if it was not found
	 */
	IExtJSFile getFile(String filePath, String libName, ILibrarySource src, boolean forceCreation);

	/**
	 * Returns the library index for given core library
	 * @param name
	 * @return
	 */
	IExtJSIndex getCoreLibIndex(String name);

}
