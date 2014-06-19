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

public class EnvVersionElement implements IEnvListElement {

	private EnvElement env;
	private String name;
	private boolean nameChanged = false;
	private boolean isNew;
	private boolean isDeleted;

	public EnvVersionElement(EnvElement envElement, String versionName, boolean isNew) {
		this.env = envElement;
		this.name = versionName;
		this.isNew = isNew;
	}

	@Override
	public IExtJSEnvironment getEnvironment() {
		return this.env.getEnvironment();
	}

	@Override
	public boolean isDeleted() {
		return this.isDeleted;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isNameChanged() {
		return this.nameChanged;
	}
	
	public void delete() {
		this.isDeleted = true;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.nameChanged = true;
	}
	
	public EnvElement getParent() {
		return this.env;
	}

	public void setDeleted(boolean b) {
		this.isDeleted = b;
	}

}
