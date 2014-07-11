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
package net.w3des.extjs.internal.core.jscpc;

import java.util.ArrayList;
import java.util.List;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.IExtJSLibraryManager;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.UnimplementedException;
import org.eclipse.wst.jsdt.core.compiler.libraries.LibraryLocation;

public class EnvContainerInitializer extends JsGlobalScopeContainerInitializer {
    
    public void initialize(IPath containerPath, IJavaScriptProject project) throws CoreException {
        if (isEnvLibraryContainer(containerPath)) {
            final String envName = containerPath.segment(1);
            
            final EnvContainer container = new EnvContainer(envName);
            JavaScriptCore.setJsGlobalScopeContainer(containerPath, new IJavaScriptProject[] { project }, new IJsGlobalScopeContainer[] { container }, null);
        }
    }

    public boolean canUpdateJsGlobalScopeContainer(IPath containerPath, IJavaScriptProject project) {
        return isEnvLibraryContainer(containerPath);
    }

    /**
     * @see org.eclipse.wst.jsdt.core.JsGlobalScopeContainerInitializer#getDescription(org.eclipse.core.runtime.IPath, org.eclipse.wst.jsdt.core.IJavaScriptProject)
     */
    public String getDescription(IPath containerPath, IJavaScriptProject project) {
        if (isEnvLibraryContainer(containerPath)) {
            return containerPath.segment(1);
        }
        return super.getDescription(containerPath, project);
    }

    public Object getComparisonID(IPath containerPath, IJavaScriptProject project) {
        return containerPath;
    }

    public LibraryLocation getLibraryLocation() {
        throw new UnimplementedException();
    }
    
    private boolean isEnvLibraryContainer(IPath path) {
        return path != null && path.segmentCount() == 2 && ExtJSCore.JSCPC_ENV_ID.equals(path.segment(0));
    }
    
    /**
     *
     */
    public static class EnvContainer implements IJsGlobalScopeContainer {
        
        private String name;
        
        public EnvContainer(String envName) {
            this.name = envName;
        }
        
        /**
         * @deprecated Use {@link #getIncludepathEntries()} instead
         */
        public IIncludePathEntry[] getClasspathEntries() {
            return getIncludepathEntries();
        }
        
        public IIncludePathEntry[] getIncludepathEntries() {
            final List<IIncludePathEntry> result = new ArrayList<IIncludePathEntry>();
            final IExtJSLibraryManager manager = ExtJSCore.getLibraryManager();
            final IExtJSEnvironment env = manager.getEnv(this.name);
            if (env != null) {
            	try {
	            	if (env.getCore() == null) {
	            		for (final IProjectFacetVersion version : env.getCompatibleVersions(ExtJSNature.getExtjsFacet())) {
	            			final IExtJSCoreLibrary coreLib = manager.getDefaultCoreLib(version);
	            			if (coreLib != null) {
	            				addLib(result, coreLib);
	            				break;
	            			}
	            		}
	            		for (final IProjectFacetVersion version : env.getCompatibleVersions(ExtJSNature.getSenchaTouchFacet())) {
	            			final IExtJSCoreLibrary coreLib = manager.getDefaultCoreLib(version);
	            			if (coreLib != null) {
	            				addLib(result, coreLib);
	            				break;
	            			}
	            		}
	            	}
	            	else {
	            		addLib(result, env.getCore());
	            	}
	            	for (final IExtJSLibrary lib : env.getLibraries()) {
	                    addLib(result, lib);
	                }
            	}
            	catch (CoreException ex) {
            		ExtJSCore.error(ex);
            	}
            }
            return result.toArray(new IIncludePathEntry[result.size()]);
            
        }

		private void addLib(final List<IIncludePathEntry> result,
				final IExtJSLibrary lib) throws CoreException {
			for (final ILibrarySource elm : lib.getSources()) {
			    switch (elm.getSourceType()) {
			        case JAVASCRIPT_FILE:
			            result.add(JavaScriptCore.newLibraryEntry(elm.getFullPath(), null, null));
			            break;
			        case FOLDER:
			            // TODO inclusions
			            result.add(JavaScriptCore.newLibraryEntry(elm.getFullPath(), null, null));
			            break;
			        case ZIP:
			            // TODO inclusions
			            result.add(JavaScriptCore.newLibraryEntry(elm.getFullPath(), null, null));
			            break;
			        default:
			            // ignore
			            break;
			    }
			}
		}

        public String getDescription() {
            return this.name;
        }
        
        public int getKind() {
            return K_APPLICATION;
        }
        
        public IPath getPath() {
            return new Path(ExtJSCore.JSCPC_ENV_ID).append(this.name);
        }
        
        public String[] resolvedLibraryImport(String a) {
            return new String[] { a };
        }
    }

}
