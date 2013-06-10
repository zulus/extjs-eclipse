package net.w3des.extjs.core.internal.project;

import java.util.HashMap;
import java.util.Map;

import net.w3des.extjs.core.IExtJSProjectManager;
import net.w3des.extjs.core.model.extjs.ExtjsPackage;

import org.eclipse.core.resources.IProject;

final public class ExtJSProjectManager implements IExtJSProjectManager {
	private Map<IProject, ExtJSProject> projects;
	
	public ExtJSProjectManager() {
		ExtjsPackage.eINSTANCE.eClass();
	}
	
	public ExtJSProject createProject(IProject project, boolean force) {
		return null;
	}

	public void deactivate() {
		projects.clear();
	}
	
	public void activate() {
		projects = new HashMap<IProject, ExtJSProject>();
	}
}
