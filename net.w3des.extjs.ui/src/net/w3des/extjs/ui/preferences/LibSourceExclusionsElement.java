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

public class LibSourceExclusionsElement implements ILibListElement {

	private LibSourceElement lib;
	private String name;
	private boolean exclusionsChanged = false;
	private List<String> exclusions = new ArrayList<String>();

	public LibSourceExclusionsElement(LibSourceElement sourceElement) {
		this.lib = sourceElement;
		if (sourceElement.getSource() != null) {
			for (final String e : sourceElement.getSource().getExclusions()) {
				this.exclusions.add(e);
			}
		}
		this.setName();
	}
	
	private void setName() {
		final StringBuilder builder = new StringBuilder();
		builder.append("exclusions: ");
		if (exclusions.size() == 0) {
			builder.append("-");
		}
		else {
			boolean first = true;
			for (final String e : exclusions) {
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
	 * @return the exclusionsChanged
	 */
	public boolean isExclusionsChanged() {
		return exclusionsChanged;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	public void setExclusions(String[] exclusions) {
		this.exclusions.clear();
		for (final String e : exclusions) {
			this.exclusions.add(e);
		}
		this.exclusionsChanged = true;
		this.setName();
	}
	
	public String[] getExclusions() {
		return this.exclusions.toArray(new String[this.exclusions.size()]);
	}

}
