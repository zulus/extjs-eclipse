package net.w3des.extjs.ui.menu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class AddExtJSSupportMenu extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		List<IContributionItem> list = new LinkedList<IContributionItem>();

		for (IProjectFacetVersion version : ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT).getVersions()) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("facet", version); //$NON-NLS-1$

			list.add(new CommandContributionItem(new CommandContributionItemParameter(PlatformUI.getWorkbench(),
					"net.w3des.extjs.ui.menu.addExtJSSupport." + version.getVersionString(), //$NON-NLS-1$
					"net.w3des.extjs.ui.command.addExtJSSupport", null, null, null, null, version.getVersionString(), //$NON-NLS-2$
					null, null, CommandContributionItem.STYLE_PUSH, null, true)));
		}

		return list.toArray(new IContributionItem[list.size()]);
	}

}
