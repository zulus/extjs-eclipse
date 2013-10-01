/*******************************************************************************
 * Copyright (c) 2013 The PDT Extension Group (https://github.com/pdt-eg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package net.w3des.extjs.core.language;

import net.w3des.extjs.core.ExtJSNature;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.AbstractLanguageToolkit;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class ExtJSLanguageToolkit extends AbstractLanguageToolkit {
	public static final String CONTENT_TYPE = "org.eclipse.wst.jsdt.core.jsSource"; //$NON-NLS-1$
	private static final IContentType JS_CONTENT_TYPE = Platform.getContentTypeManager().getContentType(CONTENT_TYPE);


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
		return (JS_CONTENT_TYPE.isAssociatedWith(resource.getName())) ? Status.OK_STATUS : Status.CANCEL_STATUS;
	}
}
