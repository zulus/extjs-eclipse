package net.w3des.extjs.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.w3des.extjs.core.ExtJSCore;

import org.junit.Test;

/**
* Sample integration test. In Eclipse, right-click > Run As > JUnit-Plugin. <br/>
* In Maven CLI, run "mvn integration-test".
*/
public class ActivatorTest {

	@Test
	public void veryStupidTest() {
		assertEquals("net.w3des.extjs.core",ExtJSCore.PLUGIN_ID);
		assertTrue("Plugin should be started", true);
	}
}