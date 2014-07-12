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

import net.w3des.extjs.core.api.IExtJSCoreLibrary;

public class CoreDefaultLibElement {

	private CoreDefaultFVElement facetVersion;
	private boolean isDefault;
	private IExtJSCoreLibrary coreLib;
	
	public CoreDefaultLibElement(CoreDefaultFVElement facetVersion,
			boolean isDefault, IExtJSCoreLibrary coreLib) {
		this.facetVersion = facetVersion;
		this.isDefault = isDefault;
		this.coreLib = coreLib;
	}

	@Override
	public String toString() {
		return this.coreLib.getName();
	}

	public CoreDefaultFVElement getParent() {
		return this.facetVersion;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public IExtJSCoreLibrary getCoreLib() {
		return coreLib;
	}

}
