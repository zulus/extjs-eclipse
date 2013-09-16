package net.w3des.extjs.ui.handler;

import javax.inject.Named;

import net.w3des.extjs.core.internal.ExtJSCore;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class AddExtJSSupport {

    @Execute
    public static void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection, final String version) throws ExecutionException {

        IAdaptable adaptable = (IAdaptable) selection.getFirstElement();
        final IProject project = ((IResource) adaptable.getAdapter(IResource.class)).getProject();

        Job job = new Job("Install ExtJS Support") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    final IFacetedProject fproject = ProjectFacetsManager.create(project, true, monitor);
                    final IProjectFacetVersion facet;

                    facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT).getVersion(version);

                    if (!fproject.hasProjectFacet(ProjectFacetsManager.getProjectFacet("wst.jsdt.web"))) { //$NON-NLS-1$
                        fproject.installProjectFacet(ProjectFacetsManager.getProjectFacet("wst.jsdt.web").getDefaultVersion(), null, monitor); //$NON-NLS-1$
                    }

                    if (!fproject.hasProjectFacet(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT))) {
                        fproject.installProjectFacet(facet, null, monitor);
                    }

                } catch (CoreException e) {
                    return Status.CANCEL_STATUS;
                }

                return Status.OK_STATUS;
            }
        };
        job.schedule();

    }

}
