package net.w3des.extjs.core.infer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.wst.jsdt.core.ast.IExpression;
import org.eclipse.wst.jsdt.core.compiler.CharOperation;

import net.w3des.extjs.core.model.extjs.Alias;
import net.w3des.extjs.core.model.extjs.Widget;
import net.w3des.extjs.core.model.extjs.impl.ExtJsFactoryImpl;

public class TypeData implements Serializable {
	private static final long serialVersionUID = 4302226813859794685L;
	public final static char[] widgetPrefix = new char[] {'w', 'i', 'd', 'g', 'e', 't', '.'};
	
	final private Map<String, Alias> aliases;
	
	public TypeData() {
		aliases = new HashMap<String, Alias>();
	}
	
	public Map<String, Alias> getAliases() {
		return aliases;
	}
	
	public void addAlias(char[] typeName, char[] alias, IExpression initializer) {
		if (CharOperation.prefixEquals(widgetPrefix, alias)) {
			Widget widget = ExtJsFactoryImpl.init().createWidget();
			widget.setName(String.valueOf(Arrays.copyOf(alias, widgetPrefix.length)));
			widget.setClassName(String.valueOf(typeName));
			widget.setSourceStart(initializer.sourceStart());
			widget.setSourceEnd(initializer.sourceEnd());
			aliases.put(String.valueOf(alias), widget);
		}
	}
}
