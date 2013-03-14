package net.w3des.extjs.core;

import org.eclipse.osgi.util.NLS;

final public class ExtJsCoreMessages extends NLS {
	private static String BUNDLE_NAME = "OSGI-INF/l10n/message"; //$NON-NLS-1$
	
	public static String name;
	public static String libraryName;
	public static String libraryDescription;
	
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, ExtJsCoreMessages.class);
	}
}