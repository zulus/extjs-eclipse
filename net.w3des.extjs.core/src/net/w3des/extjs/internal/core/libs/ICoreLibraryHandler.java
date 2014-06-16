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

import java.io.IOException;

/**
 * @author mepeisen
 *
 */
interface ICoreLibraryHandler
{
    
    /**
     * Returns the library version
     * @return 
     */
    String getLibraryVersion();
    
    /**
     * Opens the file "ext.js" for reading
     * @return
     * @throws IOException
     */
    String openExtJs() throws IOException;
    
    /**
     * Opens the file "ext-all.js" for reading
     * @return
     * @throws IOException
     */
    String[] openExtAllJs() throws IOException;
    
    /**
     * Opens the file "ext-all-debug.js" for reading
     * @return
     * @throws IOException
     */
    String[] openExtAllDebugJs() throws IOException;
    
    /**
     * Lists the available locales
     * @return
     * @throws IOException
     */
    String[] getAvailableLocales() throws IOException;
    
    /**
     * Opens the locale file for given locale
     * @param name
     * @return
     * @throws IOException
     */
    String[] openLocale(String name) throws IOException;
    
}
