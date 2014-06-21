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

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.w3des.extjs.internal.core.ExtJSCore;
import net.w3des.extjs.ui.discovery.ExtJSCatalogConfiguration.Type;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.discovery.DiscoveryCore;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogItem;
import org.eclipse.equinox.internal.p2.discovery.model.Tag;
import org.eclipse.equinox.internal.p2.ui.IProvHelpContextIds;
import org.eclipse.equinox.internal.p2.ui.ProvUI;
import org.eclipse.equinox.internal.p2.ui.dialogs.ProvisioningWizardDialog;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.IVersionedId;
import org.eclipse.equinox.p2.metadata.VersionedId;
import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.IRunnableWithProgress;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

@SuppressWarnings("restriction")
public class ExtJSDiscovery {
	
	// see http://wiki.eclipse.org/Equinox/p2/Discovery

	// TODO set to a meaningful catalog url
	private static final String DEFAULT_URL = "http://zulus.github.io/extjs-eclipse/discovery/directory-0.1.xml";

	static final Tag EXTJS_SDK_TAG = new Tag("extjs-sdk", "ExtJS SDK"); //$NON-NLS-1$

	static final Tag SENCHA_TOUCH_SDK_TAG = new Tag("sencha-touch-sdk", "Sencha Touch SDK"); //$NON-NLS-1$

	static final Tag SUITABLE_TAG = new Tag("suitable", "Suitable"); //$NON-NLS-1$

	/**
	 * 
	 */
	public static void runExtJSDiscoveryDialog(String version) {
		// create the catalog
		final Catalog catalog = createCatalog();

		final ExtJSCatalogConfiguration catalogConfiguration = createConfig(version);
		
		catalogConfiguration.setType(Type.ExtJSSdk);
		ExtJSDiscoveryWizard wizard = new ExtJSDiscoveryWizard(catalog, catalogConfiguration);
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.open();
	}

	/**
	 * 
	 */
	public static void runSenchaTouchDiscoveryDialog(String version) {
		final Catalog catalog = createCatalog();

		final ExtJSCatalogConfiguration catalogConfiguration = createConfig(version);
		
		catalogConfiguration.setType(Type.SenchaTouchSdk);
		ExtJSDiscoveryWizard wizard = new ExtJSDiscoveryWizard(catalog, catalogConfiguration);
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.open();
	}

	private static ExtJSCatalogConfiguration createConfig(String version) {
		final ExtJSCatalogConfiguration catalogConfiguration = new ExtJSCatalogConfiguration();
		catalogConfiguration.setVersion(version);
		catalogConfiguration.setSelectedTags(Collections.singleton(SUITABLE_TAG));
		return catalogConfiguration;
	}

	private static Catalog createCatalog() {
		final Catalog catalog = new Catalog();
		catalog.setEnvironment(DiscoveryCore.createEnvironment());
		catalog.setVerifyUpdateSiteAvailability(false);

		final ExtJSDiscoveryStratery remoteStrategy = new ExtJSDiscoveryStratery();
		remoteStrategy.setDirectoryUrl(System.getProperty("extjs.discovery.url", DEFAULT_URL));
		catalog.getDiscoveryStrategies().add(remoteStrategy);

		final List<Tag> tags = new ArrayList<Tag>(3);
		tags.add(EXTJS_SDK_TAG);
		tags.add(SENCHA_TOUCH_SDK_TAG);
		tags.add(SUITABLE_TAG);
		catalog.setTags(tags);
		
		return catalog;
	}

	/*
	 * Add the necessary repositories
	 */
	private static List<IMetadataRepository> addRepositories(Collection<CatalogItem> installablePlugins) throws CoreException {
		// TODO this isn't right
		// tell p2 that it's okay to use these repositories
		RepositoryTracker repositoryTracker = ProvisioningUI.getDefaultUI().getRepositoryTracker();
		Set<URI> repositoryLocations = new HashSet<URI>();
		for (CatalogItem descriptor : installablePlugins) {
			URI uri = URI.create(descriptor.getSiteUrl());
			if (repositoryLocations.add(uri)) {
				repositoryTracker.addRepository(uri, null, ProvisioningUI.getDefaultUI().getSession());
			}
		}

		// fetch meta-data for these repositories
		ArrayList<IMetadataRepository> repositories = new ArrayList<IMetadataRepository>();
		IMetadataRepositoryManager manager = (IMetadataRepositoryManager) ProvisioningUI.getDefaultUI().getSession().getProvisioningAgent().getService(IMetadataRepositoryManager.SERVICE_NAME);
		for (URI uri : repositoryLocations) {
			try {
				IMetadataRepository repository = manager.loadRepository(uri, new NullProgressMonitor());
				repositories.add(repository);
			} catch (ProvisionException e) {
				new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "installation error", e));
			}
		}
		return repositories;
	}

	/*
	 * Get the IVersionedId expected to be in the repository
	 */
	private static Set<IVersionedId> getDescriptorIds(Collection<CatalogItem> installablePlugins, IMetadataRepository repository) {
		Set<IVersionedId> ids = new HashSet<IVersionedId>();
		for (CatalogItem item : installablePlugins) {
			if (repository.getLocation().equals(URI.create(item.getSiteUrl()))) {
				for (String id : item.getInstallableUnits()) {
					ids.add(VersionedId.parse(id));
				}
			}
		}
		return ids;
	}

	/*
	 * Get IUs to install from the specified repository
	 */
	private static Collection<IInstallableUnit> queryInstallableUnits(Collection<CatalogItem> installablePlugins, List<IMetadataRepository> repositories) throws CoreException {
		final Set<IInstallableUnit> installableUnits = new HashSet<IInstallableUnit>(installablePlugins.size());

		for (CatalogItem item : installablePlugins) {
			URI address = URI.create(item.getSiteUrl());
			// get repository
			IMetadataRepository repository = null;
			for (IMetadataRepository candidate : repositories) {
				if (address.equals(candidate.getLocation())) {
					repository = candidate;
					break;
				}
			}
			if (repository == null) {
				new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "installation error"));
			}

			Set<IVersionedId> ids = getDescriptorIds(installablePlugins, repository);
			for (IVersionedId versionedId : ids) {
				IQueryResult<IInstallableUnit> result = repository.query(QueryUtil.createIUQuery(versionedId), new NullProgressMonitor());
				Set<IInstallableUnit> matches = result.toSet();
				if (matches.size() == 1) {
					installableUnits.addAll(matches);
				} else if (matches.size() == 0) {
					new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "installation error"));
				} else {
					// Choose the highest available version
					IInstallableUnit match = null;
					for (IInstallableUnit iu : matches) {
						if (match == null || iu.getVersion().compareTo(match.getVersion()) > 0) {
							match = iu;
						}
					}
					if (match != null) {
						installableUnits.add(match);
					}
				}
			}
		}
		return installableUnits;
	}

	private static IInstallableUnit[] computeInstallableUnits(Collection<CatalogItem> installablePlugins) throws CoreException {
		List<IMetadataRepository> repositories = addRepositories(installablePlugins);
		final Collection<IInstallableUnit> installableUnits = queryInstallableUnits(installablePlugins, repositories);
		return installableUnits.toArray(new IInstallableUnit[installableUnits.size()]);
	}

	/**
	 * Installs specified
	 * 
	 * @param descriptors
	 *            is the list of catalog items to install
	 * @param postInstallHook
	 *            additional operation to perform after installation has
	 *            completed and before restart, can be null
	 * @param context
	 * @return
	 */
	public static boolean install(List<CatalogItem> descriptors, IRunnableWithProgress postInstallHook, IRunnableContext context) throws CoreException {
		final IInstallableUnit[] ius = computeInstallableUnits(descriptors);
		final RestartInstallOperation operation = new RestartInstallOperation(
				ProvisioningUI.getDefaultUI().getSession(),
				Arrays.asList(ius),
				postInstallHook);
		openInstallWizard(operation, true, Arrays.asList(ius));
		return true;
	}

	private static int openInstallWizard(RestartInstallOperation operation, boolean blockOnOpen, List<IInstallableUnit> list) {
		ExtJSDiscoveryInstallWizard wizard = new ExtJSDiscoveryInstallWizard(ProvisioningUI.getDefaultUI(), operation, list, null);
		WizardDialog dialog = new ProvisioningWizardDialog(ProvUI.getDefaultParentShell(), wizard);
		dialog.create();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IProvHelpContextIds.INSTALL_WIZARD);
		dialog.setBlockOnOpen(blockOnOpen);
		return dialog.open();
	}

}
