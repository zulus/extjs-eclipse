package net.w3des.extjs.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.jsdt.core.IJavaScriptElement;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.core.IPackageFragment;
import org.eclipse.wst.jsdt.core.IPackageFragmentRoot;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

public class Util {
	public static boolean isExtProject(IProject project) {
		try {
			IFacetedProject fProject = ProjectFacetsManager.create(project);
			
			return fProject.hasProjectFacet(ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT));
		} catch (CoreException e) {
			return false;
		}
	}
	
	public static boolean isExtProject(IJavaScriptProject project) {
		return isExtProject(project.getProject());
	}
	
	public static IType[] findTypes(String startWith, IJavaScriptProject project) {
		try {
			IPackageFragmentRoot[] fragments = project.getPackageFragmentRoots();
			
			List<IType> list = new LinkedList<IType>();
			for (IPackageFragmentRoot fragment : fragments) {
				IJavaScriptElement[] children = fragment.getChildren();
				for (IJavaScriptElement el : children) {
					for (IType type : seekTypes(startWith, el)) {
						if (type != null && type.getTypeQualifiedName() != null && type.getTypeQualifiedName().startsWith(startWith)) {
							list.add(type);
						}
					}
				}
			}
			
			return list.toArray(new IType[list.size()]);
		} catch (JavaScriptModelException e) {
			ExtJSCore.error(e);
			return new IType[0];
		}
	}
	
	private static IType[] seekTypes(String startWith, IJavaScriptElement el) {
		
		List<IType> list = new LinkedList<IType>();
		if (el instanceof IJavaScriptUnit) {
			IJavaScriptUnit u = (IJavaScriptUnit) el;
			try {
				if (u.getTypes() != null) {
					for (IType t : u.getTypes()) {
						if (!t.isAnonymous() && !t.isVirtual() && t.isClass()) {
							list.add(t);
						}
					}
				}
			} catch (JavaScriptModelException e) {
				ExtJSCore.error(e);
 			}
			
			
		} else if (el instanceof IPackageFragment) {
			try {
				for (IJavaScriptElement item : ((IPackageFragment) el).getChildren()) {
					list.addAll(Arrays.asList(seekTypes(startWith, item)));
				}
			} catch (JavaScriptModelException e) {
				ExtJSCore.error(e);
			}
		}
		
		return list.toArray(new IType[list.size()]);
	}
}
