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

import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.SharedImages;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ChooseEnvDialog extends StatusDialog implements IDialogFieldListener {

	private ListDialogField fEnvField;
	
	private List<Object> selection = new ArrayList<Object>();
	
	public ChooseEnvDialog(Shell parent, List<IExtJSEnvironment> envs) {
		super(parent);
		setTitle("Choose execution environment");
		
		fEnvField = new ListDialogField(new EnvAdapter(), new String[]{}, new EnvLabelProvider());
		this.fEnvField.setElements(envs);
		this.fEnvField.setLabelText("Environments");
		fEnvField.setDialogFieldListener(this);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fEnvField }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fEnvField.getListControl(null));
		fEnvField.postSetFocusOnDialogField(parent.getDisplay());
		
		Dialog.applyDialogFont(composite);
		
		return composite;
	}
	
	public void dialogFieldChanged(DialogField field) {
		if (field == fEnvField) {
			updateStatus(validateSettings());
		}
		this.selection = this.fEnvField.getSelectedElements();
	}
	
	private IStatus validateSettings() {
		if (fEnvField.getSelectedElements().size() != 1) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Select one environment");
		}
		return Status.OK_STATUS;
	}
	
	public String[] getSelectedEnvironments() {
		final List<String> versions = new ArrayList<String>();
		for (final Object sel : selection) {
			versions.add(((IExtJSEnvironment) sel).getName());
		}
		return versions.toArray(new String[versions.size()]);
	}
	
	private final class EnvAdapter implements IListAdapter {

		@Override
		public void customButtonPressed(ListDialogField field, int index) {
			// ignore
		}

		@Override
		public void selectionChanged(ListDialogField field) {
			dialogFieldChanged(field);
		}

		@Override
		public void doubleClicked(ListDialogField field) {
			// ignore
		}
		
	}
	
	private final class EnvLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof IExtJSLibrary) {
				return ((IExtJSLibrary) element).getName();
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof IExtJSEnvironment) {
				return ((IExtJSEnvironment)element).isBuiltin() ? SharedImages.getImage(SharedImages.OBJ16.EXT) : SharedImages.getImage(SharedImages.OBJ16.CMD);
			}
			return super.getImage(element);
		}
		
	}

}
