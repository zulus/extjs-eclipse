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

import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.ui.SharedImages;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;

public class EnvListLabelProvider extends LabelProvider {
	
	private ISharedImages fSharedImages;
	
	
	public EnvListLabelProvider() {
		fSharedImages= JavaScriptUI.getSharedImages();
	}
	
	public String getText(Object element) {
		if (element instanceof IEnvListElement) {
			return element.toString();
		}
		return super.getText(element);
	}	
		
	public Image getImage(Object element) {
		if (element instanceof EnvElement) {
			final IExtJSEnvironment environment = ((EnvElement) element).getEnvironment();
			return (environment != null && environment.isBuiltin()) ? SharedImages.getImage(SharedImages.OBJ16.EXT) : SharedImages.getImage(SharedImages.OBJ16.CMD);
		} else if (element instanceof EnvLibElement) {
			return  fSharedImages.getImage(ISharedImages.IMG_OBJS_LIBRARY);
		} else if (element instanceof EnvVersionElement) {
			return  fSharedImages.getImage(ISharedImages.IMG_OBJS_ENUM);
		}
		return null;
	}


}	
