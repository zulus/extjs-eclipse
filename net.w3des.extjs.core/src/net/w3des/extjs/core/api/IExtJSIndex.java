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

import java.util.Collection;

import org.eclipse.core.resources.IProject;

/**
 * Base interface to access the index on a set of files
 * 
 * @author mepeisen
 * @see IExtJSProject
 * @see IExtJSLibrary
 */
public interface IExtJSIndex {
	
	/**
	 * Returns all indexed files within this project
	 * @return
	 */
	Collection<IExtJSFile> getFiles();

	/**
	 * Clears the index
	 */
	void clearIndex();
	
	/**
	 * Returns the project if this is a project index
	 * @return eclipse project
	 */
	IProject getProject();

}
