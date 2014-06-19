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
package net.w3des.extjs.core.test.env;

import static org.junit.Assert.assertEquals;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.core.test.Util;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.junit.Before;
import org.junit.Test;

public class Env41Test {

    private IProject project = null;

    @Before
    public void createProject() throws CoreException {
        project = Util.createJSProject("env-project-41", true);
    }

    @Test()
    public void envChangedOnVersionInstall() throws CoreException {
    	IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, null);
    	final IProjectFacetVersion facet41 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT).getVersion("4.1");
        facetedProject.installProjectFacet(facet41, null, null);
        
        IExtJSProject prj = ExtJSCore.getProjectManager().createProject(project);
        assertEquals(prj.getEnvironmentName(), ExtJSCore.getLibraryManager().getDefaultEnvName(facet41));
    }

}
