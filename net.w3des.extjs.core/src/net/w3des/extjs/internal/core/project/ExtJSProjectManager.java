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
package net.w3des.extjs.internal.core.project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.IExtJSProjectManager;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.project.ecore.ECoreStorageImpl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

final public class ExtJSProjectManager implements IExtJSProjectManager, IResourceChangeListener {

	/** the underlying index storage */
    private IIndexStorage storage;
    
    private Map<IProject, ProjectImpl> projects = new HashMap<IProject, ProjectImpl>();
    
	public ExtJSProjectManager() {
    	this.storage = new ECoreStorageImpl();
    }
	
	private IExtJSProject convert(IExtJSIndex index) {
		if (this.projects.containsKey(index.getProject())) {
			final ProjectImpl result = this.projects.get(index.getProject());
			result.updateIndex(index);
			return result;
		}
		final ProjectImpl result = new ProjectImpl(index);
		this.projects.put(index.getProject(), result);
		return result;
	}

    @Override
    public IExtJSProject createProject(final IProject project) {
        return createProject(project, false);
    }

    @Override
    public IExtJSProject createProject(final IProject project, final boolean force) {
    	if (this.storage.hasProject(project)) {
            return convert(this.storage.getProject(project, false));
        }

        if (isExtJSProject(project)) {
        	return convert(this.storage.getProject(project, true));
        }

        if (force) {
        	final IExtJSProject extProject = convert(this.storage.getProject(project, true));
            final IProgressMonitor monitor = new NullProgressMonitor();

            try {
                final IProjectDescription description = project.getDescription();
                final List<String> nature = new LinkedList<String>();
                nature.add(ExtJSNature.NATURE_ID);
                nature.addAll(Arrays.asList(description.getNatureIds()));
                description.setNatureIds(nature.toArray(new String[nature.size()]));
                project.setDescription(description, monitor);
            } catch (final CoreException e) {
                ExtJSCore.error(e);
            }

            return extProject;
        }

        return null;
    }

    @Override
    public boolean isExtJSProject(final IProject project) {
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
    public void deleteProject(final IProject project) {
        try {
            if (project.hasNature(ExtJSNature.NATURE_ID)) {
                final IProjectDescription description = project.getDescription();
                final List<String> nature = new LinkedList<String>();
                nature.addAll(Arrays.asList(description.getNatureIds()));
                nature.remove(ExtJSNature.NATURE_ID);

                description.setNatureIds(nature.toArray(new String[nature.size()]));
                project.setDescription(description, new NullProgressMonitor());
            }
        } catch (final CoreException e) {
            ExtJSCore.error(e);
        }

        this.storage.removeProject(project);
        this.projects.remove(project);
    }

    @Override
    public void resourceChanged(final IResourceChangeEvent event) {
        if (event.getResource() instanceof IProject) {
            projectChanged((IProject) event.getResource(), event);
        } else if (event.getType() == IResourceChangeEvent.POST_CHANGE && event.getDelta() != null) {
            try {
                event.getDelta().accept(new IResourceDeltaVisitor() {
                    @Override
                    public boolean visit(IResourceDelta delta) throws CoreException {
                        if (delta.getResource() instanceof IFile) {
                        	switch (delta.getKind()) {
                        	case IResourceDelta.REMOVED:
                        		ExtJSProjectManager.this.storage.notifyFileRemoval((IFile) delta.getResource());
                        		// fall through to flush properties on deleted props file
                        	case IResourceDelta.ADDED:
                        	case IResourceDelta.CHANGED:
                        		if (ProjectImpl.PROPS_PATH.equals(delta.getResource().getProjectRelativePath())) {
                        			final ProjectImpl prj = ExtJSProjectManager.this.projects.get(delta.getResource().getProject());
                        			if (prj != null) {
                        				prj.readProperties();
                        			}
                        		}
                			}
                        }

                        return true;
                    }
                });
            } catch (final CoreException e) {
                ExtJSCore.error(e);
            }
        }

    }

    private void projectChanged(IProject project, IResourceChangeEvent event) {
        if (!isExtJSProject(project)) {
            return;
        }
        switch (event.getType()) {
        case IResourceChangeEvent.PRE_DELETE:
        case IResourceChangeEvent.PRE_CLOSE:
        	this.storage.removeProject(project);
        	this.projects.remove(project);
            break;
        case IResourceChangeEvent.POST_CHANGE:
        	this.storage.updateProjectName(project);
            break;
        }
    }

    @Override
    public IExtJSProject[] getProjects() {
    	final IExtJSIndex indices[] = this.storage.getProjects();
    	final IExtJSProject result[] = new IExtJSProject[indices.length];
    	for (int i = result.length - 1; i >= 0; i--) {
    		result[i] = convert(indices[i]);
    	}
    	return result;
    }

    public void activate() {
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    	this.storage.activate();

        // scan
        for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            createProject(project);
        }
    }

    @Override
    public void save() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        this.storage.save();
    }

    @Override
    public IExtJSFile getFile(String filePath) {
    	return this.storage.getFile(filePath, true);
    }

    @Override
    public boolean onBuildPath(IResource resource) {
        final IJavaScriptProject scriptProject = JavaScriptCore.create(resource.getProject());
        try {
            for (final IIncludePathEntry entry : scriptProject.getRawIncludepath()) {
                if (entry.getEntryKind() != IIncludePathEntry.CPE_SOURCE) {
                    continue; // ignore other containers
                }
                // TODO allow exclude
                IPath fullPath = resource.getFullPath();
                IPath noFirst = fullPath.removeFirstSegments(1);
                if (entry.getPath().isPrefixOf(fullPath)
                        || entry.getPath().equals(fullPath) || entry.getPath().isPrefixOf(noFirst)
                        || entry.getPath().equals(noFirst)) {
                    return true;
                }
            }
        } catch (final JavaScriptModelException e) {
            return false;
        }

        return false;
    }
}
