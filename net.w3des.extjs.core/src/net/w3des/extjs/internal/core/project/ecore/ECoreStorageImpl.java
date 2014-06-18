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
package net.w3des.extjs.internal.core.project.ecore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.model.basic.ExtJSFactory;
import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.ExtJSProject;
import net.w3des.extjs.core.model.basic.File;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.project.IIndexStorage;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IPackageFragmentRoot;
import org.eclipse.wst.jsdt.core.JavaScriptCore;

public class ECoreStorageImpl implements IIndexStorage {
	
    private Map<IProject, ExtJSProject> projects;

    private Map<String, File> files;

	@Override
	public void activate() {
        projects = new HashMap<IProject, ExtJSProject>();
        files = new HashMap<String, File>();
        ExtJSPackage.eINSTANCE.eClass();
        try {
            final Resource resource = getResource();
            if (resource != null) {
                for (final Object o : resource.getContents()) {
                    if (o instanceof ExtJSProject) {
                        final ExtJSProject extProject = (ExtJSProject) o;
                        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(extProject.getName());
                        try {
                            if (project != null && isExtJSProject(project)) {
                                projects.put(project, extProject);
                                final Iterator<File> iterator = extProject.getFiles().iterator();
                                while (iterator.hasNext()) {
                                    final File item = iterator.next();
                                    if (!inProject(project, item.getName())) {
                                        iterator.remove();
                                        continue;
                                    }
                                    if (!files.containsKey(item.getName())) {
                                        files.put(item.getName(), item);
                                        extProject.getFiles().add(item);
                                    } else {
                                        extProject.getFiles().add(files.get(item.getName()));
                                        iterator.remove();
                                    }
                                }
                            } else {
                                ExtJSCore.warn(String.format("Project {0} haven't extjs support!", project.getName())); //$NON-NLS-1$
                            }
                        } catch (Throwable e) {
                            ExtJSCore.error(e);
                        }
                    }
                }
            }
        } catch (final Throwable e) {
            ExtJSCore.error(e);
        }
	}

    boolean isExtJSProject(final IProject project) {
        try {
            if (project.isAccessible()) {
                return project.hasNature(ExtJSNature.NATURE_ID);
            }

            return false;
        } catch (final CoreException e) {
            ExtJSCore.error(e);
            return false;
        }
    }

	@Override
	public boolean hasProject(IProject project) {
		return this.projects.containsKey(project);
	}

	@Override
	public IExtJSIndex getProject(IProject prj, boolean forceCreation) {
		if (!this.projects.containsKey(prj) && forceCreation) {
            final ExtJSProject extProject = ExtJSFactory.eINSTANCE.createExtJSProject();
            extProject.setName(prj.getName());

            projects.put(prj, extProject);
		}
		final ExtJSProject extProject = this.projects.get(prj);
		return extProject == null ? null : new ExtJSProjectImpl(prj, extProject);
	}

	@Override
	public void removeProject(IProject project) {
        if (projects.containsKey(project)) {
            projects.remove(project);
        }
	}

	@Override
	public void notifyFileRemoval(IFile file) {
        if (files.containsKey(file.getFullPath().toString())) {
            final File remove = files.remove(file.getFullPath().toString());
            for (final ExtJSProject pr : projects.values()) {
                if (pr.getFiles().contains(remove)) {
                    pr.getFiles().remove(remove);
                }
            }
        }
	}

	@Override
	public void updateProjectName(IProject project) {
        projects.get(project).setName(project.getName());
	}

	@Override
	public IExtJSIndex[] getProjects() {
    	final List<IExtJSIndex> result = new ArrayList<IExtJSIndex>();
    	for (final Map.Entry<IProject, ExtJSProject> entry : this.projects.entrySet()) {
    		// wrap content
    		result.add(new ExtJSProjectImpl(entry.getKey(), entry.getValue()));
    	}
        return result.toArray(new IExtJSIndex[result.size()]);
	}

    private Resource createResource() {
        final java.io.File file = ExtJSCore.getDefault().getStateLocation().append("index.extjsproject").toFile(); //$NON-NLS-1$
        try {
            file.createNewFile();
        } catch (IOException e) {
        }

        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("extjsproject", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        final ResourceSet resSet = new ResourceSetImpl();

        return resSet.createResource(URI.createFileURI(file.getAbsolutePath()));

    }

    private Resource getResource() {
        final java.io.File file = ExtJSCore.getDefault().getStateLocation().append("index.extjsproject").toFile(); //$NON-NLS-1$

        if (!file.exists()) {
            return null;
        }
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("extjsproject", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        final ResourceSet resSet = new ResourceSetImpl();

        return resSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);

    }

	@Override
	public void save() {
        final Resource resource = createResource();

        for (final Entry<IProject, ExtJSProject> entry : projects.entrySet()) {
            final IJavaScriptProject create = JavaScriptCore.create(entry.getKey());
            for (final File f : entry.getValue().getFiles()) {
                if (create.getPackageFragmentRoot(f.getName()) == null || !create.getPackageFragmentRoot(f.getName()).exists()) {
                    entry.getValue().getFiles().remove(f);
                }
            }

            resource.getContents().add(entry.getValue());
        }

        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (final IOException e) {
            ExtJSCore.error(e);
        }
	}

	@Override
	public IExtJSFile getFile(String filePath, boolean forceCreation) {
        File file = null;
        if (files.containsKey(filePath)) {
            file = files.get(filePath);
        } else {
        	if (!forceCreation) return null;
        	
            file = ExtJSFactory.eINSTANCE.createFile();
            file.setName(filePath);
            files.put(filePath, file);
        }

        // find and connect projects
        for (final Entry<IProject, ExtJSProject> entry : projects.entrySet()) {
            try {
                if (!entry.getValue().getFiles().contains(file) && inProject(entry.getKey(), filePath)) {
                    entry.getValue().getFiles().add(file);
                }
            } catch (Throwable e) {
                ExtJSCore.error(e);
            }

        }

        return new ExtJSFile(file);
	}
    
    private boolean inProject(IProject project, String filePath) {
        final IJavaScriptProject pr = JavaScriptCore.create(project);
        final IPath path = new Path(filePath);
        try {
            final IPackageFragmentRoot packageFragment = pr.findPackageFragmentRoot(path);
            if (packageFragment != null) {
                return true;
            }
        } catch (final Throwable e) {
            ExtJSCore.error(e);
        }
        if (!path.segment(0).equals(project.getName())) {
            return false;
        }
        final IResource findMember = project.findMember(path.removeFirstSegments(1));

        if (findMember != null) {
            return true;
        }

        return false;
    }

}
