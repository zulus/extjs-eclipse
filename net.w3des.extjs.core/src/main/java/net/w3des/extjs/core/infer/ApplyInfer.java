package net.w3des.extjs.core.infer;

import org.eclipse.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.wst.jsdt.core.infer.InferredType;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

@SuppressWarnings("restriction")
public class ApplyInfer extends ASTVisitor {
	@SuppressWarnings("unused")
	final private InferredType type;
	@SuppressWarnings("unused")
	final private CompilationUnitDeclaration unit;

	public ApplyInfer(InferredType type, CompilationUnitDeclaration unit) {
		this.type = type;
		this.unit = unit;
	}
}
