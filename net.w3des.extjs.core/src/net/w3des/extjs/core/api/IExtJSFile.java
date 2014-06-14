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

/**
 * Base interface to access the file model of extjs indexed files
 * 
 * @author mepeisen
 */
public interface IExtJSFile {
	
	/**
	 * Cleans all aliases of this extjs files
	 */
	void cleanAliases();

    /**
     * Add alias by prefix
     */
    void addAlias(String name, int sourceStart, int sourceEnd, String type);
    
    /**
     * Returns the alias definitions within this file
     * @return alias definitions
     */
    Collection<IAlias> getAliases();

}
