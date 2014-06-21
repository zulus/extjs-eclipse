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
import java.util.List;

import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.LibrarySourceType;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.ExtJSUI;
import net.w3des.extjs.ui.common.DialogField;
import net.w3des.extjs.ui.common.ITreeListAdapter;
import net.w3des.extjs.ui.common.LayoutUtil;
import net.w3des.extjs.ui.common.TreeListDialogField;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * 
 */
public class ExtJSLibraryPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private TreeListDialogField fLibList;
	
	private static final int IDX_NEW = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_ADD_FILE = 2;
	private static final int IDX_ADD_FOLDER = 3;
	private static final int IDX_ADD_ZIP = 4;
	private static final int IDX_ADD_VERSION = 5;
	private static final int IDX_REMOVE = 6;
	
	private List<LibElement> removedLibraries = new ArrayList<LibElement>();

	public ExtJSLibraryPreferencePage() {
		setPreferenceStore(ExtJSUI.getDefault().getPreferenceStore());
		setDescription("ExtJS libraries");
		noDefaultAndApplyButton();
		
		LibAdapter adapter= new LibAdapter();
		String[] buttonLabels= new String[] {
				"new", 
				"edit", 
				"add file", 
				"add folder",
				"add zip",
				"add version",
				"remove"
		};
		
		this.fLibList = new TreeListDialogField(adapter, buttonLabels, new LibListLabelProvider());
		this.fLibList.setLabelText("Libraries");
		
		final IExtJSLibrary[] libs = ExtJSCore.getLibraryManager().getLibraries();
		final List<LibElement> list = new ArrayList<LibElement>();
		for (final IExtJSLibrary lib : libs) {
			list.add(new LibElement(lib));
		}
		this.fLibList.setElements(list);
		this.fLibList.setViewerComparator(new LibListElementSorter());
		
		doSelectionChanged(this.fLibList); //update button enable state 
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
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { this.fLibList }, true);
		LayoutUtil.setHorizontalGrabbing(this.fLibList.getTreeControl(null));
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
						
						updateLibraries(monitor);
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
			String message = "Error applying extjs library settings";
			ExtJSCore.error(message, e);
			MessageDialog.openError(getShell(), title, message);
		}
		return true;
	}
	
	private void updateLibraries(IProgressMonitor monitor) throws CoreException {
		// TODO
	}
	
	protected void doSelectionChanged(TreeListDialogField field) {
		List<Object> list = field.getSelectedElements();
		field.enableButton(IDX_REMOVE, canRemove(list));
		field.enableButton(IDX_EDIT, canEdit(list));
		field.enableButton(IDX_ADD_FILE, canAddSourceElement(list));
		field.enableButton(IDX_ADD_FOLDER, canAddSourceElement(list));
		field.enableButton(IDX_ADD_ZIP, canAddSourceElement(list));
		field.enableButton(IDX_ADD_VERSION, canAddVersion(list));
	}
	
	protected void doCustomButtonPressed(TreeListDialogField field, int index) {
		if (index == IDX_NEW) {
			doAddNew();
		} else if (index == IDX_ADD_FILE) {
			doAddFile(field.getSelectedElements());
		} else if (index == IDX_ADD_FOLDER) {
			doAddFolder(field.getSelectedElements());
		} else if (index == IDX_ADD_ZIP) {
			doAddZip(field.getSelectedElements());
		} else if (index == IDX_ADD_VERSION) {
			doAddVersion(field.getSelectedElements());
		} else if (index == IDX_REMOVE) {
			doRemove(field.getSelectedElements());
		} else if (index == IDX_EDIT) {
			doEdit(field.getSelectedElements());
		}
	}
	
	protected void doDoubleClicked(TreeListDialogField field) {
		List<Object> selected = field.getSelectedElements();
		if (canEdit(selected)) {
			doEdit(field.getSelectedElements());
		}
	}
	
	protected void doKeyPressed(TreeListDialogField field, KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			List<Object> selection = field.getSelectedElements();
			if (canRemove(selection)) {
				doRemove(selection);
			}
		}
	}
	
	private void doEdit(List<Object> selected) {
		if (selected.size() == 1) {
			Object curr = selected.get(0);
			if (curr instanceof LibElement) {
				editLibElement((LibElement) curr);
			}
			doSelectionChanged(this.fLibList);
		}
	}
	
	private void doAddNew() {
		final LibDialog dialog = new LibDialog(getShell(), this.fLibList.getElements(), null);
		if (dialog.open() == Window.OK) {
			final LibElement element = new LibElement(dialog.getName(), dialog.getSelectedVersions());
			fLibList.addElement(element);
		}
	}

	private void editLibElement(LibElement element) {
		final LibDialog dialog = new LibDialog(getShell(), this.fLibList.getElements(), element);
		if (dialog.open() == Window.OK) {
			element.setName(dialog.getName());
			element.setVersions(dialog.getSelectedVersions());
			fLibList.refresh(element);
		}
	}

	private void doRemove(List<Object> selected) {
		Object selectionAfter= null;
		for (int i= 0; i < selected.size(); i++) {
			Object curr = selected.get(i);
			if (curr instanceof LibElement) {
				if (!((LibElement) curr).isNew()) {
					this.removedLibraries.add((LibElement) curr);
					((LibElement) curr).delete();
				}
				this.fLibList.removeElement(curr);
			}
			else if (curr instanceof LibSourceElement) {
				final LibElement parent = ((LibSourceElement) curr).getParent();
				((LibSourceElement) curr).delete();
				parent.remove((LibSourceElement) curr);
				this.fLibList.removeElement(curr);
				selectionAfter = parent;
			}
			else if (curr instanceof LibVersionElement) {
				final LibElement parent = ((LibVersionElement) curr).getParent();
				((LibVersionElement) curr).delete();
				parent.remove((LibVersionElement) curr);
				this.fLibList.removeElement(curr);
				selectionAfter = parent;
			}
		}
		if (this.fLibList.getSelectedElements().isEmpty()) {
			if (selectionAfter != null) {
				this.fLibList.selectElements(new StructuredSelection(selectionAfter));
			} else {
				this.fLibList.selectFirstElement();	
			}
		} else {
			doSelectionChanged(this.fLibList);
		}
	}

	private void doAddVersion(List<Object> list) {
		if (canAddVersion(list)) {
			final LibElement env = (LibElement) list.get(0);
			final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
			final List<IProjectFacetVersion> facets = new ArrayList<IProjectFacetVersion>(facet.getVersions());
			final List<String> newVersions = new ArrayList<String>();
			for (final Object child : env.getChildren())
			{
				if (child instanceof LibVersionElement)
				{
					facets.remove(facet.getVersion(((LibVersionElement) child).getName()));
					newVersions.add(((LibVersionElement) child).getName());
				}
			}
			final ChooseVersionDialog dialog = new ChooseVersionDialog(getShell(), facets);
			if (dialog.open() == Window.OK) {
				for (final String v : dialog.getSelectedVersions()) {
					newVersions.add(v);
				}
				env.setVersions(newVersions.toArray(new String[newVersions.size()]));
				fLibList.refresh(env);
			}
		}
	}

	private void doAddFile(List<Object> list) {
		if (canAddSourceElement(list)) {
			final LibElement lib = (LibElement) list.get(0);
			final FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText("Choose extjs javascript files");
			dialog.setFilterExtensions(new String[]{"*.js"});
			final String fn = dialog.open();
			if (fn != null) {
				final String[] files = dialog.getFileNames();
				for (final String fnam : files) {
					lib.addSource(LibrarySourceType.JAVASCRIPT_FILE, new Path(dialog.getFilterPath()).append(fnam), new String[0], new String[0]);
		        }
				fLibList.refresh(lib);
			}
		}
	}

	private void doAddFolder(List<Object> list) {
		if (canAddSourceElement(list)) {
			final LibElement lib = (LibElement) list.get(0);
			final DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
			dialog.setText("Choose extjs javascript folders");
			final String fn = dialog.open();
			if (fn != null) {
				lib.addSource(LibrarySourceType.FOLDER, new Path(fn), new String[]{"**/*.js"}, new String[0]);
				fLibList.refresh(lib);
			}
		}
	}

	private void doAddZip(List<Object> list) {
		if (canAddSourceElement(list)) {
			final LibElement lib = (LibElement) list.get(0);
			final FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText("Choose extjs zip archives");
			dialog.setFilterExtensions(new String[]{"*.js"});
			final String fn = dialog.open();
			if (fn != null) {
				final String[] files = dialog.getFileNames();
				for (final String fnam : files) {
					lib.addSource(LibrarySourceType.ZIP, new Path(dialog.getFilterPath()).append(fnam), new String[]{"**/*.js"}, new String[0]);
		        }
				fLibList.refresh(lib);
			}
		}
	}
	
	private boolean canAddVersion(List<Object> list) {
		if (list.size() != 1)
			return false;
		final Object object = list.get(0);
		if (object instanceof LibElement)
		{
			final IExtJSLibrary lib = ((LibElement)object).getLibrary();
			return lib == null || !lib.isBuiltin();
		}
		return false;
	}
	
	private boolean canAddSourceElement(List<Object> list) {
		if (list.size() != 1)
			return false;
		final Object object = list.get(0);
		if (object instanceof LibElement)
		{
			final IExtJSLibrary lib = ((LibElement)object).getLibrary();
			return lib == null || !lib.isBuiltin();
		}
		return false;
	}

	private boolean canEdit(List<Object> list) {
		if (list.size() != 1)
			return false;
		
		final Object obj = list.get(0);
		if (obj instanceof LibVersionElement)
			return false;
		
		return true;
	}

	private boolean canRemove(List<Object> list) { 
		if (list.size() == 0) {
			return false;
		}
		for (int i= 0; i < list.size(); i++) {
			final Object elem = list.get(i);
			if (elem instanceof LibElement) {
				final IExtJSLibrary lib = ((LibElement) elem).getLibrary();
				if (lib != null && lib.isBuiltin())
					return false;
			}
			if (elem instanceof LibVersionElement) {
				final IExtJSLibrary lib = ((LibVersionElement) elem).getLibrary();
				if (lib != null && lib.isBuiltin())
					return false;
			}
		}
		return true;
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
			if (element instanceof LibElement) {
				LibElement elem = (LibElement) element;
				return elem.getChildren();
			}
			else if (element instanceof LibSourceElement) {
				LibSourceElement elem = (LibSourceElement) element;
				return elem.getChildren();
			}
			return EMPTY;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			if (element instanceof LibSourceElement) {
				return ((LibSourceElement) element).getParent();
			} else if (element instanceof LibSourceInclusionsElement) {
				return ((LibSourceInclusionsElement) element).getParent();
			}  else if (element instanceof LibSourceExclusionsElement) {
				return ((LibSourceExclusionsElement) element).getParent();
			}  else if (element instanceof LibVersionElement) {
				return ((LibVersionElement) element).getParent();
			} 
			return null;
		}

		public boolean hasChildren(TreeListDialogField field, Object element) {
			return getChildren(field, element).length > 0;
		}
				
	}
	
}