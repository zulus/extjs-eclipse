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
package net.w3des.extjs.core;

import org.eclipse.wst.jsdt.core.infer.IInferenceFile;

/**
 * Hack that allow infer by xtype
 * 
 * @author Dawid Pakula
 */
public interface IInferHelper {
    /**
     * Get type name for alias
     * 
     * @param alias
     * @param file
     * @return
     */
    public char[] getType(char[] alias, IInferenceFile file);

    /**
     * Scan after doInfer()
     */
    public void scan();
}
