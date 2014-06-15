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
package net.w3des.extjs.internal.core.project;

import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * Internal API; Common storage to access the project index
 *  
 * @author mepeisen
 */
public interface IIndexStorage {
	
	/**
	 * Activates/initializes the storage
	 */
	void activate();
	
	/**
	 * Returns true if the given project is known in the index
	 * @param project
	 * @return
	 */
	boolean hasProject(IProject project);
	
	/**
	 * Returns the project; creates it on demand
	 * @param prj the eclipse resource
	 * @param forceCreation {@code true} to create the project if it does not exist; {@code false} to return {@code null} instead
	 * @return the project or {@code null} if it was not found
	 */
	IExtJSIndex getProject(IProject prj, boolean forceCreation);

	/**
	 * Destroys the index for given eclipse project
	 * @param project
	 */
	void removeProject(IProject project);
	
	/**
	 * Notifies the index on a file removal
	 * @param file
	 */
	void notifyFileRemoval(IFile file);

	/**
	 * Updates the project name after change
	 * @param project
	 */
	void updateProjectName(IProject project);
	
	/**
	 * Returns the available projects
	 * @return
	 */
	IExtJSIndex[] getProjects();

	/**
	 * Saves the index
	 */
	void save();
	
	/**
	 * Returns the file index; creates it on demand
	 * @param filePath the full path to the file (file system path)
	 * @param forceCreation {@code true} to create the project if it does not exist; {@code false} to return {@code null} instead
	 * @return the file or {@code null} if it was not found
	 */
	IExtJSFile getFile(String filePath, boolean forceCreation);

}
