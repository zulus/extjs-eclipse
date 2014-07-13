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

import net.w3des.extjs.core.Utils;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.validation.problem.ProblemAttributes;
import net.w3des.extjs.internal.core.validation.problem.ProblemIdentifier;
import net.w3des.extjs.internal.core.validation.problem.ProblemSeverity;
import net.w3des.extjs.internal.core.validation.problem.ValidationProblem;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptModelMarker;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
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

		final IExtJSProject prj = ExtJSCore.getProjectManager().createProject(getProject());
		final IJavaScriptProject jsPrj = JavaScriptCore.create(getProject());
		if (prj != null) {
			delta.accept(new DeltaVisitor(prj.getSourceFolder().getFullPath(), prj, jsPrj));
		}
	}

	private void fullBuild(final IProgressMonitor monitor) throws CoreException {
    	this.checkProjectLevel(monitor);

		final IExtJSProject prj = ExtJSCore.getProjectManager().createProject(getProject());
		final IJavaScriptProject jsPrj = JavaScriptCore.create(getProject());
		if (prj != null) {
			getProject().accept(new ResourceVisitor(prj.getSourceFolder().getFullPath(), prj, jsPrj));
		}
    }
	
//	private void addProblems(IResource resource, ValidationProblem[] problems) throws CoreException {
//		for (final ValidationProblem problem : problems) {
//			addProblem(resource, problem);
//		}
//	}
	
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
			marker.setAttributes(problem.getExtraMarkerAttributeNames(), problem.getExtraMarkerAttributeValues());
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
			final IProjectFacetVersion extjsVer = prj.getVersion();
			final IProjectFacetVersion touchVer = prj.getTouchVersion();
			
			// *** checking extjs environment
			checkProjectLevelEnv(prj, extjsVer, touchVer);
			
			// *** checking libraries
			checkProjectLevelLib(prj, extjsVer, touchVer);
			
			// *** checking dependent projects
			checkProjectLevelReferencedProjects(extjsVer, touchVer);
			
			// *** checking extjs source folder
			checkProjectLevelExtjsSource(prj);
		}
		monitor.done();
	}

	private void checkProjectLevelExtjsSource(final IExtJSProject prj)
			throws CoreException, JavaScriptModelException {
		final IContainer source = prj.getSourceFolder();
		final IJavaScriptProject jsProject = JavaScriptCore.create(getProject());
		boolean found = false;
		for (final IIncludePathEntry entry : jsProject.getRawIncludepath()) {
			if (entry.getEntryKind() == IIncludePathEntry.CPE_SOURCE) {
				if (entry.getPath().isPrefixOf(source.getFullPath())) {
					found = true;
				}
			}
		}
		if (!found) {
			addProblem(getProject(), new ValidationProblem(
					ProblemIdentifier.EXTJS_SOURCE_NOT_A_SOURCE,
					ValidationProblem.CAT_BUILDPATH,
					ProblemSeverity.ERROR,
					NLS.bind("Extjs source folder {0} is not a valid javascript source", new Object[]{source.getFullPath().toString()}),
					null,
					null,
					null,
					null, -1, -1, -1));
		}
	}

	private void checkProjectLevelReferencedProjects(
			final IProjectFacetVersion extjsVer,
			final IProjectFacetVersion touchVer) throws CoreException {
		final IProject[] refs = getProject().getReferencedProjects();
		for (final IProject ref : refs) {
			if (ExtJSCore.getProjectManager().isExtJSProject(ref)) {
				final IExtJSProject extjsRef = ExtJSCore.getProjectManager().createProject(ref);
				if (extjsRef != null) {
					final IProjectFacetVersion refExtjsVer = extjsRef.getVersion();
					final IProjectFacetVersion refTouchVer = extjsRef.getTouchVersion();
					if ((extjsVer != null && !extjsVer.equals(refExtjsVer)) || (touchVer != null && !touchVer.equals(refTouchVer))) {
						addProblem(getProject(), new ValidationProblem(
							ProblemIdentifier.PRJ_INCOMPATIBLE_VERSION,
							ValidationProblem.CAT_BUILDPATH,
							ProblemSeverity.ERROR,
							NLS.bind("incompatible referenced project {0}", new Object[]{ref.getName()}),
							null,
							new String[]{ProblemAttributes.KEY_PROJECTNAME},
							new Object[]{ref.getName()},
							null, -1, -1, -1));
					}
				}
			}
		}
	}

	private void checkProjectLevelLib(final IExtJSProject prj,
			final IProjectFacetVersion extjsVer,
			final IProjectFacetVersion touchVer) throws CoreException {
		for (final String libName : prj.getLibraryNames()) {
			final IExtJSLibrary lib = ExtJSCore.getLibraryManager().getLibrary(libName);
			if (lib == null) {
				addProblem(getProject(), new ValidationProblem(
						ProblemIdentifier.LIB_MISSING,
						ValidationProblem.CAT_BUILDPATH,
						ProblemSeverity.ERROR,
						NLS.bind("missing library {0}", new Object[]{libName}),
						null,
						new String[]{ProblemAttributes.KEY_LIBRARYNAME},
						new Object[]{libName},
						null, -1, -1, -1));
			}
			else if (!lib.isCompatible(extjsVer) && !lib.isCompatible(touchVer)) {
				addProblem(getProject(), new ValidationProblem(
						ProblemIdentifier.LIB_INCOMPATIBLE_VERSION,
						ValidationProblem.CAT_BUILDPATH,
						ProblemSeverity.ERROR,
						NLS.bind("incompatible library {0}", new Object[]{libName}),
						null,
						new String[]{ProblemAttributes.KEY_LIBRARYNAME},
						new Object[]{libName},
						null, -1, -1, -1));
			}
		}
	}

	private void checkProjectLevelEnv(final IExtJSProject prj,
			final IProjectFacetVersion extjsVer,
			final IProjectFacetVersion touchVer) throws CoreException {
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
					IProjectFacetVersion ver = extjsVer;
					if (ver == null) {
						ver = touchVer;
					}
					coreLib = ExtJSCore.getLibraryManager().getDefaultCoreLib(ver);
				}
				if (coreLib == null) {
					// core library was not found
					addProblem(getProject(), new ValidationProblem(
							ProblemIdentifier.ENV_MISSING_CORE,
							ValidationProblem.CAT_BUILDPATH,
							ProblemSeverity.ERROR,
							"missing extjs core lib (sdk)",
							null,
							new String[]{ProblemAttributes.KEY_CORE_VERSION},
							new Object[]{extjsVer.getVersionString()},
							null, -1, -1, -1));
				}
				else if (!coreLib.isCompatible(extjsVer) && !coreLib.isCompatible(touchVer)) {
					// core library is not compatible to chosen extjs version of the project
					addProblem(getProject(), new ValidationProblem(
							ProblemIdentifier.ENV_INCOMPATIBLE_VERSION,
							ValidationProblem.CAT_BUILDPATH,
							ProblemSeverity.ERROR,
							"incompatible extjs core lib (sdk)",
							null,
							null, -1, -1, -1));
				}
				// check env libraries
				for (final String libName : env.getLibraryNames()) {
					final IExtJSLibrary lib = ExtJSCore.getLibraryManager().getLibrary(libName);
					if (lib == null) {
						addProblem(getProject(), new ValidationProblem(
								ProblemIdentifier.LIB_MISSING,
								ValidationProblem.CAT_BUILDPATH,
								ProblemSeverity.ERROR,
								NLS.bind("missing library {0}", new Object[]{libName}),
								null,
								new String[]{ProblemAttributes.KEY_LIBRARYNAME},
								new Object[]{libName},
								null, -1, -1, -1));
					}
					else if (!lib.isCompatible(extjsVer) && !lib.isCompatible(touchVer)) {
						addProblem(getProject(), new ValidationProblem(
								ProblemIdentifier.LIB_INCOMPATIBLE_VERSION,
								ValidationProblem.CAT_BUILDPATH,
								ProblemSeverity.ERROR,
								NLS.bind("incompatible library {0}", new Object[]{libName}),
								null,
								new String[]{ProblemAttributes.KEY_LIBRARYNAME},
								new Object[]{libName},
								null, -1, -1, -1));
					}
				}
			}
		}
	}
	
	private final class DeltaVisitor implements IResourceDeltaVisitor {
		private IPath extjsFolder;
		private IJavaScriptProject prj;
		private IExtJSProject extjsPrj;

		public DeltaVisitor(IPath extjsFolder, IExtJSProject extjsPrj, IJavaScriptProject prj) {
			this.extjsFolder = extjsFolder;
			this.prj = prj;
			this.extjsPrj = extjsPrj;
		}

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			if (!this.extjsFolder.isPrefixOf(delta.getFullPath()) && !delta.getFullPath().isPrefixOf(this.extjsFolder)) {
				return false;
			}
			
			
			if (delta.getResource().getType() == IResource.FILE && (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.CHANGED)) {
				final IFile file = (IFile) delta.getResource();
				checkFile(extjsFolder, extjsPrj, prj, file);
			}
			return true;
		}
	}
	
	private final class ResourceVisitor implements IResourceVisitor {
		private IPath extjsFolder;
		private IJavaScriptProject prj;
		private IExtJSProject extjsPrj;

		public ResourceVisitor(IPath extjsFolder, IExtJSProject extjsPrj, IJavaScriptProject prj) {
			this.extjsFolder = extjsFolder;
			this.prj = prj;
			this.extjsPrj = extjsPrj;
		}

		@Override
		public boolean visit(IResource res) throws CoreException {
			if (!this.extjsFolder.isPrefixOf(res.getFullPath()) && !res.getFullPath().isPrefixOf(extjsFolder)) {
				return false;
			}
			
			
			if (res.getType() == IResource.FILE) {
				final IFile file = (IFile) res;
				checkFile(extjsFolder, extjsPrj, prj, file);
			}
			return true;
		}
		
	}

	protected void checkFile(IPath extjsFolder, IExtJSProject extjsPrj, IJavaScriptProject jsPrj, IFile file) throws CoreException {
		file.deleteMarkers(ValidationProblem.MARKER_TYPE, true, IResource.DEPTH_ZERO);
		if (file.getName().endsWith(".js")) {
			// javascript source file
			final IPath pkg = file.getParent().getFullPath().removeFirstSegments(extjsFolder.segmentCount());
			final StringBuilder builder = new StringBuilder();
			if (extjsPrj.getExtjsNamespace() != null) builder.append(extjsPrj.getExtjsNamespace());
			for (String fragment : pkg.segments()) {
				if (builder.length() > 0) builder.append('.');
				builder.append(fragment);
			}
			if (builder.length() > 0) builder.append('.');
			final String name = file.getName().substring(0, file.getName().length() - 3);
			if (name.indexOf('.') != -1) {
				addProblem(file, new ValidationProblem(
						ProblemIdentifier.EXTRA_DOTS_IN_FILENAME,
						ValidationProblem.CAT_RESTRICTION,
						ProblemSeverity.WARNING,
						"File name contains extra dots",
						null,
						null,
						null,
						null, -1, -1, -1));
			}
			else {
				builder.append(name);
				final IType[] types = Utils.findTypeByName(builder.toString(), jsPrj);
				boolean found = false;
				if (types != null) {
					for (final IType type : types) {
						if (type.getPath().equals(file.getFullPath())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					addProblem(file, new ValidationProblem(
							ProblemIdentifier.MISSING_EXTJS_CLASS,
							ValidationProblem.CAT_RESTRICTION,
							ProblemSeverity.WARNING,
							NLS.bind("Source file does not declare extjs class with name {0}", new Object[]{builder.toString()}),
							null,
							null,
							null,
							null, -1, -1, -1));
				}
			}
		}
		else {
			// some other binary file
			addProblem(file, new ValidationProblem(
					ProblemIdentifier.EXTRA_BINARY_FILE,
					ValidationProblem.CAT_RESTRICTION,
					ProblemSeverity.WARNING,
					"You should not put extra binary files on extjs source folders",
					null,
					null,
					null,
					null, -1, -1, -1));
		}
	}

}
