package net.w3des.extjs.ui.container.attribute;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.jsdt.core.IIncludePathAttribute;
import org.eclipse.wst.jsdt.ui.wizards.ClasspathAttributeConfiguration;

/**
 * Currently this not work! Due JSDT bug
 *
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class NamesAttributeConfiguration extends ClasspathAttributeConfiguration {
	@Override
	public ImageDescriptor getImageDescriptor(ClasspathAttributeAccess attribute) {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT);
	}

	@Override
	public String getNameLabel(ClasspathAttributeAccess attribute) {
		return "Global names";
	}

	@Override
	public String getValueLabel(ClasspathAttributeAccess attribute) {
		return attribute.getClasspathAttribute().getValue();
	}

	@Override
	public boolean canEdit(ClasspathAttributeAccess attribute) {
		return true;
	}

	@Override
	public boolean canRemove(ClasspathAttributeAccess attribute) {
		return true;
	}

	@Override
	public IIncludePathAttribute performEdit(Shell shell, ClasspathAttributeAccess attribute) {
		return null;
	}

	@Override
	public IIncludePathAttribute performRemove(ClasspathAttributeAccess attribute) {
		return null;
	}

}
