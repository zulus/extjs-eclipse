/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package net.w3des.extjs.ui.preferences;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class CoreDefaultLibListElementSorter extends ViewerComparator {
	
	private static final int VERSION = 0;
	private static final int LIBRARY = 1;
	
	private static final int OTHER = 20;
	
	public int category(Object obj) {
		if (obj instanceof CoreDefaultLibElement) {
			return LIBRARY;
		}
		else if (obj instanceof CoreDefaultFVElement) {
			return VERSION;
		}
		return OTHER;
	}
	
	public int compare(Viewer viewer, Object e1, Object e2) {
		
        int cat1 = category(e1);
        int cat2 = category(e2);

        if (cat1 != cat2)
            return cat1 - cat2;
        
		if (viewer instanceof ContentViewer) {
			IBaseLabelProvider prov = ((ContentViewer) viewer).getLabelProvider();
            if (prov instanceof ILabelProvider) {
                ILabelProvider lprov = (ILabelProvider) prov;
                String name1 = lprov.getText(e1);
                String name2 = lprov.getText(e2);
                return getComparator().compare(name1, name2);
            }
		}
		return 0;
	}
	

}
