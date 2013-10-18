/*******************************************************************************
 * Copyright (c) 20013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.ui.language;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import net.w3des.extjs.core.language.ExtJSLanguageToolkit;
import net.w3des.extjs.internal.core.ExtJSCore;

public class ExtJSUILanguageToolkit extends AbstractDLTKUILanguageToolkit {
	private IPreferenceStore store;

	@Override
	public IDLTKLanguageToolkit getCoreToolkit() {
		return ExtJSLanguageToolkit.getDefault();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		if (store == null) {
			store = new ScopedPreferenceStore(InstanceScope.INSTANCE, ExtJSCore.getDefault().getBundle().getSymbolicName());
		}

		return store;
	}

}
