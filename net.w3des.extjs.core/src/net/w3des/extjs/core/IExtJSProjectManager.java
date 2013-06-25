package net.w3des.extjs.core;

import net.w3des.extjs.core.model.basic.ExtJSProject;
import net.w3des.extjs.core.model.basic.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public interface IExtJSProjectManager {
    public ExtJSProject createProject(IProject project);

    public ExtJSProject createProject(IProject project, boolean force);

    public void deleteProject(IProject project);

    public boolean isExtJSProject(IProject project);

    public boolean onBuildPath(IResource resource);

    public ExtJSProject[] getProjects();

    public File getFile(String file);

    public void save();
}
