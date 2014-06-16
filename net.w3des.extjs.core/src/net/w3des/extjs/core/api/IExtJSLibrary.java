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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * An extjs library; collection of files to be used within an extjs project (f.e. additional widgets)
 * 
 * @author mepeisen
 */
public interface IExtJSLibrary {

	/**
	 * Returns the extjs index
	 * @return extjs index
	 */
	IExtJSIndex getIndex();
	
	/**
	 * Returns the library name
	 * @return
	 */
	String getName();
	
	/**
	 * returns true for a builtin library
	 * @return
	 */
	boolean isBuiltin();
	
	/**
	 * Changes the library name
	 * @param name new name of the library; prefix "core-" is reserved
	 * @throws CoreException thrown on invalid names or if this library is builtin 
	 */
	void setName(String name) throws CoreException;
	
	// *** version compatibility
	
	/**
	 * Returns the extjs versions being compatible to this build environment; should only be used to analyze problems on {@link #getCompatibleVersions()}
	 * @return version names (only major and minor version number, f.e. "4.1")
	 */
	String[] getCompatibleVersionNames();
	
	/**
	 * Returns the compatible versions as facets
	 * @return
	 * @throws CoreException thrown if there is an unknown version name this extjs-plugin does not support; indicates problems on plugin dependencies.
	 */
	IProjectFacetVersion[] getCompatibleVersions() throws CoreException;
	
	/**
	 * Adds a compatible extjs version
	 * @param version version name (only major and minor version number, f.e. "4.1")
	 * @throws CoreException thrown for invalid versions or if the library is builtin
	 */
	void addCompatibleVersion(IProjectFacetVersion version) throws CoreException;
	
	/**
	 * Removes an existing compatible extjs version
	 * @param version version name (only major and minor version number, f.e. "4.1")
	 * @throws CoreException thrown for invalid versions or if the library is builtin
	 */
	void removeCompatibleVersion(IProjectFacetVersion version) throws CoreException;
	
	/**
	 * Replaces the compatible versions
	 * @param versions the new version names (only major and minor version number, f.e. "4.1")
	 * @throws CoreException thrown for invalid versions or if the library is builtin
	 */
	void setCompatibleVersions(IProjectFacetVersion[] versions) throws CoreException;
	
	// *** contents
	
	/**
	 * Returns the library sources
	 * @return library sources
	 * @throws CoreException 
	 */
	ILibrarySource[] getSources() throws CoreException;
	
	/**
	 * Removes all sources
	 * @throws CoreException thrown if the library is builtin (not changeable)
	 */
	void removeAllSources() throws CoreException;
	
	/**
	 * Removes given source
	 * @param source
	 * @throws CoreException thrown if the library is builtin (not changeable)
	 */
	void removeSource(ILibrarySource source) throws CoreException;
	
	/**
	 * Adds a source for a single javascript file
	 * @param pathToFile path to the javascript file; either a workspace path or a file system path
	 * @return
	 * @throws CoreException thrown if the library is builtin (not changeable)
	 */
	ILibrarySource createSourceFile(IPath pathToFile) throws CoreException;
	
	/**
	 * Adds a new folder source
	 * @param pathToFolder path to the javascript folder; either a workspace path or a file system path
	 * @param exclusions inclusion patterns
	 * @param inclusions exclusion patterns
	 * @return
	 * @throws CoreException thrown if the library is builtin (not changeable)
	 */
	ILibrarySource createFolder(IPath pathToFolder, String[] exclusions, String[] inclusions) throws CoreException;
	
	/**
	 * Adds a new zip source
	 * @param pathToFolder path to the zip archive; either a workspace path or a file system path
	 * @param exclusions inclusion patterns
	 * @param inclusions exclusion patterns
	 * @return
	 * @throws CoreException thrown if the library is builtin (not changeable)
	 */
	ILibrarySource createZip(IPath pathToZip, String[] exclusions, String[] inclusions) throws CoreException;
	
}
