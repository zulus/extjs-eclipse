package net.w3des.extjs.core;

import org.eclipse.core.resources.IProject;

public interface IExtJSProjectManager {
	public IExtJSProject createProject(IProject project, boolean force);
	
}
