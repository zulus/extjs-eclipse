package net.w3des.extjs.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ExtJSUI extends AbstractUIPlugin {

    // The plug-in ID
    public static final String ID = "net.w3des.extjs.ui"; //$NON-NLS-1$

    // The shared instance
    private static ExtJSUI plugin;

    boolean started;

    /**
     * The constructor
     */
    public ExtJSUI() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        started = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        started = false;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static ExtJSUI getDefault() {
        return plugin;
    }

    public static void error(String message) {
        plugin.getLog().log(new Status(IStatus.ERROR, ID, IStatus.OK, message, null));
    }

    public static void error(Throwable e) {
        plugin.getLog().log(new Status(IStatus.ERROR, ID, IStatus.OK, e.getLocalizedMessage(), e));
    }

    public static void error(String message, Throwable e) {
        plugin.getLog().log(new Status(IStatus.ERROR, ID, IStatus.OK, message, e));
    }

    public static void warn(String message) {
        warn(message, null);
    }

    public static void warn(String message, Throwable e) {
        plugin.getLog().log(new Status(IStatus.WARNING, ID, IStatus.OK, message, e));
    }

    public static void warn(Throwable e) {
        plugin.getLog().log(new Status(IStatus.WARNING, ID, IStatus.OK, e.getLocalizedMessage(), e));
    }

    public static void info(String message) {
        info(message, null);
    }

    public static void info(String message, Throwable e) {
        plugin.getLog().log(new Status(IStatus.INFO, ID, IStatus.OK, message, e));
    }

    public static void info(Throwable e) {
        plugin.getLog().log(new Status(IStatus.INFO, ID, IStatus.OK, e.getLocalizedMessage(), e));
    }

}
