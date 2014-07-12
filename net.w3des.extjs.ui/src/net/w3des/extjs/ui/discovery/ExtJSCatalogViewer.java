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

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogItem;
import org.eclipse.equinox.internal.p2.discovery.model.Tag;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogConfiguration;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogViewer;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.statushandlers.StatusManager;

@SuppressWarnings("restriction")
public class ExtJSCatalogViewer extends CatalogViewer {

	private Set<String> installedFeatures;

	private boolean noneApplicable;

	/*
	 * Outside of tests the shellProvider should generally be a WizardPage which
	 * allows setting the header.
	 */
	public ExtJSCatalogViewer(Catalog catalog, IShellProvider shellProvider,
			IRunnableContext context, CatalogConfiguration configuration) {
		super(catalog, shellProvider, context, configuration);
	}

	protected void postDiscovery(IProgressMonitor monitor) {
		final SubMonitor subMon = SubMonitor.convert(monitor, getCatalog()
				.getItems().size() * 3);
		try {
			for (CatalogItem connector : getCatalog().getItems()) {
				connector.setInstalled(installedFeatures != null
						&& installedFeatures.containsAll(connector
								.getInstallableUnits()));
				subMon.worked(1);
			}

			if (getCatalog().getItems().size() == installedFeatures.size()) {
				handleStatus(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID,
						"All available entries are installed."));
			} else {
				final ExtJSCatalogConfiguration config = (ExtJSCatalogConfiguration) getConfiguration();
				final String version = config.getVersion();
				String prefix = null;
				switch (config.getType()) {
				case ExtJSSdk:
					prefix = "extjs-sdk-";
					break;
				case SenchaTouchSdk:
					prefix = "sencha-touch-sdk-";
					break;
				}
				final Tag versionTag = new Tag(prefix + version, prefix + version);

				noneApplicable = true;

				shellProvider.getShell().getDisplay().syncExec(new Runnable() {
					@SuppressWarnings("synthetic-access")
					public void run() {
						for (CatalogItem ci : getCatalog().getItems()) {
							subMon.worked(2);

							if (version == null || ci.hasTag(versionTag)) {
								select(ci);
								continue;
							}
						}

						if (noneApplicable) {
							handleStatus(new Status(IStatus.ERROR,
									ExtJSCore.PLUGIN_ID,
									"No suitable plugins found"));
						}
					}
				});
			}
		} finally {
			subMon.done();
		}
	}

	@Override
	public void updateCatalog() {
		boolean wasCancelled = false;
		boolean wasError = false;
		final IStatus[] result = new IStatus[1];
		try {
			context.run(true, true, new IRunnableWithProgress() {
				@SuppressWarnings("synthetic-access")
				public void run(IProgressMonitor monitor)
						throws InterruptedException {
					SubMonitor submon = SubMonitor.convert(monitor, 100);
					try {
						if (installedFeatures == null) {
							installedFeatures = getInstalledFeatures(submon
									.newChild(10));
						}
						result[0] = getCatalog().performDiscovery(
								submon.newChild(80));
						if (monitor.isCanceled()) {
							throw new InterruptedException();
						}
						if (!getCatalog().getItems().isEmpty()) {
							postDiscovery(submon.newChild(10));
						}
					} finally {
						submon.done();
					}
				}
			});
		} catch (InvocationTargetException e) {
			result[0] = computeStatus(e, "unexpected exception");
		} catch (InterruptedException e) {
			// cancelled by user so nothing to do here.
			wasCancelled = true;
		}
		if (result[0] != null && !result[0].isOK()) {
			handleStatus(result[0]);
			wasError = true;
		}
		if (getCatalog() != null) {
			catalogUpdated(wasCancelled, wasError);
			verifyUpdateSiteAvailability();
		}
		// help UI tests
		viewer.setData("discoveryComplete", Boolean.TRUE); //$NON-NLS-1$
	}

	private void select(CatalogItem ci) {
		modifySelection(ci, true);
		ci.addTag(ExtJSDiscovery.SUITABLE_TAG);
		noneApplicable = false;
	}

	private void handleStatus(final IStatus status) {
		if (status.isOK()) {
			return;
		}

		if (shellProvider instanceof WizardPage) {
			shellProvider.getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					// Display the error in the wizard header
					int messageType = IMessageProvider.INFORMATION;
					if (status.matches(IStatus.ERROR)) {
						messageType = IMessageProvider.ERROR;
					} else if (status.matches(IStatus.WARNING)) {
						messageType = IMessageProvider.WARNING;
					}
					((WizardPage) shellProvider).setMessage(
							status.getMessage(), messageType);
					StatusManager.getManager().handle(status);
				}
			});
		} else {
			StatusManager.getManager().handle(
					status,
					StatusManager.SHOW | StatusManager.BLOCK
							| StatusManager.LOG);
		}
	}

}
