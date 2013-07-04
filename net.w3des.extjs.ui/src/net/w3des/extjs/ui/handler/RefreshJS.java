package net.w3des.extjs.ui.handler;

import javax.inject.Named;

import net.w3des.extjs.core.internal.ExtJSCore;
import net.w3des.extjs.core.internal.WorkspaceHelper;
import net.w3des.extjs.core.model.basic.ExtJSProject;
import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

public class RefreshJS {
	@Execute
	public static void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection, IProgressMonitor monitor) {
		final IAdaptable adaptable = (IAdaptable) selection.getFirstElement();
		final IProject project = ((IResource) adaptable.getAdapter(IResource.class)).getProject();
		try {
			ExtJSProject pr = ExtJSCore.getProjectManager().createProject(project);
			if (pr != null && pr.getFiles().size() > 0) {
				pr.getFiles().clear();
			}
			WorkspaceHelper.refreshJavaScriptProject(JavaScriptCore.create(project), monitor);
		} catch (JavaScriptModelException e) {
			ExtJSUI.error(e);
		} catch (CoreException e) {
			ExtJSUI.error(e);
		}
		
	}
}
