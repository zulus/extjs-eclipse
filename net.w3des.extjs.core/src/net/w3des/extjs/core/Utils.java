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
package net.w3des.extjs.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import net.w3des.extjs.internal.core.ExtJSCore;

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

public class Utils {
    public static final Pattern PATTERN_JAVASCRIPT_NAMESPACE = Pattern.compile("^[$_\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}][$_\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}\\p{Mn}\\p{Mc}\\p{Nd}\\p{Pc}\\u200C\\u200D]*(\\.[$_\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}][$_\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}\\p{Mn}\\p{Mc}\\p{Nd}\\p{Pc}\\u200C\\u200D]*)*$");
    public static final Pattern PATTERN_JAVASCRIPT_CLASSNAME = Pattern.compile("^[$_\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}][$_\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}\\p{Mn}\\p{Mc}\\p{Nd}\\p{Pc}\\u200C\\u200D]*$");

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
    
    public static IType[] findTypeByName(String fullyQualifiedName, IJavaScriptProject project) {
    	try {
            IPackageFragmentRoot[] fragments = project.getPackageFragmentRoots();

            List<IType> list = new LinkedList<IType>();
            for (IPackageFragmentRoot fragment : fragments) {
                IJavaScriptElement[] children = fragment.getChildren();
                for (IJavaScriptElement el : children) {
                    for (IType type : seekTypes(el)) {
                        if (type != null && type.getTypeQualifiedName() != null && type.getTypeQualifiedName().equals(fullyQualifiedName)) {
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

    public static IType[] findTypes(String startWith, IJavaScriptProject project) {
        try {
            IPackageFragmentRoot[] fragments = project.getPackageFragmentRoots();

            List<IType> list = new LinkedList<IType>();
            for (IPackageFragmentRoot fragment : fragments) {
                IJavaScriptElement[] children = fragment.getChildren();
                for (IJavaScriptElement el : children) {
                    for (IType type : seekTypes(el)) {
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

    private static IType[] seekTypes(IJavaScriptElement el) {

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
                    list.addAll(Arrays.asList(seekTypes(item)));
                }
            } catch (JavaScriptModelException e) {
                ExtJSCore.error(e);
            }
        }

        return list.toArray(new IType[list.size()]);
    }
}
