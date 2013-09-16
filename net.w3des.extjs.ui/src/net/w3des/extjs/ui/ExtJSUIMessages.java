package net.w3des.extjs.ui;

import org.eclipse.osgi.util.NLS;

final public class ExtJSUIMessages extends NLS {
    private static String BUNDLE_NAME = "OSGI-INF/l10n/message"; //$NON-NLS-1$

    public static String wizardPageTitle;
    public static String wizardPageAdded;
    public static String wizardPageDescription;
    public static String wizardPageAbout;

    static {
        NLS.initializeMessages(BUNDLE_NAME, ExtJSUIMessages.class);
    }
}