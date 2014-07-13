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
package net.w3des.extjs.ui.properties;

import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.ui.SharedImages;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;

public class ListLabelProvider extends LabelProvider {
	
	private ISharedImages fSharedImages;
	
	
	public ListLabelProvider() {
		fSharedImages= JavaScriptUI.getSharedImages();
	}
	
	public String getText(Object element) {
		if (element instanceof IListElement) {
			return ((IListElement) element).getName();
		}
		return super.getText(element);
	}	
		
	public Image getImage(Object element) {
		if (element instanceof EnvListElement) {
			final IExtJSEnvironment environment = ((EnvListElement) element).getEnv();
			return (environment != null && environment.isBuiltin()) ? SharedImages.getImage(SharedImages.OBJ16.EXT) : SharedImages.getImage(SharedImages.OBJ16.CMD);
		} else if (element instanceof LibListElement) {
			return  fSharedImages.getImage(ISharedImages.IMG_OBJS_LIBRARY);
		}
		return null;
	}


}	
