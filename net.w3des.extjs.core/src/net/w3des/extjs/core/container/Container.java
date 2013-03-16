package net.w3des.extjs.core.container;

import java.util.HashSet;
import java.util.Set;

import net.w3des.extjs.core.ExtJsCoreMessages;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;

public class Container implements IJsGlobalScopeContainer {
	final public static String ID = "net.w3des.extjs.core.container"; //$NON-NLS-1$
	final private static String SRC = "src"; //$NON-NLS-1$
	final private static String SRC_SEPARATOR = IPath.SEPARATOR + SRC + IPath.SEPARATOR;
	final private IPath path;
	
	public Container(IJavaScriptProject project, IPath path) {
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
		return new Path(ID);
	}

	@Override
	public String[] resolvedLibraryImport(String a) {
		return new String[] {"Ext"}; //$NON-NLS-1$
	}
	
	public static IPath createPath(IFolder folder) {
		return new Path(Container.ID + SRC_SEPARATOR + folder.getProjectRelativePath());
	}

	public static IPath[] getRelativePathes(IIncludePathEntry[] entries) {
		if (entries == null || entries.length == 0) {
			return new IPath[0];
		}
		
		Set<IPath> list = new HashSet<IPath>();
		
		for (IIncludePathEntry entry : entries) {
			if (entry.getPath().segmentCount() > 2 && entry.getPath().segment(0).equals(ID) && entry.getPath().segment(1).equals(SRC)) {
				list.add(getRelativePath(entry));
			}
		}
		
		return (IPath[]) list.toArray(new IPath[list.size()]);
	}

	public static IPath getRelativePath(IIncludePathEntry selection) {
		return selection.getPath().removeFirstSegments(2);
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
