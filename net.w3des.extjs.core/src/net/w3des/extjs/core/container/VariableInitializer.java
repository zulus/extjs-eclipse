package net.w3des.extjs.core.container;

import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.JsGlobalScopeVariableInitializer;

public class VariableInitializer extends JsGlobalScopeVariableInitializer {

	@Override
	public void initialize(String variable) {
		if (variable.equals("Ext")) { //$NON-NLS-1$
			try {
				JavaScriptCore.setIncludepathVariable(variable, new Path(Container.ID), null); //$NON-NLS-1$
			} catch (JavaScriptModelException e) {
				ExtJSCore.error("Error during global variable", e); //$NON-NLS-1$
			}
		}
	}

}
