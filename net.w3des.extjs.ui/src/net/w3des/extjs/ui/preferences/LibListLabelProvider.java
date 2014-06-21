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
package net.w3des.extjs.ui.preferences;

import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.ui.SharedImages;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;

public class LibListLabelProvider extends LabelProvider {
	
	private ISharedImages fSharedImages;
	
	
	public LibListLabelProvider() {
		fSharedImages= JavaScriptUI.getSharedImages();
	}
	
	public String getText(Object element) {
		if (element instanceof ILibListElement) {
			return ((ILibListElement) element).getName();
		}
		return super.getText(element);
	}	
		
	public Image getImage(Object element) {
		if (element instanceof LibElement) {
			final IExtJSLibrary lib = ((LibElement) element).getLibrary();
			return (lib != null && lib.isBuiltin()) ? SharedImages.getImage(SharedImages.OBJ16.EXT) : fSharedImages.getImage(ISharedImages.IMG_OBJS_LIBRARY);
		} else if (element instanceof LibSourceElement) {
			switch (((LibSourceElement) element).getType()) {
			case JAVASCRIPT_FILE:
				return fSharedImages.getImage(ISharedImages.IMG_OBJS_CFILE);
			case FOLDER:
				return PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ISharedImages.IMG_OBJ_FOLDER);
			case ZIP:
				return fSharedImages.getImage(ISharedImages.IMG_OBJS_EXTERNAL_ARCHIVE);
			}
			return null;
		} else if (element instanceof LibVersionElement) {
			return  fSharedImages.getImage(ISharedImages.IMG_OBJS_ENUM);
		}
		return null;
	}


}	
