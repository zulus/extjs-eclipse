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
	EXT("Ext"),
	EXT_DOT("Ext."),
	CREATE("create"),
	DEFINE("define"),
	DOT_PROTOTYPE(".prototype"),
	BASE_CLASS("Ext.Base"),
	BASE_CLASS_NAME("Base"),
	APPLY("apply"),
	APPLICATION("application"),
	APPLICATION_CLASS("Ext.app.Application");
	
	private final char[] asChar;
	private final String asString;
	
	Constants(String str) {
		asString = str;
		asChar = str.toCharArray();
	}
	
	public char[] asArray() {
		return asChar;
	}
	
	public String asString() {
		return asString;
	}
	
	@Deprecated
    public final static char[] ext = new char[] { 'E', 'x', 't' };
	@Deprecated
    public final static char[] extDot = new char[] { 'E', 'x', 't', '.' };
    @Deprecated
    public final static char[] create = new char[] { 'c', 'r', 'e', 'a', 't', 'e' };
    @Deprecated
    public final static char[] define = new char[] { 'd', 'e', 'f', 'i', 'n', 'e' };
    @Deprecated
    public final static char[] prototypeDot = new char[] { '.', 'p', 'r', 'o', 't', 'o', 't', 'y', 'p', 'e' };
    @Deprecated
    public final static char[] baseClass = new char[] { 'E', 'x', 't', '.', 'B', 'a', 's', 'e' };
    @Deprecated
    public final static char[] baseName = new char[] { 'B', 'a', 's', 'e' };
    @Deprecated
    public final static char[] apply = new char[] { 'a', 'p', 'p', 'l', 'y' };
    @Deprecated
    public final static char[] applyIf = new char[] { 'a', 'p', 'p', 'l', 'y', 'I', 'f' };
    @Deprecated
    public final static char[] extendedClass = new char[] { 'E', 'x', 't', '.', 'B', 'a', 's', 'e', '.', 'A', 'n', 'o' };
    @Deprecated
    public final static char[] attrExtend = new char[] { 'e', 'x', 't', 'e', 'n', 'd' };
    @Deprecated
    public final static char[] attrAlias = new char[] { 'a', 'l', 'i', 'a', 's' };
    @Deprecated
    public final static char[] attrUses = new char[] { 'u', 's', 'e', 's' };
    @Deprecated
    public final static char[] attrRequires = new char[] { 'r', 'e', 'q', 'u', 'i', 'r', 'e', 's' };
    @Deprecated
    public final static char[] attrOverride = new char[] { 'o', 'v', 'e', 'r', 'r', 'i', 'd', 'e' };
    @Deprecated
    public final static char[] attrSingleton = new char[] { 's', 'i', 'n', 'g', 'l', 'e', 't', 'o', 'n' };
    @Deprecated
    public final static char[] attrStatics = new char[] { 's', 't', 'a', 't', 'i', 'c', 's' };
    @Deprecated
    public final static char[] attrInheritableStatics = new char[] { 'i', 'n', 'h', 'e', 'r', 'i', 't', 'a', 'b', 'l', 'e', 'S', 't', 'a', 't', 'i', 'c', 's' };
    @Deprecated
    public final static char[] attrAlternateClassName = new char[] { 'a', 'l', 't', 'e', 'r', 'n', 'a', 't', 'e', 'C', 'l', 'a', 's', 's', 'N', 'a', 'm', 'e' };
    @Deprecated
    public final static char[] attrMixins = new char[] { 'm', 'i', 'x', 'i', 'n', 's' };
    @Deprecated
    public final static char[] attrConstructor = new char[] { 'c', 'o', 'n', 's', 't', 'r', 'u', 'c', 't', 'o', 'r' };
    @Deprecated
    public final static char[] attrListeners = new char[] { 'l', 'i', 's', 't', 'e', 'n', 'e', 'r', 's' };
    @Deprecated
    public final static char[] attrScope = new char[] { 's', 'c', 'o', 'p', 'e' };
    @Deprecated
	public final static char[] postConstructor = new char[] { '$', '_', 'p', 'o', 's', 't', 'C', 'o', 'n', 's', 't', 'r', 'u', 'c', 't', 'o', 'r' };
}
