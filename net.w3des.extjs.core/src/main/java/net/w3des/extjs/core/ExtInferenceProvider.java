package net.w3des.extjs.core;

import java.util.Set;
import net.w3des.extjs.core.infer.InferEngine;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.jsdt.core.infer.IInferEngine;
import org.eclipse.wst.jsdt.core.infer.IInferenceFile;
import org.eclipse.wst.jsdt.core.infer.InferrenceProvider;
import org.eclipse.wst.jsdt.core.infer.RefactoringSupport;
import org.eclipse.wst.jsdt.core.infer.ResolutionConfiguration;

public class ExtInferenceProvider implements InferrenceProvider {
	final public static String ID = "net.w3des.extjs.core.inference";

	public IInferEngine getInferEngine() {
		return new InferEngine();
	}
	
	public int applysTo(final IInferenceFile scriptFile) {
		final Path path = new Path(String.valueOf(scriptFile.getFileName()));
		final IFile fileProject = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		if (fileProject == null) {
			return NOT_THIS;
		}
		
		try {
			final Set<IFacetedProject> projects = ProjectFacetsManager.getFacetedProjects(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_ID));
			boolean found = false;
			for (IFacetedProject project : projects) {
				if (project.getProject().equals(fileProject.getProject())) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				return NOT_THIS;
			}
		} catch (CoreException e) {
			ExtJSCore.error("Error during facet checking", e);
			
			return NOT_THIS;
		}
		
		return MAYBE_THIS;
	}

	public String getID() {
		return ID;
	}

	public ResolutionConfiguration getResolutionConfiguration() {
		return new ResolutionConfiguration();
	}

	public RefactoringSupport getRefactoringSupport() {
		return new RefactoringSupport();
	}

}
