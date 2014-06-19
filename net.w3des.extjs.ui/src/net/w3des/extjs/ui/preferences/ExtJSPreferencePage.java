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
package net.w3des.extjs.ui.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import net.w3des.extjs.ui.ExtJSUI;

/**
 * 
 */
public class ExtJSPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public ExtJSPreferencePage() {
		super(GRID);
		setPreferenceStore(ExtJSUI.getDefault().getPreferenceStore());
		setDescription("ExtJS preference pages. Select one of the sub pages.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
	}

	public void init(IWorkbench workbench) {
	}
	
}