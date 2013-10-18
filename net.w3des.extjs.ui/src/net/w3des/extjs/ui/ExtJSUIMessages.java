/*******************************************************************************
 * Copyright (c) 20013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
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
