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
package net.w3des.extjs.ui.discovery;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogPage;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.DiscoveryWizard;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * @author mepeisen
 */
@SuppressWarnings("restriction")
public class ExtJSDiscoveryWizard extends DiscoveryWizard {

	public ExtJSDiscoveryWizard(Catalog catalog,
			ExtJSCatalogConfiguration configuration) {
		super(catalog, configuration);
		
		switch (configuration.getType()) {
		case ExtJSSdk:
			setWindowTitle("Discover ExtJS SDK");
			break;
		case SenchaTouchSdk:
			setWindowTitle("Discover Sencha Touch SDK");
			break;
		}
		
		// setDefaultPageImageDescriptor(MavenDiscoveryIcons.WIZARD_BANNER);
	}

	@Override
	protected CatalogPage doCreateCatalogPage() {
		return new ExtJSCatalogPage(getCatalog(), ((ExtJSCatalogConfiguration)getConfiguration()).getType());
	}

	@Override
	public boolean performFinish() {
		try {
			return ExtJSDiscovery.install(getCatalogPage()
					.getInstallableConnectors(), null, getContainer());
		} catch (CoreException e) {
			StatusManager.getManager().handle(
					e.getStatus(),
					StatusManager.SHOW | StatusManager.BLOCK
							| StatusManager.LOG);
			return false;
		}
	}

}
