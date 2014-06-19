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

import java.util.ArrayList;
import java.util.List;

import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.common.DialogField;
import net.w3des.extjs.ui.common.IDialogFieldListener;
import net.w3des.extjs.ui.common.IListAdapter;
import net.w3des.extjs.ui.common.LayoutUtil;
import net.w3des.extjs.ui.common.ListDialogField;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class ChooseVersionDialog extends StatusDialog implements IDialogFieldListener {

	private ListDialogField fVersionsField;
	
	private List<Object> selection = new ArrayList<Object>();
	
	public ChooseVersionDialog(Shell parent, List<IProjectFacetVersion> versions) {
		super(parent);
		setTitle("Choose compatibility versions");
		
		fVersionsField = new ListDialogField(new VersionAdapter(), new String[]{}, new VersionLabelProvider());
		this.fVersionsField.setElements(versions);
		this.fVersionsField.setLabelText("Versions");
		fVersionsField.setDialogFieldListener(this);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fVersionsField }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fVersionsField.getListControl(null));
		fVersionsField.postSetFocusOnDialogField(parent.getDisplay());
		
		Dialog.applyDialogFont(composite);
		
		return composite;
	}
	
	public void dialogFieldChanged(DialogField field) {
		if (field == fVersionsField) {
			updateStatus(validateSettings());
		}
		this.selection = this.fVersionsField.getSelectedElements();
	}
	
	private IStatus validateSettings() {
		if (fVersionsField.getSelectedElements().size() == 0) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Select at least one version");
		}
		return Status.OK_STATUS;
	}
	
	public String[] getSelectedVersions() {
		final List<String> versions = new ArrayList<String>();
		for (final Object sel : selection) {
			versions.add(((IProjectFacetVersion) sel).getVersionString());
		}
		return versions.toArray(new String[versions.size()]);
	}
	
	private final class VersionAdapter implements IListAdapter {

		@Override
		public void customButtonPressed(ListDialogField field, int index) {
			// ignore
		}

		@Override
		public void selectionChanged(ListDialogField field) {
			dialogFieldChanged(fVersionsField);
		}

		@Override
		public void doubleClicked(ListDialogField field) {
			// ignore
		}
		
	}
	
	private final class VersionLabelProvider extends LabelProvider {
		
	}

}
