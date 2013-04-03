package net.w3des.extjs.ui.container.wizard;

import net.w3des.extjs.core.ExtJsCoreMessages;
import net.w3des.extjs.core.container.Container;
import net.w3des.extjs.ui.ExtJSUI;
import net.w3des.extjs.ui.ExtJSUIMessages;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.jsdt.core.IIncludePathAttribute;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.internal.core.ClasspathEntry;
import org.eclipse.wst.jsdt.internal.ui.JavaPluginImages;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPage;
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
public class WizardPage extends NewElementWizardPage implements IJsGlobalScopeContainerPage {
	final static private String HELP_CONTEXT_ID = "net.w3des.extjs.container.wizard"; //$NON-NLS-1$
	
	
	/**
	 * Exists only if wizard is in edit stage
	 * 
	 * @see WizardPage.getSelection()
	 */
	private IIncludePathEntry selection;
	
	/**
	 * currently disabled
	 */
	private DialogField nameField;
	
	/**
	 * Default name
	 * 
	 * @see nameField
	 */
	private String names = "Ext"; //$NON-NLS-1$
	
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
		
		LayoutUtil.doDefaultLayout(composite, new DialogField[] { nameField }, false, SWT.DEFAULT, SWT.DEFAULT);
		
		this.setControl(composite);
		
	}
	
	/**
	 * Create global name dialog
	 */
	private void createNamesField() {
		nameField = new DialogField();
		nameField.setLabelText(ExtJSUIMessages.wizardPageAbout);
	}

	
	@Override
	public boolean finish() {	
		return true;
	}

	@Override
	public IIncludePathEntry getSelection() {
		if (selection == null) {
			IIncludePathAttribute[] attributes = new IIncludePathAttribute[] {
					JavaScriptCore.newIncludepathAttribute("names", names)
			};
			selection = JavaScriptCore.newContainerEntry(new Path(Container.ID), ClasspathEntry.NO_ACCESS_RULES, attributes, false);
		}
		
		return selection;
	}

	@Override
	public void setSelection(IIncludePathEntry containerEntry) {
		this.selection = containerEntry;
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(HELP_CONTEXT_ID);
	}
}
