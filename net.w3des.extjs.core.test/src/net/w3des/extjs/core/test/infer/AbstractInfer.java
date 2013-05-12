package net.w3des.extjs.core.test.infer;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.junit.Before;
import org.junit.Test;

import net.w3des.extjs.core.test.Util;
import net.w3des.extjs.core.test.ExtTFile; 
import net.w3des.extjs.core.test.Versioned.BeforeSuite;
import net.w3des.extjs.core.test.Versioned.ExtSuite;

/**
 * Allow to analyse one test file
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
abstract public class AbstractInfer extends Util {
	
	private IFile created;
	
	private IJavaScriptUnit cu;

	public AbstractInfer(ExtTFile file) {
		super(file);
	}

	@Test
	public void main() throws JavaScriptModelException {
		IType currentType = null;
		
		for (String line : file.getExpect().split("\n")) {
			List<String> tokens = Arrays.asList(line.split(" "));
			ListIterator<String> iterator = tokens.listIterator();
			while (iterator.hasNext()) {
				String token = iterator.next();
				if (token.equals("class")) {
					token = iterator.next();
					currentType = cu.getType(token);
					
					assertTrue(currentType.exists());
					assertEquals(token, currentType.getFullyQualifiedName());
				} else if (token.equals("extends")) {
					token = iterator.next();
					assertTrue(currentType != null && currentType.getSuperclassName().equals(token));
				}
			}
		}
	}
	
	@Before
	public void before() throws CoreException {
		created = file.createFile(project);
		
		cu = JavaScriptCore.createCompilationUnitFrom(created);
	}
	
	@BeforeSuite
	public static Iterable<ExtTFile> beforeRun(ExtSuite cfg) throws Exception {
		project = createExtProject(cfg);
		
		return Util.beforeRun(cfg);
	}
}
