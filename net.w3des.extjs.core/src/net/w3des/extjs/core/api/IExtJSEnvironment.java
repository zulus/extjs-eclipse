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
package net.w3des.extjs.core.api;

import java.io.File;

import net.w3des.extjs.core.IExtJSLibraryManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * An extjs build environment
 * 
 * @author mepeisen
 */
public interface IExtJSEnvironment {
	
	/**
	 * Returns the unique name to identify this extjs environment
	 * @return
	 */
	String getName();
	
	/**
	 * Returns true if this environment is builtin (not removable and name not changeable)
	 * @return
	 */
	boolean isBuiltin();
	
	/**
	 * Changes the name of this environment
	 * @param name new name; prefix "core-" is reserved
	 * @throws CoreException thrown for invalid names or if the environment is builtin
	 */
	void setName(String name) throws CoreException;
	
	// *** version compatibility
	
	/**
	 * Returns true if this environment is of given type
	 * @param facet
	 * @return
	 */
	boolean isOfType(IProjectFacet facet);
	
	/**
	 * Checks for version compatibility
	 * @param version
	 * @return
	 * @throws CoreException 
	 */
	boolean isCompatible(IProjectFacetVersion version) throws CoreException;
	
	/**
	 * Returns the extjs versions being compatible to this build environment; should only be used to analyze problems on {@link #getCompatibleVersions()}
	 * @param facet extjs or senchatouch
	 * @return version names (only major and minor version number, f.e. "4.1")
	 */
	String[] getCompatibleVersionNames(IProjectFacet facet);
	
	/**
	 * Returns the compatible versions as facets
	 * @param facet extjs or senchatouch
	 * @return
	 * @throws CoreException thrown if there is an unknown version name this extjs-plugin does not support; indicates problems on plugin dependencies.
	 */
	IProjectFacetVersion[] getCompatibleVersions(IProjectFacet facet) throws CoreException;
	
	/**
	 * Adds a compatible extjs version
	 * @param facet extjs or senchatouch
	 * @param version version name (only major and minor version number, f.e. "4.1")
	 * @throws CoreException thrown for invalid versions or if the environment is builtin
	 */
	void addCompatibleVersion(IProjectFacetVersion version) throws CoreException;
	
	/**
	 * Removes an existing compatible extjs version
	 * @param version version name (only major and minor version number, f.e. "4.1")
	 * @throws CoreException thrown for invalid versions or if the environment is builtin
	 */
	void removeCompatibleVersion(IProjectFacetVersion version) throws CoreException;
	
	/**
	 * Replaces the compatible versions
	 * @param facet extjs or senchatouch
	 * @param versions the new version names (only major and minor version number, f.e. "4.1")
	 * @throws CoreException thrown for invalid versions or if the environment is builtin
	 */
	void setCompatibleVersions(IProjectFacet facet, IProjectFacetVersion[] versions) throws CoreException;
	
	// *** core (extjs sdk)
	
	/**
	 * Returns the extjs core library. If this method returns {@code null} use the library returned by
	 * {@link IExtJSLibraryManager#getDefaultCoreLib(IProjectFacetVersion)}.
	 * @return extjs core library or {@code null} if there is no library set
	 * @throws CoreException thrown on library problems (incompatible or not readable)
	 */
	IExtJSCoreLibrary getCore() throws CoreException;
	
	/**
	 * Checks the given zip file for compatibility
	 * @param zipFile
	 * @return
	 * @throws CoreException thrown if the zip file is not readable or compatible
	 */
	void checkCoreZip(File zipFile) throws CoreException;
	
	/**
	 * Checks the given zip file for compatibility
	 * @param zipFile
	 * @return
	 * @throws CoreException thrown if the zip file is not readable or compatible
	 */
	void checkCoreZip(IFile zipFile) throws CoreException;
	
	/**
	 * Checks the given folder for compatibility
	 * @param folder
	 * @return
	 * @throws CoreException thrown if the zip file is not readable or compatible
	 */
	void checkCoreFolder(File folder) throws CoreException;
	
	/**
	 * Checks the given folder for compatibility
	 * @param folder
	 * @return
	 * @throws CoreException thrown if the zip file is not readable or compatible
	 */
	void checkCoreFolder(IFolder folder) throws CoreException;
	
	/**
	 * Sets the core library zip file
	 * @param zipFile
	 * @throws CoreException thrown if the zip file is not readable or compatible
	 */
	void setCoreLibraryZip(File zipFile) throws CoreException;
	
	/**
	 * Sets the core library zip file
	 * @param zipFile
	 * @throws CoreException thrown if the zip file is not readable or compatible
	 */
	void setCoreLibraryZip(IFile zipFile) throws CoreException;
	
	/**
	 * Sets the core library (extracted folder)
	 * @param folder
	 * @throws CoreException thrown if the folder is not readable or compatible
	 */
	void setCoreLibraryFolder(File folder) throws CoreException;
	
	/**
	 * Sets the core library (extracted folder)
	 * @param folder
	 * @throws CoreException thrown if the folder is not readable or compatible
	 */
	void setCoreLibraryFolder(IFolder folder) throws CoreException;
	
	// *** additional libraries
	
	/**
	 * Returns the names of the user defined libraries; should only be used to analyze problems on {@link #getLibraries()}
	 * @return
	 */
	String[] getLibraryNames();
	
	/**
	 * Returns additional user defined libraries
	 * @return user defined libraries
	 * @throws CoreException thrown if some of the libraries could not be found
	 */
	IExtJSLibrary[] getLibraries() throws CoreException;
	
	/**
	 * Adds a user defined library to this environment
	 * @param lib
	 * @throws CoreException
	 */
	void addLibrary(IExtJSLibrary lib) throws CoreException;
	
	/**
	 * Removes a user defined library from this environment
	 * @param lib
	 * @throws CoreException
	 */
	void removeLibrary(IExtJSLibrary lib) throws CoreException;
	
	/**
	 * Replaces the user defined libraries
	 * @param lib
	 * @throws CoreException
	 */
	void setLibraries(IExtJSLibrary[] lib) throws CoreException;

	/**
	 * Removes core library
	 * @throws CoreException
	 */
	void removeCoreLibrary() throws CoreException;

}
