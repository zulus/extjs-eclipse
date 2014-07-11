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

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.api.CoreType;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;

/**
 * @author mepeisen
 *
 */
public class EnvElement implements IEnvListElement {

	private IExtJSEnvironment env;
	
	private boolean isNew = false;
	
	private boolean isDeleted = false;
	
	private String name;
	
	private boolean nameChanged = false;
	
	private List<IEnvListElement> children = new ArrayList<IEnvListElement>();
	
	/** includes deleted children */
	private List<IEnvListElement> allChildren = new ArrayList<IEnvListElement>();
	
	private boolean isCoreChanged = false;
	
	private String coreFileOrZip;
	
	private CoreType coreType = CoreType.NONE;

	public EnvElement(IExtJSEnvironment env) {
		this.env = env;
		this.name = env.getName();
		for (final String libName : env.getLibraryNames()) {
			this.children.add(new EnvLibElement(this, libName, false));
		}
		for (final String versionName : env.getCompatibleVersionNames(ExtJSNature.getExtjsFacet())) {
			this.children.add(new EnvVersionElement(this, "extjs/" + versionName, false));
		}
		for (final String versionName : env.getCompatibleVersionNames(ExtJSNature.getSenchaTouchFacet())) {
			this.children.add(new EnvVersionElement(this, "touch/" + versionName, false));
		}
		this.allChildren.addAll(this.children);
		try {
			if (env.getCore() != null) {
				this.coreType = env.getCore().getCoreType();
				this.coreFileOrZip = env.getCore().getPath();
			}
		}
		catch (CoreException ex) {
			ExtJSCore.error(ex);
		}
	}
	
	public EnvElement(String name, String[] versions) {
		this.name = name;
		this.isNew = true;
		for (final String versionName : versions) {
			this.children.add(new EnvVersionElement(this, versionName, true));
		}
		this.allChildren.addAll(this.children);
	}

	@Override
	public IExtJSEnvironment getEnvironment() {
		return this.env;
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
	
	public EnvVersionElement addVersion(String name) {
		final EnvVersionElement child = new EnvVersionElement(this, name, true);
		this.children.add(child);
		this.allChildren.add(child);
		return child;
	}
	
	public EnvLibElement addLibrary(String name) {
		final EnvLibElement child = new EnvLibElement(this, name, true);
		this.children.add(child);
		this.allChildren.add(child);
		return child;
	}
	
	public void remove(IEnvListElement element) {
		this.children.remove(element);
		if (element.isNew()) {
			this.allChildren.remove(element);
		}
	}

	public boolean isCoreChanged() {
		return isCoreChanged;
	}

	public String getCoreFileOrZip() {
		return coreFileOrZip;
	}

	public CoreType getCoreType() {
		return coreType;
	}
	
	public void setCoreNone() {
		this.coreType = CoreType.NONE;
		this.isCoreChanged = true;
	}
	
	public void setCoreZip(String path) {
		this.coreType = CoreType.ZIP;
		this.coreFileOrZip = path;
		this.isCoreChanged = true;
	}
	
	public void setCoreFolder(String path) {
		this.coreType = CoreType.FOLDER;
		this.coreFileOrZip = path;
		this.isCoreChanged = true;
	}

	public void setVersions(String[] selectedVersions) {
		// first remove all
		for (final IEnvListElement elm : this.children.toArray(new IEnvListElement[this.children.size()])) {
			if (elm instanceof EnvVersionElement) {
				((EnvVersionElement) elm).delete();
				this.children.remove(elm);
				if (elm.isNew()) {
					this.allChildren.remove(elm);
				}
			}
		}
		
		// add new and recover those that we find in the list
		for (final String version : selectedVersions) {
			boolean found = false;
			for (final IEnvListElement elm : this.allChildren) {
				if (elm instanceof EnvVersionElement && version.equals(elm.getName())) {
					found = true;
					if (elm.isDeleted()) {
						// recover
						this.children.add(elm);
						((EnvVersionElement) elm).setDeleted(false);
					}
				}
			}
			if (!found) {
				final EnvVersionElement v = new EnvVersionElement(this, version, true);
				this.allChildren.add(v);
				this.children.add(v);
			}
		}
	}

	public boolean isOfType(IProjectFacet facet) {
		if (this.env != null) {
			return this.env.isOfType(facet);
		}
		final String prefix = facet.equals(ExtJSNature.getExtjsFacet()) ? "extjs/" : "touch/";
		for (final Object child : this.getChildren()) {
			if (child instanceof EnvVersionElement) {
				if (((EnvVersionElement) child).getName().startsWith(prefix)) {
					return true;
				}
			}
		}
		return false;
	}

}
