/*******************************************************************************
 * Copyright (c) 2013 The PDT Extension Group (https://github.com/pdt-eg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package net.w3des.extjs.core.language;


import net.w3des.extjs.core.ExtJSNature;

import org.eclipse.dltk.core.AbstractLanguageToolkit;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class ExtJSLanguageToolkit extends AbstractLanguageToolkit {
	public static final String CONTENT_TYPE = "org.eclipse.wst.jsdt.core.jsSource"; //$NON-NLS-1$


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
		return "org.eclipse.wst.jsdt.core.jsSource"; //$NON-NLS-1$
	}
}
