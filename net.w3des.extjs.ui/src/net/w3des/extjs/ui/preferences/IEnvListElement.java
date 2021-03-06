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
package net.w3des.extjs.ui.preferences;

import net.w3des.extjs.core.api.IExtJSEnvironment;

/**
 * Base interface for environment elements
 * @author mepeisen
 */
public interface IEnvListElement {
	
	/**
	 * Returns the underlying extjs environment (if any)
	 * @return
	 */
	IExtJSEnvironment getEnvironment();
	
	/**
	 * true for deleted elements
	 * @return
	 */
	boolean isDeleted();
	
	/**
	 * true for new elements
	 * @return
	 */
	boolean isNew();
	
	/**
	 * Returns the name
	 * @return
	 */
	String getName();
	
	/**
	 * Returns true for changed names
	 * @return
	 */
	boolean isNameChanged();

}
