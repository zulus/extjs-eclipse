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
package net.w3des.extjs.ui.handler;

import javax.inject.Named;

import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.WorkspaceHelper;
import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

public class RefreshJS {
    @Execute
    public static void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection, IProgressMonitor monitor) {
        final IAdaptable adaptable = (IAdaptable) selection.getFirstElement();
        final IProject project = ((IResource) adaptable.getAdapter(IResource.class)).getProject();
        try {
            IExtJSProject pr = ExtJSCore.getProjectManager().createProject(project);
            if (pr != null && pr.getFiles().size() > 0) {
                pr.clearIndex();
            }
            WorkspaceHelper.refreshJavaScriptProject(JavaScriptCore.create(project), monitor);
        } catch (JavaScriptModelException e) {
            ExtJSUI.error(e);
        } catch (CoreException e) {
            ExtJSUI.error(e);
        }

    }
}
