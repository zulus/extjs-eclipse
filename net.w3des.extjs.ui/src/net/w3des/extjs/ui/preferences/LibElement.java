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
import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.core.api.LibrarySourceType;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * @author mepeisen
 *
 */
public class LibElement implements ILibListElement {

	private IExtJSLibrary lib;
	
	private boolean isNew = false;
	
	private boolean isDeleted = false;
	
	private String name;
	
	private boolean nameChanged = false;
	
	private List<ILibListElement> children = new ArrayList<ILibListElement>();
	
	/** includes deleted children */
	private List<ILibListElement> allChildren = new ArrayList<ILibListElement>();
	
	public LibElement(IExtJSLibrary lib) {
		this.lib = lib;
		this.name = lib.getName();
		try {
			for (final ILibrarySource source : lib.getSources()) {
				this.children.add(new LibSourceElement(this, source));
			}
		}
		catch (CoreException ex) {
			ExtJSCore.error(ex);
		}
		for (final String versionName : lib.getCompatibleVersionNames()) {
			this.children.add(new LibVersionElement(this, versionName, false));
		}
		this.allChildren.addAll(this.children);
	}
	
	public LibElement(String name, String[] versions) {
		this.name = name;
		this.isNew = true;
		for (final String versionName : versions) {
			this.children.add(new LibVersionElement(this, versionName, true));
		}
		this.allChildren.addAll(this.children);
	}

	@Override
	public IExtJSLibrary getLibrary() {
		return this.lib;
	}

	@Override
	public boolean isDeleted() {
		return this.isDeleted;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}
	
	public void delete() {
		this.isDeleted = true;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isNameChanged() {
		return this.nameChanged;
	}
	
	public void setName(String name) {
		this.name = name;
		this.nameChanged = true;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public Object[] getChildren() {
		return this.children.toArray();
	}
	
	public Object[] getAllChildren() {
		return this.allChildren.toArray();
	}
	
	public LibVersionElement addVersion(String name) {
		final LibVersionElement child = new LibVersionElement(this, name, true);
		this.children.add(child);
		this.allChildren.add(child);
		return child;
	}
	
	public LibSourceElement addSource(LibrarySourceType type, IPath path, String[] inclusions, String[] exclusions) {
		final LibSourceElement child = new LibSourceElement(this, type, path, inclusions, exclusions);
		this.children.add(child);
		this.allChildren.add(child);
		return child;
	}
	
	public void remove(ILibListElement element) {
		this.children.remove(element);
		if (element.isNew()) {
			this.allChildren.remove(element);
		}
	}

	public void setVersions(String[] selectedVersions) {
		// first remove all
		for (final ILibListElement elm : this.children.toArray(new ILibListElement[this.children.size()])) {
			if (elm instanceof LibVersionElement) {
				((LibVersionElement) elm).delete();
				this.children.remove(elm);
				if (elm.isNew()) {
					this.allChildren.remove(elm);
				}
			}
		}
		
		// add new and recover those that we find in the list
		for (final String version : selectedVersions) {
			boolean found = false;
			for (final ILibListElement elm : this.allChildren) {
				if (elm instanceof LibVersionElement && version.equals(elm.getName())) {
					found = true;
					if (elm.isDeleted()) {
						// recover
						this.children.add(elm);
						((LibVersionElement) elm).setDeleted(false);
					}
				}
			}
			if (!found) {
				final LibVersionElement v = new LibVersionElement(this, version, true);
				this.allChildren.add(v);
				this.children.add(v);
			}
		}
	}

}
