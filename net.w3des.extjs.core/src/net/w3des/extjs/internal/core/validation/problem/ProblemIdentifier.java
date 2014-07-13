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
package net.w3des.extjs.internal.core.validation.problem;

public class ProblemIdentifier {
	
	// *** source level problems
	public static final int TRAILING_COMMA = 0x0001;

	// *** source level; file or class related
	
	/** there is a extra binary file on extjs source folder */
	public static final int EXTRA_BINARY_FILE = 0x0100;
	/** there are extra dots in javascript file names (extjs loader cannot load this file) */
	public static final int EXTRA_DOTS_IN_FILENAME = 0x0101;
	/** a class files does not declare class with given type name */
	public static final int MISSING_EXTJS_CLASS = 0x0102;

	// *** project level problems
	/** incompatible extjs version of the build environment */
	public static final int ENV_INCOMPATIBLE_VERSION = 0x1000;
	/** incompatible extjs version of a library */
	public static final int LIB_INCOMPATIBLE_VERSION = 0x1001;
	/** incompatible extjs version of a referenced project */
	public static final int PRJ_INCOMPATIBLE_VERSION = 0x1002;
	
	/** missing build environment */
	public static final int ENV_MISSING = 0x1010;
	/** missing core library */
	public static final int ENV_MISSING_CORE = 0x1011;
	/** missing library */
	public static final int LIB_MISSING = 0x1012;
	
	/** extjs source file is not a javascript source */
	public static final int EXTJS_SOURCE_NOT_A_SOURCE = 0x1020;
	
}
