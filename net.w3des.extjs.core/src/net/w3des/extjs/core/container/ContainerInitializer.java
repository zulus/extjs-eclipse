package net.w3des.extjs.core.container;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import net.w3des.extjs.core.ExtJSCore;
import net.w3des.extjs.core.ExtJsCoreMessages;
import net.w3des.extjs.core.infer.InferProvider;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.JsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.compiler.libraries.LibraryLocation;

/**
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 *
 */
public class ContainerInitializer extends JsGlobalScopeContainerInitializer implements IJsGlobalScopeContainerInitializer {
	@Override
	public LibraryLocation getLibraryLocation() {
		return null;
	}
	
	@Override
	public String getInferenceID() {
		return null; //allow all
 	}
	
	@Override
	public String[] containerSuperTypes() {
		return new String[] { "Ext" }; //$NON-NLS-1$
	}
	
	@Override
	public boolean allowAttachJsDoc() {
		return true;
	}

	@Override
	public void initialize(IPath containerPath, IJavaScriptProject project) throws CoreException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		JavaScriptCore.setIncludepathVariable("Ext", containerPath, monitor);
		JavaScriptCore.setJsGlobalScopeContainer(containerPath, new IJavaScriptProject[] { project }, new IJsGlobalScopeContainer[] { getContainer(containerPath, project) }, monitor);
	}

	protected IJsGlobalScopeContainer getContainer(IPath containerPath, IJavaScriptProject project) {
		Container cont = new Container(project, containerPath);
		return cont;
	}

	@Override
	public boolean canUpdateJsGlobalScopeContainer(IPath containerPath,IJavaScriptProject project) {
		return true;
	}

	@Override
	public void requestJsGlobalScopeContainerUpdate(IPath containerPath, IJavaScriptProject project, IJsGlobalScopeContainer containerSuggestion) throws CoreException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		JavaScriptCore.setIncludepathVariable("Ext", containerPath, monitor);
		JavaScriptCore.setJsGlobalScopeContainer(containerPath, new IJavaScriptProject[] { project }, new IJsGlobalScopeContainer[] { getContainer(containerPath, project) }, monitor);
	}

	@Override
	public String getDescription(IPath containerPath, IJavaScriptProject project) {
		return containerPath.toString();
	}

	@Override
	public IJsGlobalScopeContainer getFailureContainer(IPath containerPath, IJavaScriptProject project) {
		return null;
	}

	@Override
	public Object getComparisonID(IPath containerPath, IJavaScriptProject project) {
		return Container.ID;
	}
	
	@Override
	public URI getHostPath(IPath path, IJavaScriptProject project) {
		return project.getHostPath();
	}

	@Override
	public void removeFromProject(IJavaScriptProject project) {
	}
	
	
}
