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
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class CoreDefaultFVElement {

	private IProjectFacetVersion facetVersion;
	private List<CoreDefaultLibElement> children = new ArrayList<CoreDefaultLibElement>();
	
	public CoreDefaultFVElement(IProjectFacetVersion facetVersion) {
		this.facetVersion = facetVersion;
	}

	@Override
	public String toString() {
		return (ExtJSNature.getExtjsFacet().equals(this.facetVersion.getProjectFacet()) ? "extjs/" : "touch/") + this.facetVersion.getVersionString();
	}

	public IProjectFacetVersion getFacetVersion() {
		return facetVersion;
	}

	public void addChild(IExtJSCoreLibrary coreLib) {
		final IExtJSCoreLibrary defaultLib = ExtJSCore.getLibraryManager().getDefaultCoreLib(this.facetVersion);
		this.children.add(new CoreDefaultLibElement(this, coreLib.getName().equals(defaultLib.getName()), coreLib));
	}
	
	public CoreDefaultLibElement[] getChildren() {
		return this.children.toArray(new CoreDefaultLibElement[this.children.size()]);
	}
	
}
