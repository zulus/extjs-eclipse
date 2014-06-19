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

import org.eclipse.wst.jsdt.core.compiler.CategorizedProblem;
import org.eclipse.wst.jsdt.core.compiler.CharOperation;
import org.eclipse.wst.jsdt.internal.compiler.problem.DefaultProblem;

public class ValidationProblem extends CategorizedProblem {

	public static String MARKER_TYPE = "net.w3des.extjs.validation.problem";

	private String[] arguments;
	private String message;
	private int id;
	private int category = CAT_UNSPECIFIED;
	private char[] fileName;
	private int sourceStart;
	private int sourceEnd;
	private int sourceLine;
	private ProblemSeverity severity;
	
	private String[] extraAttributeNames = CharOperation.NO_STRINGS;
	private Object[] extraAttributes = DefaultProblem.EMPTY_VALUES;
	
	public ValidationProblem(int id, int category, ProblemSeverity severity, String message, String[] arguments, char[] fileName, int sourceStart, int sourceEnd, int lineNumber) {
		this.id = id;
		this.category = category;
		this.message = message;
		this.arguments = arguments;
		this.fileName = fileName;
		this.sourceStart = sourceStart;
		this.sourceEnd = sourceEnd;
		this.sourceLine = lineNumber;
		this.severity = severity;
	}
	
	public ValidationProblem(int id, int category, ProblemSeverity severity, String message, String[] arguments, String[] extraAttributeNames, Object[] extraAttributes, char[] fileName, int sourceStart, int sourceEnd, int lineNumber) {
		this.id = id;
		this.category = category;
		this.message = message;
		this.arguments = arguments;
		this.fileName = fileName;
		this.sourceStart = sourceStart;
		this.sourceEnd = sourceEnd;
		this.sourceLine = lineNumber;
		this.severity = severity;
		this.extraAttributeNames = extraAttributeNames;
		this.extraAttributes = extraAttributes;
	}
	
	public ProblemSeverity getSeverity() {
		return severity;
	}

	@Override
	public String[] getArguments() {
		return arguments == null ? new String[0] : arguments;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public char[] getOriginatingFileName() {
		return fileName;
	}

	@Override
	public int getSourceEnd() {
		return sourceEnd;
	}

	@Override
	public int getSourceLineNumber() {
		return sourceLine;
	}

	@Override
	public int getSourceStart() {
		return sourceStart;
	}

	@Override
	public boolean isError() {
		return severity == ProblemSeverity.ERROR;
	}

	@Override
	public boolean isWarning() {
		return severity == ProblemSeverity.WARNING;
	}

	@Override
	public void setSourceEnd(int sourceEnd) {
		this.sourceEnd = sourceEnd;
	}

	@Override
	public void setSourceLineNumber(int lineNumber) {
		this.sourceLine = lineNumber;
	}

	@Override
	public void setSourceStart(int sourceStart) {
		this.sourceStart = sourceStart;
	}

	@Override
	public int getCategoryID() {
		return category;
	}

	@Override
	public String getMarkerType() {
		return MARKER_TYPE;
	}

	@Override
	public String[] getExtraMarkerAttributeNames() {
		return this.extraAttributeNames;
	}

	@Override
	public Object[] getExtraMarkerAttributeValues() {
		return this.extraAttributes;
	}

}
