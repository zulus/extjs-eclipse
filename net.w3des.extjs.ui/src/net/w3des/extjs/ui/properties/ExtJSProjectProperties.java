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
package net.w3des.extjs.ui.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.w3des.extjs.core.Utils;
import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSEnvironment;
import net.w3des.extjs.core.api.IExtJSLibrary;
import net.w3des.extjs.core.api.IExtJSProject;
import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.common.DialogField;
import net.w3des.extjs.ui.common.IDialogFieldListener;
import net.w3des.extjs.ui.common.IStringButtonAdapter;
import net.w3des.extjs.ui.common.ITreeListAdapter;
import net.w3des.extjs.ui.common.LayoutUtil;
import net.w3des.extjs.ui.common.StringButtonDialogField;
import net.w3des.extjs.ui.common.StringDialogField;
import net.w3des.extjs.ui.common.TreeListDialogField;
import net.w3des.extjs.ui.preferences.ChooseEnvDialog;
import net.w3des.extjs.ui.preferences.ChooseLibraryDialog;
import net.w3des.extjs.ui.preferences.LibElement;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * Properties page for extjs projects
 * 
 * @author mepeisen
 */
public class ExtJSProjectProperties extends PropertyPage implements IWorkbenchPropertyPage, IDialogFieldListener {

	private TreeListDialogField fList;

	private StringDialogField fNamespace;

	private StringButtonDialogField fSourceFolder;
	
	private static final int IDX_NEW_LIBRARY = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;
	
	private List<IExtJSLibrary> removedLibs = new ArrayList<IExtJSLibrary>();
	
	private List<IExtJSLibrary> newLibs = new ArrayList<IExtJSLibrary>();
	
	private String newEnv = null;
	
	/**
	 * 
	 */
	public ExtJSProjectProperties() {
		setDescription("ExtJS build environments");
		noDefaultAndApplyButton();
		
		ListAdapter adapter= new ListAdapter();
		String[] buttonLabels= new String[] {
				"new library", 
				"edit", 
				"remove"
		};
		
		this.fList = new TreeListDialogField(adapter, buttonLabels, new ListLabelProvider());
		this.fList.setLabelText("Environment/Libraries");
		
		this.fList.setViewerComparator(new ListElementSorter());
		doSelectionChanged(this.fList); //update button enable state
		
		this.fNamespace = new StringDialogField();
		this.fNamespace.setLabelText("Application namespace");
		this.fNamespace.setText("");
		this.fNamespace.setDialogFieldListener(this);
		
		this.fSourceFolder = new StringButtonDialogField(new SourceFolderAdapter());
		this.fSourceFolder.setLabelText("Application class folder");
		this.fSourceFolder.setText("");
		this.fSourceFolder.setButtonLabel("Browse");
		this.fSourceFolder.setDialogFieldListener(this);
	}
	
	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);
		this.removedLibs.clear();
		this.newLibs.clear();
		this.newEnv = null;
		
		try {
			final IExtJSProject project = getExtJSProject();
			
			final List<IListElement> list = new ArrayList<IListElement>();
			list.add(new EnvListElement(ExtJSCore.getLibraryManager().getEnv(project.getEnvironmentName())));
			for (final IExtJSLibrary lib : project.getLibraries()) {
				if (! (lib instanceof IExtJSCoreLibrary)) {
					list.add(new LibListElement(lib));
				}
			}
			this.fList.setElements(list);
			
			doSelectionChanged(this.fList); //update button enable state
			
			this.fNamespace.setText(project.getExtjsNamespace() == null ? "" : project.getExtjsNamespace());
			
			if (project.getSourceFolder() == null) {
				this.fSourceFolder.setText("");
			}
			else {
				this.fSourceFolder.setText(project.getSourceFolder().getFullPath().toString());
			}
		}
		catch (CoreException ex) {
			ExtJSCore.error(ex);
		}
	}

	private IExtJSProject getExtJSProject() {
		final IProject project = getProject();
		return ExtJSCore.getProjectManager().createProject(project);
	}

	private IProject getProject() {
		return (IProject) getElement();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite= new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { this.fList, this.fNamespace, this.fSourceFolder }, true);
		LayoutUtil.setHorizontalGrabbing(this.fList.getTreeControl(null));
		Dialog.applyDialogFont(composite);
		return composite;
	}
	
	protected void doSelectionChanged(TreeListDialogField field) {
		List<Object> list = field.getSelectedElements();
		field.enableButton(IDX_EDIT, canEdit(list));
		field.enableButton(IDX_NEW_LIBRARY, true);
		field.enableButton(IDX_REMOVE, canRemove(list));
	}
	
	private boolean canEdit(List<Object> list) {
		if (list.size() != 1)
			return false;
		return list.get(0) instanceof EnvListElement;
	}
	
	private boolean canRemove(List<Object> list) {
		if (list.size() != 1)
			return false;
		return list.get(0) instanceof LibListElement;
	}
	
	protected void doDoubleClicked(TreeListDialogField field) {
		List<Object> selected = field.getSelectedElements();
		if (canEdit(selected)) {
			doEdit(field.getSelectedElements());
		}
	}
	
	protected void doCustomButtonPressed(TreeListDialogField field, int index) {
		if (index == IDX_EDIT) {
			doEdit(field.getSelectedElements());
		} else if (index == IDX_NEW_LIBRARY) {
			doNewLib();
		} else if (index == IDX_REMOVE) {
			doRemove(field.getSelectedElements());
		}
	}
	
	protected void doEdit(List<Object> list) {
		if (list.size() == 1 && list.get(0) instanceof EnvListElement) {
			final EnvListElement elm = (EnvListElement) list.get(0);

			final IExtJSProject project = getExtJSProject();
			final List<IExtJSEnvironment> envs = new ArrayList<IExtJSEnvironment>();
			for (final IExtJSEnvironment env : ExtJSCore.getLibraryManager().getEnvironments()) {
				try {
					if ((project.getVersion() != null && env.isCompatible(project.getVersion())) || (project.getTouchVersion() != null && env.isCompatible(project.getTouchVersion()))) {
						envs.add(env);
					}
				}
				catch (CoreException ex) {
					ExtJSCore.error(ex);
				}
			}
			
			final ChooseEnvDialog dialog = new ChooseEnvDialog(getShell(), envs);
			if (dialog.open() == Window.OK) {
				final String newEnvName = dialog.getSelectedEnvironments()[0];
				if (!elm.getName().equals(newEnvName)) {
					newEnv = newEnvName;
					elm.setEnv(ExtJSCore.getLibraryManager().getEnv(newEnvName));
					this.fList.refresh(elm);
				}
			}
		}
	}
	
	protected void doNewLib() {
		final IExtJSProject project = getExtJSProject();
		final List<IExtJSLibrary> libs = new ArrayList<IExtJSLibrary>();
		for (final IExtJSLibrary lib : ExtJSCore.getLibraryManager().getLibraries()) {
			try {
				if ((project.getVersion() != null && lib.isCompatible(project.getVersion())) || (project.getTouchVersion() != null && lib.isCompatible(project.getTouchVersion()))) {
					libs.add(lib);
				}
			}
			catch (CoreException ex) {
				ExtJSCore.error(ex);
			}
		}
		for (IExtJSLibrary lib : project.getLibraries()) {
			if (!this.removedLibs.contains(lib)) {
				libs.remove(lib);
			}
		}
		libs.removeAll(this.newLibs);
		
		final ChooseLibraryDialog dialog = new ChooseLibraryDialog(getShell(), libs);
		if (dialog.open() == Window.OK) {
			for (final String libName : dialog.getSelectedLibraries()) {
				final IExtJSLibrary lib = ExtJSCore.getLibraryManager().getLibrary(libName);
				if (this.removedLibs.contains(lib)) {
					this.removedLibs.remove(lib);
				}
				else {
					this.newLibs.add(lib);
				}
				final LibListElement elm = new LibListElement(lib);
				this.fList.addElement(elm);
			}
		}
	}
	
	protected void doKeyPressed(TreeListDialogField field, KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			if (canRemove(field.getSelectedElements())) {
				doRemove(field.getSelectedElements());
			}
		}
	}

	protected void browseFolderPressed(DialogField field) {
		final IExtJSProject project = getExtJSProject();
		final IContainer current = (this.fSourceFolder.getText() == null || this.fSourceFolder.getText().length() == 0) ? project.getProject() : project.getProject().getFolder(this.fSourceFolder.getText());
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(),
				project.getProject(),
				false,
				"Choose the source folder for your extjs classes");
		dialog.setInitialSelections(new Object[]{current});
		dialog.open();
		Object[] result = dialog.getResult();
        if (result != null && result.length == 1) {
            this.fSourceFolder.setText(((IPath) result[0]).toString());
        }
	}
	
	private void doRemove(List<Object> selected) {
		if (selected.size() == 1) {
			Object curr = selected.get(0);
			if (curr instanceof LibElement) {
				final IExtJSLibrary lib = ((LibElement) curr).getLibrary();
				if (!this.newLibs.remove(lib)) {
					this.removedLibs.add(lib);
				}
			}
			doSelectionChanged(this.fList);
		}
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
						
						update(monitor);
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
	
	private void update(IProgressMonitor monitor) throws CoreException {
		final IExtJSProject project = getExtJSProject();
		final String folder = this.fSourceFolder.getText();
		if (folder != null && folder.length() > 0) {
			final IPath path = new Path(folder).removeFirstSegments(1);
			final IContainer f = path.toString().length() == 0 ? project.getProject() : project.getProject().getFolder(path);
			project.setSourceFolder(f);
		}
		final String ns = this.fNamespace.getText();
		if (ns != null && ns.length() > 0) {
			project.setExtjsNamespace(ns);
		}
		if (this.newEnv != null) {
			project.setEnvironmentName(this.newEnv);
		}
		for (final IExtJSLibrary lib : this.removedLibs) {
			project.removeLibrary(lib.getName());
		}
		for (final IExtJSLibrary lib : this.newLibs) {
			project.addLibrary(lib.getName());
		}
		project.refreshLibContainer();
	}

	
	@Override
	public boolean isValid() {
		final IExtJSProject project = getExtJSProject();
		final String folder = this.fSourceFolder.getText();
		if (folder != null && folder.length() > 0) {
			final IPath path = new Path(folder).removeFirstSegments(1);
			final IContainer f = path.toString().length() == 0 ? project.getProject() : project.getProject().getFolder(path);
			if (!f.exists()) {
				setErrorMessage("Invalid source folder selected");
				return false;
			}
		}
		else {
			setErrorMessage("Select a source folder to place your extjs classes");
			return false;
		}
		
		final String ns = this.fNamespace.getText();
		if (ns != null && ns.length() > 0) {
			if (!Utils.PATTERN_JAVASCRIPT_NAMESPACE.matcher(ns).matches()) {
				setErrorMessage("The namespace contains invalid characters");
				return false;
			}
		}
		else {
			setErrorMessage("Enter your javascript namespace");
			return false;
		}
		
		return true;
	}


	private class ListAdapter implements ITreeListAdapter {
		
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
			return EMPTY;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			return null;
		}

		public boolean hasChildren(TreeListDialogField field, Object element) {
			return getChildren(field, element).length > 0;
		}
				
	}
	
	private class SourceFolderAdapter implements IStringButtonAdapter {

		@Override
		public void changeControlPressed(DialogField field) {
			browseFolderPressed(field);
		}

	}

	@Override
	public void dialogFieldChanged(DialogField field) {
		this.setErrorMessage(null);
		setValid(isValid());
	}

}
