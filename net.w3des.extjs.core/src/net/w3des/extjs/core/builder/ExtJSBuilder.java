/*******************************************************************************
 * Copyright (c) 2013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.core.builder;

import java.util.Map;

import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.validation.problem.ProblemAttributes;
import net.w3des.extjs.internal.core.validation.problem.ProblemIdentifier;
import net.w3des.extjs.internal.core.validation.problem.ProblemSeverity;
import net.w3des.extjs.internal.core.validation.problem.ValidationProblem;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.jsdt.core.IJavaScriptModelMarker;
import org.eclipse.wst.jsdt.internal.core.util.Util;

/**
 * Extra data collector
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class ExtJSBuilder extends IncrementalProjectBuilder {

    public static final String ID = "net.w3des.extjs.core.builder.extJSBuilder";

    public ExtJSBuilder() {
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
    	try {
	    	if (kind == IncrementalProjectBuilder.FULL_BUILD) {
	    		fullBuild(monitor == null ? new NullProgressMonitor()  : monitor);
	    	} else {
	    		final IResourceDelta delta = getDelta(getProject());
	    		if (delta == null) {
	    			fullBuild(monitor == null ? new NullProgressMonitor()  : monitor);
	    		}
	    		else {
	    			incrementalBuild(delta, monitor == null ? new NullProgressMonitor()  : monitor);
	    		}
	    	}
    	}
    	catch (CoreException ex) {
    		ExtJSCore.error(ex);
    	}
        return null;
    }
    
    private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
    	this.checkProjectLevel(monitor);
	}

	private void fullBuild(final IProgressMonitor monitor) throws CoreException {
    	this.checkProjectLevel(monitor);
    }
	
	private void addProblems(IResource resource, ValidationProblem[] problems) throws CoreException {
		for (final ValidationProblem problem : problems) {
			addProblem(resource, problem);
		}
	}
	
	private void addProblem(IResource resource, ValidationProblem problem) throws CoreException {
		Integer severity = Integer.valueOf(IMarker.SEVERITY_ERROR);
		switch (problem.getSeverity()) {
		case ERROR:
			// severity is already set to error
			break;
		case IGNORE:
		case INFO:
			severity = Integer.valueOf(IMarker.SEVERITY_INFO);
			break;
		case WARNING:
			severity = Integer.valueOf(IMarker.SEVERITY_WARNING);
			break;
		}
		final IMarker marker = resource.createMarker(problem.getMarkerType());
		marker.setAttributes(
			new String[] {
				IMarker.MESSAGE,
				IMarker.SEVERITY,
				IMarker.LOCATION,
				IJavaScriptModelMarker.ID,
				IJavaScriptModelMarker.ARGUMENTS ,
				IJavaScriptModelMarker.CATEGORY_ID,
				IMarker.SOURCE_ID,
				IMarker.LINE_NUMBER,
				IMarker.CHAR_START,
				IMarker.CHAR_END
			},
			new Object[] {
				problem.getMessage(),
				severity,
				problem.getOriginatingFileName() == null ? null : new String(problem.getOriginatingFileName()),
				new Integer(problem.getID()),
				Util.getProblemArgumentsForMarker(problem.getArguments()) ,
				new Integer(problem.getCategoryID()),
				"EXTJS",
				new Integer(problem.getSourceLineNumber()),
				new Integer(problem.getSourceStart()),
				new Integer(problem.getSourceEnd())
			}
		);
		if (problem.getExtraMarkerAttributeNames() != null && problem.getExtraMarkerAttributeNames().length > 0) {
			marker.setAttributes(problem.getArguments(), problem.getExtraMarkerAttributeValues());
		}
	}
	
	/**
	 * Checks project level problems
	 * @param monitor
	 */
	private void checkProjectLevel(final IProgressMonitor monitor) throws CoreException {
		getProject().deleteMarkers(ValidationProblem.MARKER_TYPE, true, IResource.DEPTH_ZERO);
		monitor.beginTask("Check extjs version and dependencies", 1);
		final IExtJSProject prj = ExtJSCore.getProjectManager().createProject(getProject());
		if (prj != null) {
			// *** checking extjs environment
			if (prj.getEnvironmentName() == null) {
				// env is not set (should not happen because facet installation causes to set it to the default)
				addProblem(getProject(), new ValidationProblem(
						ProblemIdentifier.ENV_MISSING,
						ValidationProblem.CAT_BUILDPATH,
						ProblemSeverity.ERROR,
						"missing extjs execution environment",
						null,
						null, -1, -1, -1));
			}
			else {
				final IExtJSEnvironment env = ExtJSCore.getLibraryManager().getEnv(prj.getEnvironmentName());
				if (env == null) {
					// environment is set but it not known by env manager
					addProblem(getProject(), new ValidationProblem(
							ProblemIdentifier.ENV_MISSING,
							ValidationProblem.CAT_BUILDPATH,
							ProblemSeverity.ERROR,
							"missing extjs execution environment",
							null,
							null, -1, -1, -1));
				}
				else {
					IExtJSCoreLibrary coreLib = env.getCore();
					if (coreLib == null) {
						// fallback to default
						coreLib = ExtJSCore.getLibraryManager().getDefaultCoreLib(prj.getVersion());
					}
					if (coreLib == null) {
						// core library was not found
						addProblem(getProject(), new ValidationProblem(
								ProblemIdentifier.ENV_MISSING_CORE,
								ValidationProblem.CAT_BUILDPATH,
								ProblemSeverity.ERROR,
								"missing extjs core lib (sdk)",
								null,
								null, -1, -1, -1));
					}
					else if (!coreLib.isCompatible(prj.getVersion())) {
						// core library is not compatible to chosen extjs version of the project
						addProblem(getProject(), new ValidationProblem(
								ProblemIdentifier.ENV_INCOMPATIBLE_VERSION,
								ValidationProblem.CAT_BUILDPATH,
								ProblemSeverity.ERROR,
								"incompatible extjs core lib (sdk)",
								null,
								null, -1, -1, -1));
					}
				}
			}
			
			// *** checking libraries
			for (final String libName : prj.getLibraryNames()) {
				final IExtJSLibrary lib = ExtJSCore.getLibraryManager().getLibrary(libName);
				if (lib == null) {
					addProblem(getProject(), new ValidationProblem(
							ProblemIdentifier.LIB_MISSING,
							ValidationProblem.CAT_BUILDPATH,
							ProblemSeverity.ERROR,
							NLS.bind("missing library '{0}'", new Object[]{libName}),
							null,
							new String[]{ProblemAttributes.KEY_LIBRARYNAME},
							new Object[]{libName},
							null, -1, -1, -1));
				}
				else if (!lib.isCompatible(prj.getVersion())) {
					addProblem(getProject(), new ValidationProblem(
							ProblemIdentifier.LIB_INCOMPATIBLE_VERSION,
							ValidationProblem.CAT_BUILDPATH,
							ProblemSeverity.ERROR,
							NLS.bind("incompatible library '{0}'", new Object[]{libName}),
							null,
							new String[]{ProblemAttributes.KEY_LIBRARYNAME},
							new Object[]{libName},
							null, -1, -1, -1));
				}
			}
			
			// *** checking dependent projects
			final IProject[] refs = getProject().getReferencedProjects();
			for (final IProject ref : refs) {
				if (ExtJSCore.getProjectManager().isExtJSProject(ref)) {
					final IExtJSProject extjsRef = ExtJSCore.getProjectManager().createProject(ref);
					if (extjsRef != null && !prj.getVersion().equals(extjsRef.getVersion())) {
						addProblem(getProject(), new ValidationProblem(
								ProblemIdentifier.PRJ_INCOMPATIBLE_VERSION,
								ValidationProblem.CAT_BUILDPATH,
								ProblemSeverity.ERROR,
								NLS.bind("incompatible referenced project '{0}'", new Object[]{ref.getName()}),
								null,
								new String[]{ProblemAttributes.KEY_PROJECTNAME},
								new Object[]{ref.getName()},
								null, -1, -1, -1));
					}
				}
			}
		}
		monitor.done();
	}

}
