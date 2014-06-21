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

import net.w3des.extjs.ui.discovery.ExtJSCatalogConfiguration.Type;

import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogPage;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author mepeisen
 */
@SuppressWarnings("restriction")
public class ExtJSCatalogPage extends CatalogPage {

	public ExtJSCatalogPage(Catalog catalog, Type type) {
		super(catalog);
		
		switch (type) {
		case ExtJSSdk:
			setTitle("Install ExtJS SDK");
			break;
		case SenchaTouchSdk:
			setTitle("Install Sencha Touch SDK");
			break;
		}
		
		setDescription("Select updates and extensions to install. Press Finish to proceed with installation.\nPress the information button to see a detailed overview and a link to more information.");
	}

	protected CatalogViewer doCreateViewer(Composite parent) {
		ExtJSCatalogViewer viewer = new ExtJSCatalogViewer(getCatalog(), this,
				getContainer(), getWizard().getConfiguration());
		viewer.setMinimumHeight(MINIMUM_HEIGHT);
		viewer.createControl(parent);
		return viewer;
	}

}
