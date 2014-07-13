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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Base interface to access the extjs project index
 * 
 * @author mepeisen
 */
public interface IExtJSProject {

	/**
	 * Returns the extjs index
	 * @return extjs index
	 */
	IExtJSIndex getIndex();
	
	/**
	 * Returns all environment libraries/ core libraries and user defined libraries added to this project
	 * @return
	 */
	IExtJSLibrary[] getLibraries();
	
	/**
	 * gets a cumulated index over the project itself and all libraries
	 * @return
	 */
	IExtJSIndex getCumulatedIndex();
	
	/**
	 * Returns the eclipse project
	 * @return
	 */
	IProject getProject();
	
	/**
	 * Checks if the project is of given type
	 * @param type
	 * @return
	 * @throws CoreException 
	 */
	boolean isOfType(ProjectType type) throws CoreException;
	
	/**
	 * Refresh the extjs library classpath containers
	 * @throws CoreException
	 */
	void refreshLibContainer() throws CoreException;
	
	/**
	 * Returns the current project version of extjs facet
	 * @return
	 * @throws CoreException
	 */
	IProjectFacetVersion getVersion() throws CoreException;
	
	/**
	 * Returns the current project version of touch facet
	 * @return
	 * @throws CoreException
	 */
	IProjectFacetVersion getTouchVersion() throws CoreException;
	
	/**
	 * Changes the project version (either extjs or touch facet)
	 * @param version
	 * @throws CoreException
	 */
	void setVersion(IProjectFacetVersion version) throws CoreException;
	
	// *** environment
	
	/**
	 * Returns the name of the environment that should be used
	 * @return environment name
	 */
	String getEnvironmentName() throws CoreException;
	
	/**
	 * Sets the environment name
	 * @param name
	 * @throws CoreException thrown if the environment is unknown or if it is not compatible to used extjs version
	 */
	void setEnvironmentName(String name) throws CoreException;
	
	// *** extjs libraries
	
	/**
	 * Returns the libraries to be used
	 * @return
	 * @throws CoreException 
	 */
	String[] getLibraryNames() throws CoreException;
	
	/**
	 * Adds a library
	 * @param name
	 * @throws CoreException thrown if the library is not known or if it is not compatible to used extjs version
	 */
	void addLibrary(String name) throws CoreException;
	
	/**
	 * Removes a library
	 * @param name
	 * @throws CoreException thrown if the library is not known or not associated with this project
	 */
	void removeLibrary(String name) throws CoreException;
	
	/**
	 * Sets the libraries
	 * @param libraries
	 * @throws CoreException thrown if the one of the libraries is not known or if it is not compatible to used extjs version
	 */
	void setLibraries(String[] libraries) throws CoreException;
	
	// *** extjs options
	
	/**
	 * Returns the extjs namespace to be used within the project
	 * @return javascript namespace
	 * @throws CoreException
	 */
	String getExtjsNamespace() throws CoreException;
	
	/**
	 * Sets the extjs namespace
	 * @param namespace
	 * @throws CoreException
	 */
	void setExtjsNamespace(String namespace) throws CoreException;
	
	/**
	 * Returns the primary source folder where the extjs classes will be placed into
	 * @return
	 * @throws CoreException
	 */
	IContainer getSourceFolder() throws CoreException;
	
	/**
	 * Sets the primary source folder
	 * @param folder
	 * @throws CoreException
	 */
	void setSourceFolder(IContainer folder) throws CoreException;
	
}
