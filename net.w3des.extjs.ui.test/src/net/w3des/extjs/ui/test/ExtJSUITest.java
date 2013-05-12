package net.w3des.extjs.ui.test;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class ExtJSUITest extends Plugin {
	public static final String PLUGIN_ID = "net.w3des.extjs.ui.test";
	
	private static ExtJSUITest plugin;
	
	boolean started;
	
	/**
	 * The constructor
	 */
	public ExtJSUITest() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin  = this;
		started = true;
	}

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
	public static ExtJSUITest getDefault() {
		return plugin;
	}
}
