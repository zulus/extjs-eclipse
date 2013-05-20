/**
 * 
 */
package net.w3des.extjs.core.infer;

import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.JsGlobalScopeVariableInitializer;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 * 
 */
public class VariableInitializer extends JsGlobalScopeVariableInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.jsdt.core.JsGlobalScopeVariableInitializer#initialize
	 * (java.lang.String)
	 */
	@Override
	public void initialize(String variable) {
		if (variable.equals("Ext") || variable.startsWith("Ext.")) { //$NON-NLS-1$
			try {
				JavaScriptCore.setIncludepathVariable("Ext",
						new Path(ExtJSCore.getDefault().getBundle().getEntry("resources/main.js").toString()),
						new NullProgressMonitor());
			} catch (JavaScriptModelException e) {
				ExtJSCore.error(e);
			}
		}
	}

}
