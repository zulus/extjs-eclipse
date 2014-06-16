package net.w3des.extjs.internal.core.jscpc;

import java.util.ArrayList;
import java.util.List;

import net.w3des.extjs.core.IExtJSLibraryManager;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.UnimplementedException;
import org.eclipse.wst.jsdt.core.compiler.libraries.LibraryLocation;

public class LibContainerInitializer extends JsGlobalScopeContainerInitializer {
    
    public void initialize(IPath containerPath, IJavaScriptProject project) throws CoreException {
        if (isLibLibraryContainer(containerPath)) {
            final String envName = containerPath.segment(1);
            
            final LibContainer container = new LibContainer(envName);
            JavaScriptCore.setJsGlobalScopeContainer(containerPath, new IJavaScriptProject[] { project }, new IJsGlobalScopeContainer[] { container }, null);
        }
    }

    public boolean canUpdateJsGlobalScopeContainer(IPath containerPath, IJavaScriptProject project) {
        return isLibLibraryContainer(containerPath);
    }

    /**
     * @see org.eclipse.wst.jsdt.core.JsGlobalScopeContainerInitializer#getDescription(org.eclipse.core.runtime.IPath, org.eclipse.wst.jsdt.core.IJavaScriptProject)
     */
    public String getDescription(IPath containerPath, IJavaScriptProject project) {
        if (isLibLibraryContainer(containerPath)) {
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
    
    private boolean isLibLibraryContainer(IPath path) {
        return path != null && path.segmentCount() == 2 && ExtJSCore.JSCPC_LIB_ID.equals(path.segment(0));
    }
    
    /**
     *
     */
    public static class LibContainer implements IJsGlobalScopeContainer {
        
        private String name;
        
        public LibContainer(String envName) {
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
            final IExtJSLibrary lib = manager.getLibrary(this.name);
            if (lib != null) {
            	try {
	            	addLib(result, lib);
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
            return new Path(ExtJSCore.JSCPC_LIB_ID).append(this.name);
        }
        
        public String[] resolvedLibraryImport(String a) {
            return new String[] { a };
        }
    }

}
