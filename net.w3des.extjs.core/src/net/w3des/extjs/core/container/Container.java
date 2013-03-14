package net.w3des.extjs.core.container;

import net.w3des.extjs.core.ExtJSCore;
import net.w3des.extjs.core.ExtJsCoreMessages;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.IAccessRule;
import org.eclipse.wst.jsdt.core.IIncludePathAttribute;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.internal.core.ClasspathEntry;

public class Container implements IJsGlobalScopeContainer {
	final public static String ID = "net.w3des.extjs.core.container"; //$NON-NLS-1$
	final private IJavaScriptProject project;
	final private IPath path;
	
	public Container(IJavaScriptProject project, IPath path) {
		this.project = project;
		this.path = path;
	}
	
	@Override
	public IIncludePathEntry[] getIncludepathEntries() {
		return new IIncludePathEntry[0];
	}

	@Override
	public String getDescription() {
		return ExtJsCoreMessages.libraryName;
	}

	@Override
	public int getKind() {
		return K_APPLICATION;
	}

	@Override
	public IPath getPath() {
		return path;
	}

	@Override
	public String[] resolvedLibraryImport(String a) {
		return new String[0];
	}

}
