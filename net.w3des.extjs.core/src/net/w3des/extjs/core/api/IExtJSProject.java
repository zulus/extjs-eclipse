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
	 * Returns the eclipse project
	 * @return
	 */
	IProject getProject();
	
	/**
	 * Returns the current project version
	 * @return
	 * @throws CoreException
	 */
	IProjectFacetVersion getVersion() throws CoreException;
	
	/**
	 * Changes the project version
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
	
}
