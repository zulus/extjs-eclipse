package net.w3des.extjs.ui.command;

import net.w3des.extjs.core.ExtJSCore;
import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class RemoveExtJSSupport extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection rawSelection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();

		if (!(rawSelection instanceof IStructuredSelection)) {
			return null;
		}

		IAdaptable adaptable = (IAdaptable) ((IStructuredSelection) rawSelection).getFirstElement();
		final IProject project = ((IResource) adaptable.getAdapter(IResource.class)).getProject();

		Job job = new Job("Remove ExtJS support") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				try {
					final IFacetedProject fproject = ProjectFacetsManager.create(project, false, monitor);

					fproject.uninstallProjectFacet(
							fproject.getInstalledVersion(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT)),
							null, monitor);
				} catch (CoreException e) {
					ExtJSUI.error(e);

					return Status.CANCEL_STATUS;
				}

				return Status.OK_STATUS;
			}
		};
		
		job.schedule();

		return null;
	}

}
