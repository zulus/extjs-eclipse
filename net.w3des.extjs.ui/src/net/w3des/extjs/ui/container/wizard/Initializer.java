package net.w3des.extjs.ui.container.wizard;

import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.internal.ui.IJsGlobalScopeContainerInitializerExtension;

@SuppressWarnings("restriction")
public class Initializer implements IJsGlobalScopeContainerInitializerExtension {

	@Override
	public ImageDescriptor getImage(IPath containerPath, String element, IJavaScriptProject project) {
		return ExtJSUI.getImageDescriptor(ExtJSUI.IMG_ICO);
	}

}
