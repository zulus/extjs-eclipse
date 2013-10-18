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
package net.w3des.extjs.core.test;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.test.Versioned.AfterSuite;
import net.w3des.extjs.core.test.Versioned.BeforeSuite;
import net.w3des.extjs.core.test.Versioned.ExtSuite;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

abstract public class Util {
    protected static List<String> dirs;
    protected static IProject project;
    protected static String root = "/workspace";

    protected ExtTFile file;

    @BeforeSuite
    public static Iterable<ExtTFile> beforeRun(ExtSuite cfg) throws Exception {
        // prepare
        List<String> dirs = new LinkedList<String>();
        List<ExtTFile> files = new LinkedList<ExtTFile>();

        String dir = cfg.dir();
        if (cfg.sub().length > 0) {
            for (String sub : cfg.sub()) {
                dirs.add("/" + sub + "/");
                for (String path : getFiles(root + "/" + dir + "/" + sub)) {
                    files.add(new ExtTFile(ExtJSCoreTest.getDefault().getBundle(), "/" + sub + "/", path));
                }
            }
        } else {
            dirs.add("/");
            for (String path : getFiles(root + dir)) {
                files.add(new ExtTFile(ExtJSCoreTest.getDefault().getBundle(), "/", path));
            }
        }

        parseDirs(dirs.toArray(new String[dirs.size()]));

        return files;
    }

    public static IProject createExtProject(ExtSuite cfg) throws CoreException {
        return createExtProject(cfg.dir(), cfg.version());
    }

    public static IProject createExtProject(String shortName, String version) throws CoreException {
        String name = shortName + "-ext" + version;

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
        project.create(null);
        project.open(null);

        IProjectDescription desc = project.getDescription();

        desc.setNatureIds(new String[] { "org.eclipse.wst.common.project.facet.core.nature", JavaScriptCore.NATURE_ID });

        project.setDescription(desc, null);

        IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, null);

        // install facets
        facetedProject.installProjectFacet(ProjectFacetsManager.getProjectFacet("wst.jsdt.web").getVersion("1.0"), null, null); // main
                                                                                                                                // JSDT
                                                                                                                                // facet
        facetedProject.installProjectFacet(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT).getVersion(version), null, null); // ExtJS
                                                                                                                                       // facet

        return project;
    }

    public static IProject createJSProject(String name, boolean facetNature) throws CoreException {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
        project.create(null);
        project.open(null);

        IProjectDescription desc = project.getDescription();

        if (facetNature) {
            desc.setNatureIds(new String[] { "org.eclipse.wst.common.project.facet.core.nature", JavaScriptCore.NATURE_ID });
            IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, null);
            facetedProject.installProjectFacet(ProjectFacetsManager.getProjectFacet("wst.jsdt.web").getVersion("1.0"), null, null); // main
                                                                                                                                    // JSDT
                                                                                                                                    // facet
        } else {
            desc.setNatureIds(new String[] { JavaScriptCore.NATURE_ID });
        }

        project.setDescription(desc, null);

        return project;
    }

    private static void createDir(String name) {
        if (name.equals("/")) {
            return;
        }

        String curr = "";
        for (String n : name.split("/")) {
            if (n.equals("")) {
                continue;
            }
            curr += n + "/";
            IFolder folder = project.getFolder(curr);
            if (!folder.exists()) {
                try {
                    folder.create(true, true, null);
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static void parseDirs(String[] dirs) throws JavaScriptModelException, CoreException {
        if (project != null) {

            IJavaScriptProject jsproject = null;
            if (project.hasNature(JavaScriptCore.NATURE_ID)) {
                jsproject = JavaScriptCore.create(project);
            }

            List<IIncludePathEntry> ent = new LinkedList<IIncludePathEntry>();
            for (String dir : dirs) {
                createDir(dir);
                if (jsproject != null) {
                    ent.add(JavaScriptCore.newSourceEntry(new Path(project.getLocation().toString() + dir)));
                }
            }
            if (jsproject != null) {
                jsproject.setRawIncludepath(ent.toArray(new IIncludePathEntry[ent.size()]), null);
            }
        }
    }

    private static List<String> getFiles(String testDir) {
        List<String> paths = new LinkedList<String>();
        Enumeration<String> entryPaths = ExtJSCoreTest.getDefault().getBundle().getEntryPaths(testDir);

        if (entryPaths != null) {
            while (entryPaths.hasMoreElements()) {
                final String path = entryPaths.nextElement();
                if (!path.endsWith(".jsdtt")) {
                    continue;
                }

                paths.add(path);
            }
        }

        return paths;
    }

    @AfterSuite
    public static void afterRun() throws Exception {
        if (project != null) {
            project.delete(true, true, null);
        }
    }

    public static void deleteProject(IProject project) throws CoreException {
        project.delete(true, true, null);
    }

    public Util(ExtTFile file) {
        this.file = file;
    }

}
