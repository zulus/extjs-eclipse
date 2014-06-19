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

import net.w3des.extjs.core.api.IExtJSLibrary;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;

public class ChooseLibraryDialog extends StatusDialog implements IDialogFieldListener {

	private ListDialogField fLibsField;
	
	private List<Object> selection = new ArrayList<Object>();
	
	public ChooseLibraryDialog(Shell parent, List<IExtJSLibrary> versions) {
		super(parent);
		setTitle("Choose additional libraries");
		
		fLibsField = new ListDialogField(new LibraryAdapter(), new String[]{}, new LibraryLabelProvider());
		this.fLibsField.setElements(versions);
		this.fLibsField.setLabelText("Libraries");
		fLibsField.setDialogFieldListener(this);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fLibsField }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fLibsField.getListControl(null));
		fLibsField.postSetFocusOnDialogField(parent.getDisplay());
		
		Dialog.applyDialogFont(composite);
		
		return composite;
	}
	
	public void dialogFieldChanged(DialogField field) {
		if (field == fLibsField) {
			updateStatus(validateSettings());
		}
		this.selection = this.fLibsField.getSelectedElements();
	}
	
	private IStatus validateSettings() {
		if (fLibsField.getSelectedElements().size() == 0) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Select at least one library");
		}
		return Status.OK_STATUS;
	}
	
	public String[] getSelectedLibraries() {
		final List<String> versions = new ArrayList<String>();
		for (final Object sel : selection) {
			versions.add(((IExtJSLibrary) sel).getName());
		}
		return versions.toArray(new String[versions.size()]);
	}
	
	private final class LibraryAdapter implements IListAdapter {

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
	
	private final class LibraryLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof IExtJSLibrary) {
				return ((IExtJSLibrary) element).getName();
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof IExtJSLibrary) {
				return JavaScriptUI.getSharedImages().getImage(ISharedImages.IMG_OBJS_LIBRARY);
			}
			return super.getImage(element);
		}
		
	}

}
