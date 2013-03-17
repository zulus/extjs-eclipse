package net.w3des.extjs.core.infer;

import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.wst.jsdt.core.compiler.CharOperation;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.wst.jsdt.internal.core.CompilationUnit;

@SuppressWarnings("restriction")
/**
 * This class walk
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class JSDuckInfer {
	private CompilationUnitDeclaration unit;
	private boolean found = false;
	
	public JSDuckInfer(CompilationUnitDeclaration unit) {
		this.unit = unit;
	}
	
	public boolean compile() {
		return found;
		/*
		if (unit.comments != null  && CharOperation.endsWith(fName, "ClassManager.js".toCharArray())) {
			CompilationUnit t = (CompilationUnit) unit.compilationResult.compilationUnit;
			for (int[] range : unit.comments) {
				if (range[0] > 0) {
					try {
						String text = t.getSource();
						if (text != null) {
							
						}
					} catch (Exception e) {
						ExtJSCore.error("Error during JSDuck compiler", e);
					}
				}
			}
		}
		
		return found; */
	}
}
