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
package net.w3des.extjs.internal.core;

/**
 * Statics for infer engine and others in both form (char array and string)
 *
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public enum Constants {
	EXT("Ext"), //$NON-NLS-1$
	EXT_DOT("Ext."), //$NON-NLS-1$
	CREATE("create"), //$NON-NLS-1$
	DEFINE("define"), //$NON-NLS-1$
	DOT_PROTOTYPE(".prototype"), //$NON-NLS-1$
	BASE_CLASS("Ext.Base"), //$NON-NLS-1$
	BASE_CLASS_NAME("Base"), //$NON-NLS-1$
	APPLY("apply"), //$NON-NLS-1$
	APPLY_IF("applyIf"), //$NON-NLS-1$
	APPLICATION("application"), //$NON-NLS-1$
	APPLICATION_CLASS("Ext.app.Application"), //$NON-NLS-1$
	EXTENDED_CLASS("Ext.Base.Ano"), //$NON-NLS-1$
	ATTR_EXTEND("extend"), //$NON-NLS-1$
	ATTR_ALIAS("alias"), //$NON-NLS-1$
	ATTR_USES("uses"), //$NON-NLS-1$
	ATTR_REQUIRES("requires"), //$NON-NLS-1$
	ATTR_OVERRIDE("override"), //$NON-NLS-1$
	ATTR_SINGLETON("singleton"), //$NON-NLS-1$
	ATTR_STATICS("statics"), //$NON-NLS-1$
	ATTR_INHERITABLE_STATICS("inheritableStatics"), //$NON-NLS-1$
	ATTR_ALTERNATE_CLASS_NAME("alternateClassName"), //$NON-NLS-1$
	ATTR_MIXINS("mixins"), //$NON-NLS-1$
	ATTR_CONSTRUCTOR("constructor"), //$NON-NLS-1$
	ATTR_LISTENERS("listeners"), //$NON-NLS-1$
	ATTR_SCOPE("scope"), //$NON-NLS-1$
	POST_CONSTRUCTOR("$_postConstructor") //$NON-NLS-1$
	;
	public final char[] asChar;
	public final String asString;

	Constants(String str) {
		asString = str;
		asChar = str.toCharArray();
	}
}
