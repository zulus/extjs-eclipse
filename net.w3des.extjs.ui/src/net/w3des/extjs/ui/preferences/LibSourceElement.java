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

import org.eclipse.core.runtime.IPath;

public class LibSourceElement implements ILibListElement {

	private LibElement lib;
	private ILibrarySource source;
	private IPath path;
	private LibrarySourceType type;
	private boolean pathChanged = false;
	private boolean isNew;
	private boolean isDeleted;
	
	private List<ILibListElement> children = new ArrayList<ILibListElement>();

	public LibSourceElement(LibElement libElement, ILibrarySource source) {
		this.lib = libElement;
		this.source = source;
		this.type = source.getSourceType();
		this.path = source.getFullPath();
		this.children.add(new LibSourceInclusionsElement(this));
		this.children.add(new LibSourceExclusionsElement(this));
	}

	/**
	 * @param libElement
	 * @param type
	 * @param path2
	 * @param inclusions
	 * @param exclusions
	 */
	public LibSourceElement(LibElement libElement, LibrarySourceType type, IPath path, String[] inclusions, String[] exclusions) {
		this.lib = libElement;
		this.type = type;
		this.path = path;
		LibSourceInclusionsElement inc = new LibSourceInclusionsElement(this);
		inc.setInclusions(inclusions);
		this.children.add(inc);
		LibSourceExclusionsElement exc = new LibSourceExclusionsElement(this);
		exc.setExclusions(exclusions);
		this.children.add(exc);
	}

	@Override
	public IExtJSLibrary getLibrary() {
		return this.lib.getLibrary();
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
		return this.path.toString();
	}

	@Override
	public boolean isNameChanged() {
		return this.pathChanged;
	}
	
	public void delete() {
		this.isDeleted = true;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	public void setPath(IPath path) {
		this.path = path;
		this.pathChanged = true;
	}
	
	public LibElement getParent() {
		return this.lib;
	}

	public void setDeleted(boolean b) {
		this.isDeleted = b;
	}

	/**
	 * @return the source
	 */
	public ILibrarySource getSource() {
		return source;
	}

	/**
	 * @return the type
	 */
	public LibrarySourceType getType() {
		return type;
	}

	/**
	 * @return the children
	 */
	public ILibListElement[] getChildren() {
		return children.toArray(new ILibListElement[this.children.size()]);
	}

}
