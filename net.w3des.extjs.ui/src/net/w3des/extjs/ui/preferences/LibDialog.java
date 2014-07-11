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

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.common.DialogField;
import net.w3des.extjs.ui.common.IDialogFieldListener;
import net.w3des.extjs.ui.common.IListAdapter;
import net.w3des.extjs.ui.common.LayoutUtil;
import net.w3des.extjs.ui.common.ListDialogField;
import net.w3des.extjs.ui.common.StringDialogField;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class LibDialog extends StatusDialog implements IDialogFieldListener {

	private StringDialogField fNameField;
	private ListDialogField fVersionsField;
	
	private List<Object> fExistingLibraries;
	
	private LibElement fEditedElement;
	
	private List<Object> selectedVersions = new ArrayList<Object>();

	public LibDialog(Shell parent, List<Object> existingLibraries, LibElement env) {
		super(parent);
		setTitle("New ExtJS library");
		
		fExistingLibraries= existingLibraries;
		
		fNameField= new StringDialogField();
		fNameField.setDialogFieldListener(this);
		fNameField.setLabelText("Name"); 
		
		fVersionsField = new ListDialogField(new VersionAdapter(), new String[]{}, new VersionLabelProvider());
		final List<IProjectFacetVersion> versionsList = new ArrayList<IProjectFacetVersion>();
		final IProjectFacet facet1 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		versionsList.addAll(facet1.getVersions());
		final IProjectFacet facet2 = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_TOUCH);
		versionsList.addAll(facet2.getVersions());
		this.fVersionsField.setElements(versionsList);
		this.fVersionsField.setLabelText("Versions");
		fVersionsField.setDialogFieldListener(this);
		
		this.fEditedElement = env;
		
		if (this.fEditedElement == null) {
			fNameField.setText(""); //$NON-NLS-1$
			fVersionsField.selectFirstElement();
		}
		else {
			fNameField.setText(env.getName());
			final List<IProjectFacetVersion> selection = new ArrayList<IProjectFacetVersion>();
			for (final Object v : env.getChildren()) {
				if (v instanceof EnvVersionElement) {
					final String[] vn = v.toString().split("/");
					if (vn[0].equals("extjs")) {
						selection.add(facet1.getVersion(vn[1]));
					}
					else if (vn[0].equals("touch")) {
						selection.add(facet2.getVersion(vn[1]));
					}
				}
			}
			fVersionsField.selectElements(new StructuredSelection(selection));
			if (env.getLibrary() != null && env.getLibrary().isBuiltin()) {
				fNameField.setEnabled(false);
				fVersionsField.setEnabled(false);
			}
		}
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fNameField, fVersionsField }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fNameField.getTextControl(null));
		fNameField.postSetFocusOnDialogField(parent.getDisplay());
		
		Dialog.applyDialogFont(composite);
		
		return composite;
	}
	
	public void dialogFieldChanged(DialogField field) {
		if (field == fNameField || field == fVersionsField) {
			updateStatus(validateSettings());
		}
		this.selectedVersions = this.fVersionsField.getSelectedElements();
	}
	
	private IStatus validateSettings() {
		String name= fNameField.getText();
		if (name.length() == 0) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Enter the environment name"); 
		}
		for (int i= 0; i < fExistingLibraries.size(); i++) {
			LibElement curr= (LibElement) fExistingLibraries.get(i);
			if (curr != fEditedElement && name.equals(curr.getName())) {
				return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, NLS.bind("Library {0} already exists", new Object[]{name})); 
			}
		}
		IStatus status = ResourcesPlugin.getWorkspace().validateName(name, IResource.PROJECT);
		if (status.matches(IStatus.ERROR)) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid name");
		}
		if (name.startsWith("core-")) {
			if (fEditedElement == null || fEditedElement.getLibrary() == null || !fEditedElement.getLibrary().isBuiltin())
			{
				return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid name");
			}
		}
		if (fVersionsField.getSelectedElements().size() == 0) {
			return new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Select at least one version");
		}
		
		return Status.OK_STATUS;
	}
	
	public String getName() {
		return fNameField.getText();
	}
	
	public String[] getSelectedVersions() {
		final List<String> versions = new ArrayList<String>();
		for (final Object sel : this.selectedVersions) {
			final IProjectFacetVersion fv = (IProjectFacetVersion) sel;
			versions.add((fv.getProjectFacet().equals(ExtJSNature.getExtjsFacet()) ? "extjs/" : "touch/") + fv.getVersionString());
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
