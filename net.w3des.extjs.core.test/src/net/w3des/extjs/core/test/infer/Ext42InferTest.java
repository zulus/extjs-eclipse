package net.w3des.extjs.core.test.infer;

import net.w3des.extjs.core.test.ExtTFile;
import net.w3des.extjs.core.test.Versioned;
import net.w3des.extjs.core.test.Versioned.ExtSuite;

import org.junit.runner.RunWith;

@RunWith(Versioned.class)
@ExtSuite(dir = "infer", version = "4.2", sub = { "4.1", "4.2" })
public class Ext42InferTest extends AbstractInfer {

	public Ext42InferTest(ExtTFile file) {
		super(file);
	}

}
