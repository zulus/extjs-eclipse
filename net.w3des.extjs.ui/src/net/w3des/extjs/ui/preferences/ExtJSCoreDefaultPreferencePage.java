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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.IExtJSLibraryManager;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.ExtJSUI;
import net.w3des.extjs.ui.common.DialogField;
import net.w3des.extjs.ui.common.ITreeListAdapter;
import net.w3des.extjs.ui.common.LayoutUtil;
import net.w3des.extjs.ui.common.TreeListDialogField;
import net.w3des.extjs.ui.discovery.ExtJSDiscovery;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * 
 */
public class ExtJSCoreDefaultPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private TreeListDialogField fCoreLibList;
	
	private static final int IDX_DOWNLOAD = 0;
	private static final int IDX_CHANGE = 1;
	
	private Map<IProjectFacetVersion, IExtJSCoreLibrary> newDefaults = new HashMap<IProjectFacetVersion, IExtJSCoreLibrary>();
	
	public ExtJSCoreDefaultPreferencePage() {
		setPreferenceStore(ExtJSUI.getDefault().getPreferenceStore());
		setDescription("ExtJS builtin core libraries");
		noDefaultAndApplyButton();
		
		LibAdapter adapter= new LibAdapter();
		String[] buttonLabels= new String[] {
				"download sdk", 
				"change builtin sdk"
		};
		
		this.fCoreLibList = new TreeListDialogField(adapter, buttonLabels, new CoreDefaultListLabelProvider());
		this.fCoreLibList.setLabelText("Core Libraries");
		
		final List<CoreDefaultFVElement> list = new ArrayList<CoreDefaultFVElement>();
		for (final IProjectFacetVersion v : ExtJSNature.getExtjsFacet().getVersions()) {
			final CoreDefaultFVElement elm = new CoreDefaultFVElement(v);
			list.add(elm);
			for (final IExtJSCoreLibrary coreLib : ExtJSCore.getLibraryManager().getBuiltinCoreLibraries()) {
				if (coreLib.isCompatible(v)) {
					elm.addChild(coreLib);
				}
			}
		}
		for (final IProjectFacetVersion v : ExtJSNature.getSenchaTouchFacet().getVersions()) {
			final CoreDefaultFVElement elm = new CoreDefaultFVElement(v);
			list.add(elm);
			for (final IExtJSCoreLibrary coreLib : ExtJSCore.getLibraryManager().getBuiltinCoreLibraries()) {
				if (coreLib.isCompatible(v)) {
					elm.addChild(coreLib);
				}
			}
		}
		
		this.fCoreLibList.setElements(list);
		this.fCoreLibList.setViewerComparator(new CoreDefaultLibListElementSorter());
		
		doSelectionChanged(this.fCoreLibList); //update button enable state 
	}
	
	@Override
	public void applyData(Object data) {
		// empty
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	}	

	protected Control createContents(Composite parent) {
		Composite composite= new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { this.fCoreLibList }, true);
		LayoutUtil.setHorizontalGrabbing(this.fCoreLibList.getTreeControl(null));
		Dialog.applyDialogFont(composite);
		return composite;
	}
	
	public void init(IWorkbench workbench) {
	}
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true, new IRunnableWithProgress() { 
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					try {
						if (monitor != null) {
							monitor= new NullProgressMonitor();
						}
						
						updateDefaultLibs(monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					} finally {
						monitor.done();
					}
				}
			});
		} catch (InterruptedException e) {
			// cancelled by user
		} catch (InvocationTargetException e) {
			String title = "Error"; 
			String message = "Error applying extjs core library settings";
			ExtJSCore.error(message, e);
			MessageDialog.openError(getShell(), title, message);
		}
		return true;
	}
	
	private void updateDefaultLibs(IProgressMonitor monitor) throws CoreException {
		final IExtJSLibraryManager manager = ExtJSCore.getLibraryManager();
		for (final Map.Entry<IProjectFacetVersion, IExtJSCoreLibrary> defEntry : this.newDefaults.entrySet()) {
			manager.setDefaultCoreLib(defEntry.getKey(), defEntry.getValue());
		}
	}
	
	protected void doSelectionChanged(TreeListDialogField field) {
		List<Object> list = field.getSelectedElements();
		field.enableButton(IDX_DOWNLOAD, canDownload());
		field.enableButton(IDX_CHANGE, canChangeDefault(list));
	}
	
	protected void doCustomButtonPressed(TreeListDialogField field, int index) {
		if (index == IDX_DOWNLOAD) {
			doDownload();
		} else if (index == IDX_CHANGE) {
			doChangeDefault(field.getSelectedElements());
		}
	}
	
	protected void doDoubleClicked(TreeListDialogField field) {
		List<Object> selected = field.getSelectedElements();
		if (canChangeDefault(selected)) {
			doChangeDefault(field.getSelectedElements());
		}
	}
	
	protected void doKeyPressed(TreeListDialogField field, KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			// do nothing
		}
	}
	
	private void doChangeDefault(List<Object> selected) {
		if (selected.size() == 1) {
			Object curr = selected.get(0);
			if (curr instanceof CoreDefaultLibElement) {
				final CoreDefaultLibElement lib = (CoreDefaultLibElement) curr;
				final CoreDefaultFVElement parent = lib.getParent();
				final IProjectFacetVersion fv = parent.getFacetVersion();
				for (final CoreDefaultLibElement lib2 : parent.getChildren()) {
					if (lib2.isDefault()) {
						lib2.setDefault(false);
						this.fCoreLibList.refresh(lib2);
					}
				}
				lib.setDefault(true);
				this.newDefaults.put(fv, lib.getCoreLib());
				this.fCoreLibList.refresh(lib);
			}
			doSelectionChanged(this.fCoreLibList);
		}
	}
	
	private void doDownload() {
		ExtJSDiscovery.runExtJSDiscoveryDialog(null);
	}
	
	private boolean canDownload() {
		return true;
	}
	
	private boolean canChangeDefault(List<Object> list) {
		if (list.size() != 1)
			return false;
		return list.get(0) instanceof CoreDefaultLibElement && !((CoreDefaultLibElement)list.get(0)).isDefault();
	}

	
	private class LibAdapter implements ITreeListAdapter {
		
		private final Object[] EMPTY= new Object[0];

		public void customButtonPressed(TreeListDialogField field, int index) {
			doCustomButtonPressed(field, index);
		}

		public void selectionChanged(TreeListDialogField field) {
			doSelectionChanged(field);
		}
		
		public void doubleClicked(TreeListDialogField field) {
			doDoubleClicked(field);
		}

		public void keyPressed(TreeListDialogField field, KeyEvent event) {
			doKeyPressed(field, event);
		}

		public Object[] getChildren(TreeListDialogField field, Object element) {
			if (element instanceof CoreDefaultFVElement) {
				CoreDefaultFVElement elem = (CoreDefaultFVElement) element;
				return elem.getChildren();
			}
			return EMPTY;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			if (element instanceof CoreDefaultLibElement) {
				return ((CoreDefaultLibElement) element).getParent();
			}
			return null;
		}

		public boolean hasChildren(TreeListDialogField field, Object element) {
			return getChildren(field, element).length > 0;
		}
				
	}
	
}