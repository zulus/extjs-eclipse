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
package net.w3des.extjs.internal.core.validation;

import java.util.HashSet;

import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.validation.problem.ProblemIdentifier;
import net.w3des.extjs.internal.core.validation.problem.ProblemSeverity;
import net.w3des.extjs.internal.core.validation.problem.ValidationProblem;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.jsdt.core.IBuffer;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.compiler.BuildContext;
import org.eclipse.wst.jsdt.core.compiler.CategorizedProblem;
import org.eclipse.wst.jsdt.core.compiler.ReconcileContext;
import org.eclipse.wst.jsdt.core.compiler.ValidationParticipant;

/**
 * TODO Configurable severity
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class TrailingCommaValidation extends ValidationParticipant {

	@Override
	public boolean isActive(IJavaScriptProject project) {
		return ExtJSCore.getProjectManager().isExtJSProject(project.getProject());
	}

	@Override
	public void reconcile(ReconcileContext context) {
		super.reconcile(context);
		try {
			IResource resource = context.getWorkingCopy().getCorrespondingResource();
			if (resource instanceof IFile && !resource.isDerived(IResource.CHECK_ANCESTORS)) {
				IBuffer buffer = context.getWorkingCopy().getBuffer();
				context.putProblems(ValidationProblem.MARKER_TYPE, validate(buffer.getCharacters(), resource.getFullPath().toString().toCharArray()));
			}
		} catch (JavaScriptModelException e) {
		}
	}

	@Override
	public void buildStarting(BuildContext[] files, boolean isBatch) {
		super.buildStarting(files, isBatch);
		if (files == null) {
			return;
		}

		for (BuildContext context : files) {
			if (context.getFile().isDerived(IResource.CHECK_ANCESTORS)) {
				continue;
			}
			try {
				context.getFile().deleteMarkers(ValidationProblem.MARKER_TYPE, false, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
			}
			context.recordNewProblems(this.validate(context.getContents(), context.getFile().getName().toCharArray()));
		}
	}

	private CategorizedProblem[] validate(char[] contents, char[] fileName) {
		HashSet<CategorizedProblem> problems = new HashSet<CategorizedProblem>();
		int test = 0;
		int line = 1;
		char before = 0;
		boolean comment = false;
		boolean lineComment = false;
		boolean string = false;
		boolean dbString = false;
		boolean regex = false;
		boolean charSequence = false;
		for (int pos = 0; pos < contents.length; pos++) {
			final char current = contents[pos];
			if (current == '\n') { //$NON-NLS-1$
				line++;
				lineComment = false;
				before = 0;
				continue;
			} else if (lineComment || Character.isWhitespace(current)) {
				continue;
			} else if (before == '\\') {
				before = 0;
				continue;
			}
			switch(current) {
			case '[':
				if (regex) {
					charSequence = true;
				}
				break;
			case ']':
				charSequence = false;
				break;
			case '*':
				if (before == '/' && !dbString && !string && !regex) {
					comment = true;
				}
				break;
			case '\'':
				if (!regex && !dbString && !comment && !string) {
					string = true;
				} else if (string) {
					string = false;
				}
				break;
			case '"':
				if (!regex && !dbString && !comment && !string) {
					dbString = true;
				} else if (dbString) {
					dbString = false;
				}
				break;
			case '/':
				switch (before) {
				case '/':
					if (!lineComment && !regex && !dbString && !string) {
						lineComment = true;
						break;
					}
				case '*':
					if (comment) {
						comment = false;
						break;
					}
				default:
					if (!comment && !lineComment && !string && !dbString) {
						if (!regex) {
							regex = true;
						} else if (regex && !charSequence) {
							regex = false;
						}
					}
				}
				break;
			case ',':
				if (!comment && !lineComment && !regex && !dbString && !string) {
					test = pos;
					before = current;
					continue;
				}
			}
			before = current;

			if (test == 0) {
				continue;
			}

			if (!comment && !lineComment && !regex && !dbString && !string && (current == ']' || current == '}')) {
				problems.add(new ValidationProblem(ProblemIdentifier.TRAILING_COMMA, ValidationProblem.CAT_UNNECESSARY_CODE, ProblemSeverity.WARNING,
						"Trailing comma is not legal in an ECMA-262", null, fileName, test, test + 1, line));
			}
			test = 0;
		}

		return problems.toArray(new CategorizedProblem[problems.size()]);
	}

	@Override
	public void cleanStarting(IJavaScriptProject project) {
		super.cleanStarting(project);
		try {
			project.getProject().deleteMarkers(ValidationProblem.MARKER_TYPE, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			ExtJSCore.error(e);
		}
	}
}
