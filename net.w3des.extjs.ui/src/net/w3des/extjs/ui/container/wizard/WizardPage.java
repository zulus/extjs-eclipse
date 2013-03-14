package net.w3des.extjs.ui.container.wizard;


import java.awt.List;
import java.util.ArrayList;

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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.internal.SharedImages;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;
import org.eclipse.wst.jsdt.core.IIncludePathAttribute;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.internal.core.ClasspathEntry;
import org.eclipse.wst.jsdt.internal.ui.JavaPluginImages;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.IListAdapter;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPage;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPageExtension;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPageExtension2;
import org.eclipse.wst.jsdt.ui.wizards.NewElementWizardPage;

@SuppressWarnings("restriction")
public class WizardPage extends NewElementWizardPage implements IJsGlobalScopeContainerPage, IJsGlobalScopeContainerPageExtension {
	final private String CONTEXT_ID = "net.w3des.extjs.container.wizard"; //$NON-NLS-1$
	private IIncludePathEntry selection;
	private StringDialogField nameField;
	private ListDialogField dirField;
	private String names = "Ext"; //$NON-NLS-1$
	private IJavaScriptProject project;
	
	final private SharedImages sharedImages;
	
	public WizardPage() {
		super(ExtJsCoreMessages.name);
		sharedImages = new SharedImages();
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
		
		nameField = new StringDialogField();
		nameField.setLabelText(ExtJSUIMessages.wizardGlobalName);
		nameField.setText(names);
		nameField.setDialogFieldListener(new IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(DialogField field) {
				WizardPage.this.names = ((StringDialogField) field).getText();
			}
		});
		
		dirField = new ListDialogField(new IListAdapter() {
			
			@Override
			public void selectionChanged(ListDialogField field) {
			}
			
			@Override
			public void doubleClicked(ListDialogField field) {
			}
			
			@Override
			public void customButtonPressed(ListDialogField field, int index) {
				if (index != 0) {
					return;
				}
				
				dirDialog();
			}
		}, new String[] { ExtJSUIMessages.wizardAddDir, ExtJSUIMessages.wizardRemoveDir }, new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return sharedImages.getImage(SharedImages.IMG_OBJ_FOLDER);
			}
			
			@Override
			public String getText(Object element) {
				IPath entry = (IPath) element;
				
				return entry.toPortableString();
			}
		});
		
		dirField.setRemoveButtonIndex(1);
		dirField.setLabelText(ExtJSUIMessages.wizardSrcDir);
		if (selection != null) {
			
		}
		
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { nameField, dirField }, false, SWT.DEFAULT, SWT.DEFAULT);
		
		this.setControl(composite);
		
	}

	protected void dirDialog() {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(this.getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setTitle("Select src dir");
		dialog.setMessage("ExtJS libraries");
		dialog.setInput(project.getProject());
		dialog.addFilter(new ViewerFilter() {
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
				} catch (CoreException e) {
					ExtJSUI.error(e);
					return false;
				}
				
				return true;
			}
		});
		dialog.setAllowMultiple(true);
		dialog.setImage(ExtJSUI.getImageDescriptor(ExtJSUI.IMG_ICO).createImage());
		dialog.setDoubleClickSelects(true);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		int buttonId = dialog.open();
		if (buttonId == IDialogConstants.OK_ID) {
			for (Object o : dialog.getResult()) {
				if (o == null) {
					continue;
				}
				addDirSelection((IFolder) o);
			}
		}
	}

	/**
	 * Iterate dir dialog result
	 * 
	 * @param item
	 * @see dirDialog()
	 */
	private void addDirSelection(IFolder item) {
		dirField.addElement(item.getProjectRelativePath());
	}

	
	@Override
	public boolean finish() {
		if (names == null || names.length() == 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public IIncludePathEntry getSelection() {
		IIncludePathAttribute[] attributes = new IIncludePathAttribute[] {
				JavaScriptCore.newIncludepathAttribute("names", names),
				JavaScriptCore.newIncludepathAttribute("hide", "true")
		};
		selection = JavaScriptCore.newContainerEntry(new Path(Container.ID), ClasspathEntry.NO_ACCESS_RULES, attributes, false);
		ClasspathEntry ent = (ClasspathEntry) selection;
		
		
		return selection;
	}

	@Override
	public void setSelection(IIncludePathEntry containerEntry) {
		this.selection = containerEntry;
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(CONTEXT_ID);
	}

	@Override
	public void initialize(IJavaScriptProject project, IIncludePathEntry[] currentEntries) {
		this.project = project;
	}
}
