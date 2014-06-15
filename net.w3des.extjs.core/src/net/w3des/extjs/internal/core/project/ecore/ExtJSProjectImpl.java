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
package net.w3des.extjs.internal.core.project.ecore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;

import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.model.basic.ExtJSProject;
import net.w3des.extjs.core.model.basic.File;

public class ExtJSProjectImpl implements IExtJSIndex {
	
	private ExtJSProject project;
	private IProject eclipseProject;

	public ExtJSProjectImpl(IProject eclipseProject, ExtJSProject project) {
		this.project = project;
		this.eclipseProject = eclipseProject;
	}

	@Override
	public Collection<IExtJSFile> getFiles() {
		final List<IExtJSFile> result = new ArrayList<IExtJSFile>();
		if (this.project != null) {
			for (final File file : this.project.getFiles()) {
				// wrap contents
				result.add(new ExtJSFile(file));
			}
		}
		return result;
	}

	@Override
	public void clearIndex() {
		this.project.getFiles().clear();
	}

	@Override
	public IProject getProject() {
		return this.eclipseProject;
	}

}
