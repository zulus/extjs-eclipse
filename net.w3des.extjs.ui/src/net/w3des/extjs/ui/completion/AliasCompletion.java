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
import net.w3des.extjs.core.api.ILayout;
import net.w3des.extjs.core.api.IPlugin;
import net.w3des.extjs.core.api.IWidget;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.SharedImages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.jsdt.ui.text.java.JavaContentAssistInvocationContext;

public class AliasCompletion extends AbstractCompletion {
    public AliasCompletion() {
        super(new String[] { "Ext.createByAlias(" });
    }

    public AliasCompletion(String[] before) {
        super(before);
    }

    @Override
    protected void computeCompletionProposals(boolean inString, String start, List<ICompletionProposal> proposals,
            JavaContentAssistInvocationContext context, IProgressMonitor monitor) {
        final IExtJSProject project = ExtJSCore.getProjectManager().createProject(context.getProject().getProject());

        for (final IExtJSFile f : project.getCumulatedIndex().getFiles()) {
            for (final IAlias a : f.getAliases()) {

                if (a.getRawName().startsWith(start)) {
                    proposals.add(createTextProposal(inString, aliasImage(a), a.getRawName(),
                            context.getInvocationOffset(), start, null, null));

                }
            }
        }
    }

    public static Image aliasImage(IAlias item) {
        if (item instanceof IWidget) {
            return SharedImages.getImage(SharedImages.OBJ16.WIDGET);
        } else if (item instanceof IPlugin) {
            return SharedImages.getImage(SharedImages.OBJ16.PLUGIN);
        } else if (item instanceof ILayout) {
            return SharedImages.getImage(SharedImages.OBJ16.LAYOUT);
        } else if (item instanceof IFeature) {
            return SharedImages.getImage(SharedImages.OBJ16.FEATURE);
        }

        return SharedImages.getImage(SharedImages.OBJ16.ALIAS);
    }

}
