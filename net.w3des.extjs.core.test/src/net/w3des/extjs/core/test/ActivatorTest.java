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
package net.w3des.extjs.core.test;

import static org.junit.Assert.assertEquals;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.junit.Test;

/**
 * Sample integration test. In Eclipse, right-click > Run As > JUnit-Plugin. <br/>
 * In Maven CLI, run "mvn integration-test".
 */
public class ActivatorTest {

    @Test
    public void veryStupidTest() {
        assertEquals("net.w3des.extjs.core", ExtJSCore.PLUGIN_ID);
    }

}
