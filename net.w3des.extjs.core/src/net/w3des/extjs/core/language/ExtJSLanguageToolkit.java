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
package net.w3des.extjs.core.language;

import java.io.File;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.dltk.core.environment.IFileHandle;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class ExtJSLanguageToolkit extends AbstractLanguageToolkit {
	public static final String CONTENT_TYPE = "net.w3des.extjs.core.extjsContentType"; //$NON-NLS-1$
	private static final IContentType JS_CONTENT_TYPE = Platform.getContentTypeManager().getContentType(CONTENT_TYPE);
	
	private static ExtJSLanguageToolkit instance; 

	public static ExtJSLanguageToolkit getDefault() {
		if (instance == null) {
			instance = new ExtJSLanguageToolkit();
		}
		return instance;
	}
	
	@Override
	public String getNatureId() {
		return ExtJSNature.NATURE_ID;
	}

	@Override
	public String getLanguageName() {
		return "ExtJS"; //$NON-NLS-1$
	}

	@Override
	public String getLanguageContentType() {
		return CONTENT_TYPE; //$NON-NLS-1$
	}
	
	
	@Override
	public IStatus validateSourceModule(IResource resource) {
		 IStatus s = (JS_CONTENT_TYPE.isAssociatedWith(resource.getName())) ? Status.OK_STATUS : new Status(Status.ERROR, ExtJSCore.PLUGIN_ID, "");
		 
		 return s; 
	}
	
	@Override
	public String getPreferenceQualifier() {
		return ExtJSCore.PLUGIN_ID; //$NON-NLS-1$
	}
	
	@Override
	public boolean canValidateContent(File file) {
		return JS_CONTENT_TYPE.isAssociatedWith(file.getName());
	}
	
	@Override
	public boolean canValidateContent(IFileHandle file) {
		return JS_CONTENT_TYPE.isAssociatedWith(file.getName());
	}
	
	@Override
	public boolean canValidateContent(IResource resource) {
		return super.canValidateContent(resource) && isJSFile(resource);
	}
	
	public boolean isJSFile(IResource resource) {
		if (IResource.FILE != resource.getType()) {
			return false;
		}
		IFile file = (IFile) resource;
		
		for (IContentType type : Platform.getContentTypeManager().findContentTypesFor(file.getName())) {
			if (type.isKindOf(JS_CONTENT_TYPE)) {
				return true;
			}
		}
		
		return false;
	}
	
}
