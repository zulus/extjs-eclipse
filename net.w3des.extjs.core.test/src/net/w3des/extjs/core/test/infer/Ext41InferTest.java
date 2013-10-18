/*******************************************************************************
 * Copyright (c) 2013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
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
