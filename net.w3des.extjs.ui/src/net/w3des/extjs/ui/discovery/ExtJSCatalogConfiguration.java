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
package net.w3des.extjs.ui.discovery;

import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogConfiguration;

/**
 * @author mepeisen
 */
@SuppressWarnings("restriction")
public class ExtJSCatalogConfiguration extends CatalogConfiguration {
	
	private String version;
	
	public enum Type {
		ExtJSSdk,
		SenchaTouchSdk,
	}
	
	private Type type;

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
