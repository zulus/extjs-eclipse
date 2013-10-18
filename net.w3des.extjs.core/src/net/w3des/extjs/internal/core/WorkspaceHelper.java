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
package net.w3des.extjs.internal.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

public class WorkspaceHelper {
    private static void touch(IResource[] res, IProgressMonitor monitor) throws CoreException {
        for (IResource r : res) {
            if (r instanceof IFolder) {
                touch(((IFolder) r).members(), monitor);
            } else if (r.getName().contains(".js")) {
                r.touch(monitor);
            } else if (!(r instanceof IFile)) {
                ExtJSCore.warn("Invalid class: " + r.getClass().getName());
            }
        }
    }

    public static void refreshJavaScriptProject(IJavaScriptProject jsProject, IProgressMonitor monitor) throws JavaScriptModelException, CoreException {
        final IProject project = jsProject.getProject();
        for (IIncludePathEntry e : jsProject.getResolvedIncludepath(true)) {
            if (e.getEntryKind() != IIncludePathEntry.CPE_SOURCE) {
                continue;
            }

            if (project.getFullPath().equals(e.getPath())) {
                touch(project.members(false), monitor);
                continue;
            }

            IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(e.getPath());
            touch(folder.members(false), monitor);
        }
    }
}
