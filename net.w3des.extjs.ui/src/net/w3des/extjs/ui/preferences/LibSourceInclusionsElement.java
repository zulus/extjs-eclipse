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

import java.util.ArrayList;
import java.util.List;

import net.w3des.extjs.core.api.IExtJSLibrary;

public class LibSourceInclusionsElement implements ILibListElement {

	private LibSourceElement lib;
	private String name;
	private boolean inclusionsChanged = false;
	private List<String> inclusions = new ArrayList<String>();

	public LibSourceInclusionsElement(LibSourceElement sourceElement) {
		this.lib = sourceElement;
		if (sourceElement.getSource() != null) {
			for (final String e : sourceElement.getSource().getInclusions()) {
				this.inclusions.add(e);
			}
		}
		this.setName();
	}
	
	private void setName() {
		final StringBuilder builder = new StringBuilder();
		builder.append("inclusions: ");
		if (inclusions.size() == 0) {
			builder.append("*.*");
		}
		else {
			boolean first = true;
			for (final String e : inclusions) {
				if (first) first = false;
				else builder.append(", ");
				builder.append(e);
			}
		}
		this.name = builder.toString();
	}

	/**
	 * @return the lib
	 */
	public LibSourceElement getParent() {
		return this.lib;
	}

	@Override
	public IExtJSLibrary getLibrary() {
		return this.lib.getLibrary();
	}

	@Override
	public boolean isDeleted() {
		return false;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isNameChanged() {
		return false;
	}
	
	/**
	 * @return the inclusionsChanged
	 */
	public boolean isInclusionsChanged() {
		return inclusionsChanged;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	public void setInclusions(String[] inclusions) {
		this.inclusions.clear();
		for (final String e : inclusions) {
			this.inclusions.add(e);
		}
		this.inclusionsChanged = true;
		this.setName();
	}
	
	public String[] getInclusions() {
		return this.inclusions.toArray(new String[this.inclusions.size()]);
	}

}
