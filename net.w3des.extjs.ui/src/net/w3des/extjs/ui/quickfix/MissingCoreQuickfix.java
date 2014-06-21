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
package net.w3des.extjs.ui.quickfix;

import net.w3des.extjs.ui.discovery.ExtJSDiscovery;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;

/**
 * A quickfix to help discovering extjs core plugins from remote repositories
 * 
 * @author mepeisen
 */
public class MissingCoreQuickfix implements IMarkerResolution {

	private String version;

	/**
	 * @param version
	 */
	public MissingCoreQuickfix(String version) {
		this.version = version;
	}

	@Override
	public String getLabel() {
		return "Discover extjs-sdk for version " + version;
	}

	@Override
	public void run(IMarker marker) {
		ExtJSDiscovery.runExtJSDiscoveryDialog(version);
	}

}
