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
package net.w3des.extjs.ui.completion;

import java.util.List;

import net.w3des.extjs.core.api.IAlias;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.api.IFeature;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.jsdt.ui.text.java.JavaContentAssistInvocationContext;

public class FeatureCompletion extends AliasCompletion {
    public FeatureCompletion() {
        super(new String[] { "ftype:", ".ftype=" });
    }

    @Override
    protected void computeCompletionProposals(boolean inString, String start, List<ICompletionProposal> proposals,
            JavaContentAssistInvocationContext context, IProgressMonitor monitor) {
        final IExtJSProject project = ExtJSCore.getProjectManager().createProject(context.getProject().getProject());

        for (final IExtJSFile f : project.getCumulatedIndex().getFiles()) {
            for (final IAlias a : f.getAliases()) {

                if (a instanceof IFeature && a.getName().startsWith(start)) {
                    proposals.add(createTextProposal(inString, aliasImage(a), a.getName(),
                            context.getInvocationOffset(), start, null, null));

                }
            }
        }
    }
}
