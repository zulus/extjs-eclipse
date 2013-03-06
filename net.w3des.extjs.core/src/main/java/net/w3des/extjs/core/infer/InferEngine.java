package net.w3des.extjs.core.infer;

import org.eclipse.wst.jsdt.core.infer.IInferEngine;
import org.eclipse.wst.jsdt.core.infer.InferOptions;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

public class InferEngine implements IInferEngine  {
	private InferOptions inferOptions;
	@SuppressWarnings("restriction")
	private CompilationUnitDeclaration parsedUnit;
	
	public void initialize() {
		
	}

	@SuppressWarnings("restriction")
	public void setCompilationUnit(CompilationUnitDeclaration parsedUnit) {
		this.parsedUnit = parsedUnit;
	}

	@SuppressWarnings("restriction")
	public void doInfer() {
		Inferrer inf = new Inferrer(parsedUnit, inferOptions);
		parsedUnit.traverse(inf);
	}
 
	public void initializeOptions(InferOptions inferOptions) {
		this.inferOptions = inferOptions;
		inferOptions.useAssignments = true;
		inferOptions.useInitMethod = true;
		inferOptions.engineClass = this.getClass().getName();
		inferOptions.saveArgumentComments = true;
  	}
	
}
