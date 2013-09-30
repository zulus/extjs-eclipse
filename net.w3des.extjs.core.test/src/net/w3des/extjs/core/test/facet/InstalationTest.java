package net.w3des.extjs.core.test.facet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import net.w3des.extjs.core.test.Util;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InstalationTest {

    private IProject project = null;
    private IFile file = null;

    final private String name = "test.js";
    final private String contents = "Ext.define('test', {});";

    @Before
    public void createProject() throws CoreException {
        project = Util.createJSProject("facet-project", true);
        file = project.getFile(name);
        if (file.exists()) {
            file.setContents(new ByteArrayInputStream(contents.getBytes()), IResource.FORCE, null);
        } else {
            file.create(new ByteArrayInputStream(contents.getBytes()), IResource.FORCE, null);
        }

    }

    @After
    public void deleteProject() throws CoreException {
        Util.deleteProject(project);
    }

    @Test()
    public void typeNotExists() {
        IJavaScriptUnit compilationUnit = JavaScriptCore.createCompilationUnitFrom(file);
        assertFalse(compilationUnit.getType("type").exists());
    }

    @Test()
    public void activation() throws CoreException {
        JavaScriptCore.createCompilationUnitFrom(file); // create
                                                        // compilationUnit

        IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, null);
        facetedProject.installProjectFacet(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT).getVersion("4.1"), null, null);
        IType type = JavaScriptCore.createCompilationUnitFrom(file).getType("test");

        assertTrue(type.exists());
        assertEquals("test", type.getFullyQualifiedName());
    }

}
