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
package net.w3des.extjs.internal.core.facet;

import java.util.Arrays;
import java.util.List;

import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.WorkspaceHelper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

public class CoreUninstall implements IDelegate {

    @Override
    public void execute(IProject project, IProjectFacetVersion version, Object config, IProgressMonitor monitor) throws CoreException {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }
        ExtJSCore.getProjectManager().deleteProject(project);
        final IJavaScriptProject jsProject = JavaScriptCore.create(project);
        
        this.removeCPC(jsProject, new Path(ExtJSCore.JSCPC_ENV_ID), monitor);
        this.removeCPC(jsProject, new Path(ExtJSCore.JSCPC_LIB_ID), monitor);

        try {
            WorkspaceHelper.refreshJavaScriptProject(JavaScriptCore.create(project), monitor);
        } finally {
            monitor.done();
        }
    }

	private void removeCPC(IJavaScriptProject jsProject, IPath path, IProgressMonitor monitor) throws JavaScriptModelException {
		final List<IIncludePathEntry> entries = Arrays.asList(jsProject.getRawIncludepath());
		for (final IIncludePathEntry entry : entries) {
			if (entry.getEntryKind() == IIncludePathEntry.CPE_CONTAINER && path.isPrefixOf(entry.getPath())) {
				entries.remove(entry);
				jsProject.setRawIncludepath(entries.toArray(new IIncludePathEntry[entries.size()]), monitor);
				return;
			}
		}
	}
}
