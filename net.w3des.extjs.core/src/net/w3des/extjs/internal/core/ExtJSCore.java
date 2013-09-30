package net.w3des.extjs.internal.core;

import net.w3des.extjs.core.IExtJSProjectManager;

import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class ExtJSCore extends Plugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "net.w3des.extjs.core"; //$NON-NLS-1$

    public static final String FACET_EXT = "wst.jsdt.extjs.core"; //$NON-NLS-1$
    public static final String FACET_CMD = "wst.jsdt.extjs.cmd"; //$NON-NLS-1$

    // The shared instance
    private static ExtJSCore plugin;

    boolean started;

    /**
     * The constructor
     */
    public ExtJSCore() {
        super();
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        started = true;
        final ISaveParticipant saveParticipant = new SaveParticipant();
        ResourcesPlugin.getWorkspace().addSaveParticipant(PLUGIN_ID, saveParticipant);
    }

    @Override
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
    public static ExtJSCore getDefault() {
        return plugin;
    }

    public boolean isStarted() {
        return started;
    }

    public static void error(String message) {
        plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message, null));
    }

    public static void error(Throwable e) {
        plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, e.getLocalizedMessage(), e));
    }

    public static void error(String message, Throwable e) {
        plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message, e));
    }

    public static void warn(String message) {
        warn(message, null);
    }

    public static void warn(String message, Throwable e) {
        plugin.getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, IStatus.OK, message, e));
    }

    public static void warn(Throwable e) {
        plugin.getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, IStatus.OK, e.getLocalizedMessage(), e));
    }

    public static void info(String message) {
        info(message, null);
    }

    public static void info(String message, Throwable e) {
        plugin.getLog().log(new Status(IStatus.INFO, PLUGIN_ID, IStatus.OK, message, e));
    }

    public static void info(Throwable e) {
        plugin.getLog().log(new Status(IStatus.INFO, PLUGIN_ID, IStatus.OK, e.getLocalizedMessage(), e));
    }

    public static IExtJSProjectManager getProjectManager() {
        final ServiceReference<?> serviceReference = plugin.getBundle().getBundleContext().getServiceReference(IExtJSProjectManager.class.getName());
        return (IExtJSProjectManager) plugin.getBundle().getBundleContext().getService(serviceReference);
    }
}
