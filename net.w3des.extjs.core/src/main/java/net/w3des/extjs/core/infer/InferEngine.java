package net.w3des.extjs.core.infer;

import org.eclipse.wst.jsdt.core.infer.IInferEngine;
import org.eclipse.wst.jsdt.core.infer.InferOptions;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

@SuppressWarnings("restriction")
public class InferEngine implements IInferEngine  {
	private InferOptions inferOptions;
	private CompilationUnitDeclaration parsedUnit;
	
	public void initialize() {
		parsedUnit = null;
		inferOptions = null;
	}

	public void setCompilationUnit(CompilationUnitDeclaration parsedUnit) {
		this.parsedUnit = parsedUnit;
	}

	public void doInfer() {
		parsedUnit.traverse(new AliasInfer(parsedUnit));
		parsedUnit.traverse(new Inferrer(parsedUnit, inferOptions));
	}
 
	public void initializeOptions(InferOptions inferOptions) {
		this.inferOptions = inferOptions;
		inferOptions.useAssignments = true;
		inferOptions.useInitMethod = true;
		inferOptions.saveArgumentComments = true;
		inferOptions.engineClass = this.getClass().getName();
  	}
	
}
