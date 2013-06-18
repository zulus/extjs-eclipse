package net.w3des.extjs.core.internal.facet;

import net.w3des.extjs.core.internal.ExtJSCore;
import net.w3des.extjs.core.internal.WorkspaceHelper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.JavaScriptCore;

public class CoreUninstall implements IDelegate {

    @Override
    public void execute(IProject project, IProjectFacetVersion version, Object config, IProgressMonitor monitor) throws CoreException {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }
        ExtJSCore.getProjectManager().deleteProject(project);

        try {
            WorkspaceHelper.refreshJavaScriptProject(JavaScriptCore.create(project), monitor);
        } finally {
            monitor.done();
        }
    }
}
