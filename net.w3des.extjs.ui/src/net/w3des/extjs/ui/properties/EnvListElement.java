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
package net.w3des.extjs.ui.properties;

import net.w3des.extjs.core.api.IExtJSEnvironment;

/**
 * 
 * @author mepeisen
 */
public class EnvListElement implements IListElement {
	
	/** the selected env */
	private IExtJSEnvironment env;

	public EnvListElement(IExtJSEnvironment env) {
		this.env = env;
	}

	public IExtJSEnvironment getEnv() {
		return env;
	}

	public void setEnv(IExtJSEnvironment env) {
		this.env = env;
	}

	@Override
	public String getName() {
		return this.env.getName();
	}

}
