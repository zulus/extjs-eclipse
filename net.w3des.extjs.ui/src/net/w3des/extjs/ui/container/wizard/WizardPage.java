package net.w3des.extjs.ui.container.wizard;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.ExtJsCoreMessages;
import net.w3des.extjs.core.container.Container;
import net.w3des.extjs.ui.ExtJSUI;
import net.w3des.extjs.ui.ExtJSUIMessages;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;
import org.eclipse.wst.jsdt.core.IAccessRule;
import org.eclipse.wst.jsdt.core.IIncludePathAttribute;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.internal.core.ClasspathEntry;
import org.eclipse.wst.jsdt.internal.ui.JavaPluginImages;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.IListAdapter;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPage;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPageExtension;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPageExtension2;
import org.eclipse.wst.jsdt.ui.wizards.NewElementWizardPage;

/**
 * This class allow to create ExtJS libraru container entiries
 * 
 * Currently only will add empty position
 * 
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
@SuppressWarnings("restriction")
public class WizardPage extends NewElementWizardPage implements IJsGlobalScopeContainerPage, IJsGlobalScopeContainerPageExtension/*, IJsGlobalScopeContainerPageExtension2*/ {
	final static private String HELP_CONTEXT_ID = "net.w3des.extjs.container.wizard"; //$NON-NLS-1$
	
	/**
	 * @see selection
	 */
	private boolean isEdit;
	
	/**
	 * Exists only if wizard is in edit stage
	 * 
	 * @see WizardPage.getSelection()
	 */
	private IIncludePathEntry selection;
	
	/**
	 * currently disabled
	 */
	private StringDialogField nameField;
	
	/**
	 * Manage ExtJS libraries dir
	 */
	private ListDialogField dirField;
	
	/**
	 * Edit classpathentry
	 */
	private StringButtonDialogField editDirField;
	
	/**
	 * Current edited dir
	 */
	private IFolder currentDir;
	
	/**
	 * Default name
	 * 
	 * @see nameField
	 */
	private String names = "Ext"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	private IJavaScriptProject project;
	
	/**
	 * Current container list (used for filters)
	 */
	private IPath[] currentDirs;

	
	public WizardPage() {
		super(ExtJsCoreMessages.name);
	    this.setTitle(ExtJSUIMessages.wizardPageTitle);
	    this.setImageDescriptor(JavaPluginImages.DESC_WIZBAN_ADD_LIBRARY);
	}
	
	/**
	 * TODO do it on native SWT (no internal elements)
	 */
	@Override
	public void createControl(Composite parent) {
		this.setDescription(ExtJSUIMessages.wizardPageDescription);
		this.setImageDescriptor(ExtJSUI.getImageDescriptor(ExtJSUI.WIZ_IMAGE));
		
		Composite composite = new Composite(parent, SWT.None);
		composite.setFont(parent.getFont());
		createNamesField();
		
		if (isEdit) {
			dirEditionWidget();
			LayoutUtil.doDefaultLayout(composite, new DialogField[] { nameField, editDirField }, false, SWT.DEFAULT, SWT.DEFAULT);
		} else {
			dirSelectionWidget();
			LayoutUtil.doDefaultLayout(composite, new DialogField[] { nameField, dirField }, false, SWT.DEFAULT, SWT.DEFAULT);
		}
		
		this.setControl(composite);
		
	}
	
	/**
	 * Text + brows button
	 */
	private void dirEditionWidget() {
		editDirField = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {
				ElementTreeSelectionDialog dialog =  dirDialog(false);
				int buttonId = dialog.open();
				if (buttonId == IDialogConstants.OK_ID) {
					IFolder folder = (IFolder) dialog.getFirstResult();
					
					editDirField.setText(folder.getProjectRelativePath().toString());
					currentDir = folder;
				};
			}
		});
		editDirField.setTextFieldEditable(false);
		editDirField.setLabelText(ExtJSUIMessages.wizardSrcDir);
		editDirField.setButtonLabel(ExtJSUIMessages.wizardBrowseDir);
		editDirField.setText(currentDirs[0].toString());
		editDirField.setText(Container.getRelativePathes(new IIncludePathEntry[] { selection })[0].toString());
		
		for (int i = 0; i < currentDirs.length; i++) {
			if (currentDirs[i].equals(Container.getRelativePath(selection))) {
				currentDirs[i] = new Path(".empty"); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * Dir list with remove/add buttons
	 */
	private void dirSelectionWidget() {
		dirField = new ListDialogField(new DirFieldAdapter(), new String[] { ExtJSUIMessages.wizardAddDir, ExtJSUIMessages.wizardRemoveDir }, new DirFieldLabelProvider());
		
		dirField.setRemoveButtonIndex(1);
		dirField.setLabelText(ExtJSUIMessages.wizardSrcDir);
		dirField.setEnabled(false);
	}

	/**
	 * Create Dir selection dialog and return before open
	 * 
	 * @param multi
	 * @return
	 */
	private ElementTreeSelectionDialog dirDialog(boolean multi) {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(this.getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setTitle("Select src dir");
		dialog.setMessage("ExtJS libraries");
		dialog.setInput(project.getProject());
		dialog.addFilter(new DirFieldViewerFilter());
		dialog.setImage(ExtJSUI.getImageDescriptor(ExtJSUI.IMG_ICO).createImage());
		dialog.setDoubleClickSelects(true);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		dialog.setAllowMultiple(multi);
		
		return dialog;
	}
	
	/**
	 * Create global name dialog
	 */
	private void createNamesField() {
		nameField = new StringDialogField();
		nameField.setLabelText(ExtJSUIMessages.wizardGlobalName);
		nameField.setText(names);
		nameField.setEnabled(false);
		nameField.setDialogFieldListener(new IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(DialogField field) {
				WizardPage.this.names = ((StringDialogField) field).getText();
			}
		});
	}

	/**
	 * Iterate dir dialog result
	 * 
	 * @param item
	 * @see dirDialog()
	 */
	private void addDirSelection(IFolder item) {
		dirField.addElement(item);
	}

	
	@Override
	public boolean finish() {	
		return true;
	}

	@Override
	public IIncludePathEntry getSelection() {
		/*if (currentDir == null) {
			return selection;
		}*/
		IIncludePathAttribute[] attributes = new IIncludePathAttribute[] {
				JavaScriptCore.newIncludepathAttribute("names", names)
		};
		selection = JavaScriptCore.newContainerEntry(new Path(Container.ID), ClasspathEntry.NO_ACCESS_RULES, attributes, false);
		
		return selection;
	}

	@Override
	public void setSelection(IIncludePathEntry containerEntry) {
		this.selection = containerEntry;
		
		if (containerEntry != null) {
			isEdit = true;
		} else {
			isEdit = false;
		}
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(HELP_CONTEXT_ID);
	}

	@Override
	public void initialize(IJavaScriptProject project, IIncludePathEntry[] currentEntries) {
		this.project = project;
		isEdit = false;
		
		currentDirs = Container.getRelativePathes(currentEntries);
	}

	//@Override
	public IIncludePathEntry[] getNewContainers() {
		IIncludePathEntry[] ret = new IIncludePathEntry[dirField.getElements().size()];
		IIncludePathAttribute[] attributes = new IIncludePathAttribute[] {
				JavaScriptCore.newIncludepathAttribute("names", names)
		};
		for (int i = 0; i < dirField.getElements().size(); i++) {
			IFolder item = (IFolder) dirField.getElement(i);
			
			ret[i] = JavaScriptCore.newContainerEntry(Container.createPath(item), new IAccessRule[0], attributes, false);
		}
		
		return ret;
	}
	
	/**
	 * "Add" button
	 * 
	 * @author Dawid zulus Pakula <zulus@w3des.net>
	 */
	final private class DirFieldAdapter implements IListAdapter {
		
		@Override
		public void selectionChanged(ListDialogField field) {}
		
		@Override
		public void doubleClicked(ListDialogField field) {}
		
		@Override
		public void customButtonPressed(ListDialogField field, int index) {
			if (index != 0) {
				return;
			}
			ElementTreeSelectionDialog dialog = dirDialog(true);
			int buttonId = dialog.open();
			if (buttonId == IDialogConstants.OK_ID) {
				for (Object o : dialog.getResult()) {
					if (o == null) {
						continue;
					}
					addDirSelection((IFolder) o);
				}
			};
		}
	}
	
	/**
	 * All positions have the same folder image
	 * 
	 * @author Dawid zulus Pakula <zulus@w3des.net>
	 */
	final private class DirFieldLabelProvider extends LabelProvider {
		@Override
		public Image getImage(Object element) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		
		@Override
		public String getText(Object element) {
			IFolder entry = (IFolder) element;
			
			return entry.getProjectRelativePath().toString();
		}
	}
	
	/**
	 * Allow only  dirs from current project
	 * 
	 * @author Dawid zulus Pakula <zulus@w3des.net>
	 */
	final private class DirFieldViewerFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (!(element instanceof IFolder)) {
				return false;
			}
			
			IFolder dir = (IFolder) element;
			
			try {
				if (!dir.isAccessible() || dir.isHidden() || dir.isPhantom() || dir.members().length == 0 || dir.getName().startsWith(".")) {
					return false;
				}
				
				for (IPath path : currentDirs) {
					if (path.equals(dir.getProjectRelativePath())) {
						return false;
					}
				}
			} catch (CoreException e) {
				ExtJSUI.error(e);
				return false;
			}
			
			return true;
		}
	}
}
