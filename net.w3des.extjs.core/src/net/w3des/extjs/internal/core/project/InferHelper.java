/*******************************************************************************
 * Copyright (c) 20013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.internal.core.project;

import net.w3des.extjs.core.IInferHelper;

import org.eclipse.wst.jsdt.core.infer.IInferenceFile;

/**
 * @author Dawid Pakuła
 */
public class InferHelper implements IInferHelper {

    public void activate() {

    }

    public void deactivate() {

    }

    @Override
    public char[] getType(char[] alias, IInferenceFile file) {

        return null;
    }

    @Override
    public void scan() {
        // TODO Auto-generated method stub

    }
}
