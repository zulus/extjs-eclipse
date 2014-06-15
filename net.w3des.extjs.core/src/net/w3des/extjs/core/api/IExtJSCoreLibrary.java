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

import java.util.Locale;

/**
 * Special extension to extjs libraries representing the extjs SDK
 * 
 * @author mepeisen
 */
public interface IExtJSCoreLibrary extends IExtJSLibrary {
	
	/**
	 * Returns the available locales
	 * @return
	 */
	Locale[] getAvailableLocales();
	
	/**
	 * Returns additional sources for given locale
	 * @param locale
	 * @return
	 */
	ILibrarySource[] getLocaleSources(Locale locale);
	
	/**
	 * Returns the major version number detected from folder/archive
	 * @return
	 */
	int getMajorVersion();
	
	/**
	 * Returns the minor version number detected from folder/archive
	 * @return
	 */
	int getMinorVersion();
	
	/**
	 * Returns the fix version number detected from folder/archive
	 * @return
	 */
	int getFixVersion();

}
