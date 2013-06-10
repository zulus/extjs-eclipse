package net.w3des.extjs.core.internal.facet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.internal.WorkspaceHelper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.JavaScriptCore;

public class CoreInstall implements IDelegate {

	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object config, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			WorkspaceHelper.refreshJavaScriptProject(JavaScriptCore.create(project), monitor);
		} finally {
			
			if (!project.hasNature(ExtJSNature.NATURE_ID)) {
				IProjectDescription description = project.getDescription();
				List<String> nature = new LinkedList<String>();
				nature.add(ExtJSNature.NATURE_ID);
				nature.addAll(Arrays.asList(description.getNatureIds()));
				description.setNatureIds(nature.toArray(new String[nature.size()]));
				project.setDescription(description, monitor);
			}
			
			monitor.done();
		}
	}

}
