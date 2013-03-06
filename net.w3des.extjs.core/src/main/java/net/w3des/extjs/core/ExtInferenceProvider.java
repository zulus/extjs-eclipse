package net.w3des.extjs.core;

import net.w3des.extjs.core.infer.InferEngine;

import org.eclipse.wst.jsdt.core.infer.IInferEngine;
import org.eclipse.wst.jsdt.core.infer.IInferenceFile;
import org.eclipse.wst.jsdt.core.infer.InferrenceProvider;
import org.eclipse.wst.jsdt.core.infer.RefactoringSupport;
import org.eclipse.wst.jsdt.core.infer.ResolutionConfiguration;

public class ExtInferenceProvider implements InferrenceProvider {
	final public static String ID = "net.w3des.extjs.core.inferenceSupport";

	public IInferEngine getInferEngine() {
		return new InferEngine();
	}

	public int applysTo(IInferenceFile scriptFile) {
		return MAYBE_THIS;
	}

	public String getID() {
		return ID;
	}

	public ResolutionConfiguration getResolutionConfiguration() {
		return new ResolutionConfiguration();
	}

	public RefactoringSupport getRefactoringSupport() {
		return new RefactoringSupport();
	}

}
