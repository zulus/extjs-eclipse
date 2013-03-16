package net.w3des.extjs.core.infer;

import org.eclipse.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.wst.jsdt.core.compiler.CategorizedProblem;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

@SuppressWarnings("restriction")
/**
 * This class walk
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class JSDuckInfer extends ASTVisitor {
	private CompilationUnitDeclaration unit;

	public JSDuckInfer(CompilationUnitDeclaration unit) {
		this.unit = unit;
		/*
		 * remove all JSDoc problems...
		 */
		//unit.compilationResult.problems = new CategorizedProblem[0];
		//unit.compilationResult.problemCount = 0;
	}
}
