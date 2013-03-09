package net.w3des.extjs.core.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.internal.core.JavaModelManager;

@SuppressWarnings("restriction")
public class Uninstall implements IDelegate {
	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object config, IProgressMonitor monitor) throws CoreException {
		JavaModelManager.getJavaModelManager().getIndexManager().reset();
		project.build(IncrementalProjectBuilder.FULL_BUILD, JavaScriptCore.BUILDER_ID, null, monitor);
	}

}
