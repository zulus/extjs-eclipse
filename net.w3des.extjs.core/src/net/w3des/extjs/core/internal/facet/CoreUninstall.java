package net.w3des.extjs.core.internal.facet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.ExtJSNature;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;

public class CoreUninstall implements IDelegate {

	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object config, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			IJavaScriptProject jsProject = JavaScriptCore.create(project);
			
			for (IIncludePathEntry e : jsProject.getResolvedIncludepath(true)) {
				if (e.getEntryKind() != IIncludePathEntry.CPE_SOURCE) {
					continue;
				}
				
				if (project.getFullPath().equals(e.getPath())) {
					touch(project.members(false), monitor);
					continue;
				} 
				
				IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(e.getPath());
				touch(folder.members(false), monitor);
			}
		} finally {
			
			if (project.hasNature(ExtJSNature.NATURE_ID)) {
				IProjectDescription description = project.getDescription();
				List<String> nature = new LinkedList<String>();
				nature.addAll(Arrays.asList(description.getNatureIds()));
				nature.remove(ExtJSNature.NATURE_ID);	
				
				description.setNatureIds(nature.toArray(new String[nature.size()]));
				project.setDescription(description, monitor);
			}
			
			monitor.done();
		}
	}
	
	public void touch(IResource[] res, IProgressMonitor monitor) throws CoreException {
		for (IResource r : res) {
			if (r instanceof IFolder) {
				touch(((IFolder) r).members(), monitor);
			} else if (r.getName().endsWith(".js")) {
				r.touch(monitor);
			}
		}
	}

}
