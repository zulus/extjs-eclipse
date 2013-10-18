/*******************************************************************************
 * Copyright (c) 20013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.builder.ExtJSBuilder;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class ExtJSNature implements IProjectNature {

    public static final String NATURE_ID = "net.w3des.extjs.core.extjsNature"; //$NON-NLS-1$
    private IProject project;

    @Override
    public void configure() throws CoreException {
        if (hasBuilder()) {
            return;
        }

        final IProjectDescription desc = project.getDescription();
        final ICommand builder = desc.newCommand();
        builder.setBuilderName(ExtJSBuilder.ID);

        final List<ICommand> list = new LinkedList<ICommand>();
        list.addAll(Arrays.asList(desc.getBuildSpec()));
        list.add(builder);

        desc.setBuildSpec(list.toArray(new ICommand[list.size()]));
        project.setDescription(desc, null);

    }

    private boolean hasBuilder() {
        try {
            for (ICommand cmd : project.getDescription().getBuildSpec()) {
                if (ExtJSBuilder.ID.equals(cmd.getBuilderName())) {
                    return true;
                }
            }
        } catch (CoreException e) {
            ExtJSCore.error(e);
        }

        return false;
    }

    @Override
    public void deconfigure() throws CoreException {
        if (!hasBuilder()) {
            return;
        }

        final IProjectDescription desc = project.getDescription();
        final LinkedList<ICommand> list = new LinkedList<ICommand>();
        final Iterator<ICommand> iterator = list.iterator();
        while (iterator.hasNext()) {
            ICommand builder = iterator.next();
            if (ExtJSBuilder.ID.equals(builder.getBuilderName())) {
                list.remove(builder);
            }
        }

        desc.setBuildSpec(list.toArray(new ICommand[list.size()]));
        project.setDescription(desc, null);

    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
