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
package net.w3des.extjs.internal.core.infer;

import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.infer.IInferEngine;
import org.eclipse.wst.jsdt.core.infer.IInferenceFile;
import org.eclipse.wst.jsdt.core.infer.InferrenceProvider;
import org.eclipse.wst.jsdt.core.infer.RefactoringSupport;
import org.eclipse.wst.jsdt.core.infer.ResolutionConfiguration;

public class InferProvider implements InferrenceProvider {
    final public static String ID = "net.w3des.extjs.core.inference"; //$NON-NLS-1$

    @Override
    public IInferEngine getInferEngine() {
        final InferEngine eng = new InferEngine();
        eng.inferenceProvider = this;

        return eng;
    }

    @Override
    public int applysTo(final IInferenceFile scriptFile) {
        final char[] fileName = scriptFile.getFileName();
        if (fileName == null) {
            return InferrenceProvider.NOT_THIS;
        }

        final IPath path = new Path(new String(fileName));
        final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        final IResource member = workspaceRoot.findMember(path);
        if (member == null) {
        	// check for library
        	if (ExtJSCore.getLibraryManager().isLibraryFile(path)) {
                return InferrenceProvider.ONLY_THIS;
        	}
            return InferrenceProvider.NOT_THIS;
        }

        final IProject project = member.getProject();
        if (project == null) {
            return InferrenceProvider.NOT_THIS;
        }

        if (!ExtJSCore.getProjectManager().isExtJSProject(project)) {
            return InferrenceProvider.NOT_THIS;
        }

        if (ExtJSCore.getProjectManager().onBuildPath(member)) {
            return InferrenceProvider.ONLY_THIS;
        }

        return InferrenceProvider.MAYBE_THIS;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public ResolutionConfiguration getResolutionConfiguration() {
        return new ResolutionConfiguration();
    }

    /**
     * TODO create refactoring for ExtJS classes
     */
    @Override
    public RefactoringSupport getRefactoringSupport() {
        return new RefactoringSupport();
    }

}
