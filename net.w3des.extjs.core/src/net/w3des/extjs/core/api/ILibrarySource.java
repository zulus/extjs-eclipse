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

import org.eclipse.core.runtime.IPath;

/**
 * Library source component
 * 
 * @author mepeisen
 */
public interface ILibrarySource {
	
	/**
	 * Returns the javascript source type
	 * @return source type
	 */
	LibrarySourceType getSourceType();
	
	/**
	 * Returns the full path of the source file
	 * @return
	 */
	IPath getFullPath();
	
	/**
	 * Returns the file exclusion patterns
	 * @return
	 */
	String[] getExclusions();
	
	/**
	 * Returns the file inclusion patterns
	 * @return
	 */
	String[] getInclusions();

}
