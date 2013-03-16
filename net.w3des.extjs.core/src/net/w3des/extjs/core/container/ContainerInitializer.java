package net.w3des.extjs.core.container;


import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
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
		return null; 
 	}
	
	/**
	 * TODO configurable by attributes
	 */
	@Override
	public String[] containerSuperTypes() {
		return new String[] { "Ext" }; //$NON-NLS-1$
	}
	
	@Override
	public boolean allowAttachJsDoc() {  //no because ExtJS use JSDuck format, maybe later
		return false;
	}

	@Override
	public void initialize(IPath containerPath, IJavaScriptProject project) throws CoreException {
		try {
			JavaScriptCore.setJsGlobalScopeContainer(containerPath, new IJavaScriptProject[] { project }, new IJsGlobalScopeContainer[] { getContainer(containerPath, project) }, null);
		} catch (Exception e) {
			ExtJSCore.error(e);
		}
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
		return containerPath.segment(0);
	}

	@Override
	public IJsGlobalScopeContainer getFailureContainer(IPath containerPath, IJavaScriptProject project) {
		return null;
	}


	@Override
	public void removeFromProject(IJavaScriptProject project) {
		
	}
	
	@Override
	public IStatus getAttributeStatus(IPath containerPath, IJavaScriptProject project, String attributeKey) {
		return super.getAttributeStatus(containerPath, project, attributeKey);
	}
	@Override
	public IIncludePathEntry[] getIncludepathEntries() {
		return new IIncludePathEntry[0];
	}
	
	@Override
	public Object getComparisonID(IPath containerPath,
			IJavaScriptProject project) {
		return Container.ID;
	}
}
