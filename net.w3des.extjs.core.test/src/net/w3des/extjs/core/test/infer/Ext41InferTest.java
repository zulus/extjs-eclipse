package net.w3des.extjs.core.test.infer;

import net.w3des.extjs.core.test.ExtTFile;
import net.w3des.extjs.core.test.Versioned;
import net.w3des.extjs.core.test.Versioned.ExtSuite;

import org.junit.runner.RunWith;

@RunWith(Versioned.class)
@ExtSuite(dir = "infer", version = "4.1", sub = { "4.1" })
public class Ext41InferTest extends AbstractInfer {

    public Ext41InferTest(ExtTFile file) {
        super(file);
    }

}
