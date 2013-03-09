package net.w3des.extjs.core.validation;

import org.eclipse.wst.jsdt.core.dom.ASTVisitor;
import org.eclipse.wst.jsdt.core.dom.FunctionInvocation;
import org.eclipse.wst.jsdt.core.dom.FunctionRef;

public class BindingVisitor extends ASTVisitor {
	@Override
	public boolean visit(FunctionInvocation node) {
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FunctionRef node) {
		return super.visit(node);
	}
}
