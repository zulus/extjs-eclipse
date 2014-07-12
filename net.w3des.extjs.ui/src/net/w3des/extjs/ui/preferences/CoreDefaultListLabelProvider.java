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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;

public class CoreDefaultListLabelProvider extends LabelProvider {
	
	private ISharedImages fSharedImages;
	
	public CoreDefaultListLabelProvider() {
		fSharedImages= JavaScriptUI.getSharedImages();
	}
	
	public String getText(Object element) {
		if (element instanceof CoreDefaultLibElement && ((CoreDefaultLibElement) element).isDefault()) {
			return "[default] " + super.getText(element);
		}
		return super.getText(element);
	}
		
	public Image getImage(Object element) {
		if (element instanceof CoreDefaultLibElement) {
			return  fSharedImages.getImage(ISharedImages.IMG_OBJS_LIBRARY);
		} else if (element instanceof CoreDefaultFVElement) {
			return  fSharedImages.getImage(ISharedImages.IMG_OBJS_ENUM);
		}
		return null;
	}


}	
