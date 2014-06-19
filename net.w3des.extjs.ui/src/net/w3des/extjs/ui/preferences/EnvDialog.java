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

import net.w3des.extjs.core.api.CoreType;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.common.DialogField;
import net.w3des.extjs.ui.common.IDialogFieldListener;
import net.w3des.extjs.ui.common.IListAdapter;
import net.w3des.extjs.ui.common.IStringButtonAdapter;
import net.w3des.extjs.ui.common.LayoutUtil;
import net.w3des.extjs.ui.common.ListDialogField;
import net.w3des.extjs.ui.common.SelectionButtonDialogFieldGroup;
import net.w3des.extjs.ui.common.StringButtonDialogField;
import net.w3des.extjs.ui.common.StringDialogField;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class EnvDialog extends StatusDialog implements IDialogFieldListener {

	private StringDialogField fNameField;
	private ListDialogField fVersionsField;
	private SelectionButtonDialogFieldGroup fCoreTypeField;
	
	private List<Object> fExistingLibraries;
	
	private EnvElement fEditedElement;
	private StringButtonDialogField fPathField;
	
	private static final int IX_BUILTIN = 0;
	private static final int IX_EXTERNAL_FOLDER = 1;
	private static final int IX_EXTERNAL_ZIP = 2;
	
	private List<Object> selectedVersions = new ArrayList<Object>();

	public EnvDialog(Shell parent, List<Object> existingLibraries, EnvElement env) {
		super(parent);
		setTitle("New ExtJS execution environment");
		
		fExistingLibraries= existingLibraries;
		
		fNameField= new StringDialogField();
		fNameField.setDialogFieldListener(this);
		fNameField.setLabelText("Name"); 
		
		fVersionsField = new ListDialogField(new VersionAdapter(), new String[]{}, new VersionLabelProvider());
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		this.fVersionsField.setElements(facet.getVersions());
		this.fVersionsField.setLabelText("Versions");
		fVersionsField.setDialogFieldListener(this);
		
		final String[] typeNames = new String[]{
			"builtin",
			"external folder",
			"external zip"
		};
		fCoreTypeField = new SelectionButtonDialogFieldGroup(SWT.RADIO, typeNames, 1);
		fCoreTypeField.setLabelText("Core type");
		fCoreTypeField.setDialogFieldListener(this);
		
		fPathField = new StringButtonDialogField(new BrowseButtonAdapter());
		fPathField.setButtonLabel("Browse...");
		fPathField.setDialogFieldListener(this);
		
		this.fEditedElement = env;
		
		if (this.fEditedElement == null) {
			fNameField.setText(""); //$NON-NLS-1$
			fVersionsField.selectFirstElement();
			fCoreTypeField.setSelection(IX_BUILTIN, true);
			fPathField.setText("");
			fPathField.setEnabled(false);
		}
		else {
			fNameField.setText(env.getName());
			final List<IProjectFacetVersion> selection = new ArrayList<IProjectFacetVersion>();
			for (final Object v : env.getChildren()) {
				if (v instanceof EnvVersionElement) {
					selection.add(facet.getVersion(v.toString()));
				}
			}
			fVersionsField.selectElements(new StructuredSelection(selection));
			if (env.getEnvironment() != null && env.getEnvironment().isBuiltin()) {
				fNameField.setEnabled(false);
				fVersionsField.setEnabled(false);
			}
			
			switch (env.getCoreType()) {
			case NONE:
				fCoreTypeField.setSelection(IX_BUILTIN, true);
				fCoreTypeField.setSelection(IX_EXTERNAL_FOLDER, false);
				fCoreTypeField.setSelection(IX_EXTERNAL_ZIP, false);
				fPathField.setText("");
				fPathField.setEnabled(false);
				break;
			case FOLDER:
				fCoreTypeField.setSelection(IX_BUILTIN, false);
				fCoreTypeField.setSelection(IX_EXTERNAL_FOLDER, true);
				fCoreTypeField.setSelection(IX_EXTERNAL_ZIP, false);
				fPathField.setText(env.getCoreFileOrZip());
				break;
			case ZIP:
				fCoreTypeField.setSelection(IX_BUILTIN, false);
				fCoreTypeField.setSelection(IX_EXTERNAL_FOLDER, false);
				fCoreTypeField.setSelection(IX_EXTERNAL_ZIP, true);
				fPathField.setText(env.getCoreFileOrZip());
				break;
			}
		}
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fNameField, fVersionsField, fCoreTypeField, fPathField }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fNameField.getTextControl(null));
		fNameField.postSetFocusOnDialogField(parent.getDisplay());
		
		Dialog.applyDialogFont(composite);
		
		return composite;
	}
	
	public void dialogFieldChanged(DialogField field) {
		if (field == fNameField || field == fVersionsField || field == fCoreTypeField || field == fPathField) {
			updateStatus(validateSettings());
		}
		if (fCoreTypeField.isSelected(IX_BUILTIN)) {
			fPathField.setEnabled(false);
		}
		else {
			fPathField.setEnabled(true);
		}
		this.selectedVersions = this.fVersionsField.getSelectedElements();
	}
	
	private IStatus validateSettings() {
		String name= fNameField.getText();
		if (name.length() == 0) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Enter the environment name"); 
		}
		for (int i= 0; i < fExistingLibraries.size(); i++) {
			EnvElement curr= (EnvElement) fExistingLibraries.get(i);
			if (curr != fEditedElement && name.equals(curr.getName())) {
				return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, NLS.bind("Environment {0} already exists", new Object[]{name})); 
			}
		}
		IStatus status = ResourcesPlugin.getWorkspace().validateName(name, IResource.PROJECT);
		if (status.matches(IStatus.ERROR)) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid name");
		}
		if (name.startsWith("core-")) {
			if (fEditedElement == null || fEditedElement.getEnvironment() == null || !fEditedElement.getEnvironment().isBuiltin())
			{
				return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid name");
			}
		}
		if (fVersionsField.getSelectedElements().size() == 0) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Select at least one version");
		}
		
		if (fCoreTypeField.isSelected(IX_EXTERNAL_FOLDER)) {
			try {
				ExtJSCore.getLibraryManager().checkCore(CoreType.FOLDER, fPathField.getText());
			}
			catch (CoreException ex) {
				return ex.getStatus();
			}
		}
		
		if (fCoreTypeField.isSelected(IX_EXTERNAL_ZIP)) {
			try {
				ExtJSCore.getLibraryManager().checkCore(CoreType.ZIP, fPathField.getText());
			}
			catch (CoreException ex) {
				return ex.getStatus();
			}
		}
		
		return Status.OK_STATUS;
	}
	
	public String getName() {
		return fNameField.getText();
	}
	
	public String[] getSelectedVersions() {
		final List<String> versions = new ArrayList<String>();
		for (final Object sel : this.selectedVersions) {
			versions.add(((IProjectFacetVersion) sel).getVersionString());
		}
		return versions.toArray(new String[versions.size()]);
	}
	
	public CoreType getCoreType() {
		if (fCoreTypeField.isSelected(IX_EXTERNAL_FOLDER)) {
			return CoreType.FOLDER;
		}
		if (fCoreTypeField.isSelected(IX_EXTERNAL_ZIP)) {
			return CoreType.ZIP;
		}
		return CoreType.NONE;
	}
	
	public String getCorePath() {
		return this.fPathField.getText();
	}
	
	private void doBrowse() {
		if (fCoreTypeField.isSelected(IX_EXTERNAL_FOLDER)) {
			final String label = "Choose extjs sdk folder"; 
			final DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
			dialog.setText(label);
			final String path = dialog.open();
			// TODO set current directory
			// TODO set last path used during last creations for new environments (empty paths)
			if (path != null) {
				this.fPathField.setText(path);
			}
		}
		else {
			final String label = "Choose extjs zip"; 
			final FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			dialog.setText(label);
			dialog.setFilterExtensions(new String[] {"*.zip"}); //$NON-NLS-1$ //$NON-NLS-2$
			final String path = dialog.open();
			// TODO set current file
			// TODO set last path used during last creations for new environments (empty paths)
			if (path != null) {
				this.fPathField.setText(path);
			}
		}
	}
	
	private final class BrowseButtonAdapter implements IStringButtonAdapter {

		@Override
		public void changeControlPressed(DialogField field) {
			doBrowse();
		}
		
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
