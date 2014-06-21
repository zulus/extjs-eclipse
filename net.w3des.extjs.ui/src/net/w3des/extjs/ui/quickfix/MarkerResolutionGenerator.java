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
package net.w3des.extjs.ui.quickfix;

import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.internal.core.validation.problem.ProblemAttributes;
import net.w3des.extjs.internal.core.validation.problem.ProblemIdentifier;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.wst.jsdt.core.IJavaScriptModelMarker;

/**
 * Quickfix marker for extjs validation markers
 * 
 * @author mepeisen
 */
public class MarkerResolutionGenerator implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			switch (marker.getAttribute(IJavaScriptModelMarker.ID, -1)) {
				case ProblemIdentifier.ENV_MISSING_CORE: {
					// missing core, show quickfix to discover the extjs core sdk
					final String version = marker.getAttribute(ProblemAttributes.KEY_CORE_VERSION, "");
					if (version != null && version.length() > 0) {
						return new IMarkerResolution[]{new MissingCoreQuickfix(version)};
					}
				}
				break;
			}
		}
		catch (Exception ex) {
			ExtJSCore.error(ex);
		}
		return new IMarkerResolution[0];
	}

}
