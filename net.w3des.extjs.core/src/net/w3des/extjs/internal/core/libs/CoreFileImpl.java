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
package net.w3des.extjs.internal.core.libs;

import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.core.api.LibrarySourceType;

import org.eclipse.core.runtime.IPath;

public class CoreFileImpl implements ILibrarySource {

	private IPath fullPath;

	public CoreFileImpl(IPath fullPath) {
		this.fullPath = fullPath;
	}

	@Override
	public LibrarySourceType getSourceType() {
		return LibrarySourceType.JAVASCRIPT_FILE;
	}

	@Override
	public IPath getFullPath() {
		return this.fullPath;
	}

	@Override
	public String[] getExclusions() {
		return new String[0];
	}

	@Override
	public String[] getInclusions() {
		return new String[0];
	}

}
