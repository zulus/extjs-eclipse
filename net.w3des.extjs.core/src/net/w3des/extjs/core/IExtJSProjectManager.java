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
package net.w3des.extjs.core;

import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSProject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public interface IExtJSProjectManager {
    public IExtJSProject createProject(IProject project);

    public IExtJSProject createProject(IProject project, boolean force);

    public void deleteProject(IProject project);

    public boolean isExtJSProject(IProject project);

    public boolean onBuildPath(IResource resource);

    public IExtJSProject[] getProjects();

    public IExtJSFile getFile(String file);

    public void save();
}
