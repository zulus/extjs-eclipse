package net.w3des.extjs.ui.discovery;

import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.discovery.AbstractCatalogSource;
import org.eclipse.equinox.internal.p2.discovery.compatibility.ConnectorDiscoveryExtensionReader;
import org.eclipse.equinox.internal.p2.discovery.compatibility.RemoteBundleDiscoveryStrategy;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogCategory;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogItem;
import org.eclipse.equinox.internal.p2.discovery.model.Certification;
import org.eclipse.equinox.internal.p2.discovery.model.ValidationException;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("restriction")
/**
 * @author taken from m2e
 */
public class ExtJSDiscoveryStratery extends RemoteBundleDiscoveryStrategy {

	protected void processExtensions(IProgressMonitor monitor,
			IExtension[] extensions) {
		monitor.beginTask("processing extensions",
				extensions.length == 0 ? 1 : extensions.length);
		try {
			final ExtJSConnectorDiscoveryExtensionReader extensionReader = new ExtJSConnectorDiscoveryExtensionReader();
			for (IExtension extension : extensions) {
				AbstractCatalogSource discoverySource = computeDiscoverySource(extension
						.getContributor());
				IConfigurationElement[] elements = extension
						.getConfigurationElements();
				for (IConfigurationElement element : elements) {
					if (monitor.isCanceled()) {
						return;
					}

					try {
						if (ConnectorDiscoveryExtensionReader.CONNECTOR_DESCRIPTOR
								.equals(element.getName())) {
							CatalogItem descriptor = extensionReader
									.readConnectorDescriptor(element,
											CatalogItem.class);
							descriptor.setSource(discoverySource);
							items.add(descriptor);
						} else if (ConnectorDiscoveryExtensionReader.CONNECTOR_CATEGORY
								.equals(element.getName())) {
							CatalogCategory category = extensionReader
									.readConnectorCategory(element,
											CatalogCategory.class);
							category.setSource(discoverySource);
							if (!discoverySource.getPolicy()
									.isPermitCategories()) {
								ExtJSCore
										.error(new CoreException(
												new Status(
														IStatus.ERROR,
														ExtJSCore.PLUGIN_ID,
														NLS.bind(
																"category {0}/{1}/{2} disallowed",
																new Object[] {
																		category.getName(),
																		category.getId(),
																		element.getContributor()
																				.getName() }),
														null)));
							} else {
								categories.add(category);
							}
						} else if (ConnectorDiscoveryExtensionReader.CERTIFICATION
								.equals(element.getName())) {
							Certification certification = extensionReader
									.readCertification(element,
											Certification.class);
							certification.setSource(discoverySource);
							certifications.add(certification);
						} else {
							throw new ValidationException(NLS.bind(
									"Unexpected element {0}",
									element.getName()));
						}
					} catch (ValidationException e) {
						ExtJSCore.error(new CoreException(new Status(
								IStatus.ERROR, ExtJSCore.PLUGIN_ID,
								NLS.bind("ValidationException {0}/{1}",
										element.getContributor().getName(),
										e.getMessage()), e)));
					}
				}
				monitor.worked(1);
			}
			tags.addAll(extensionReader.getTags());
		} finally {
			monitor.done();
		}
	}
}