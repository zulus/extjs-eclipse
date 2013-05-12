package net.w3des.extjs.ui.jsduck;

import java.io.Reader;

import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.ILocalVariable;
import org.eclipse.wst.jsdt.core.IMember;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.ui.IDocumentationReader;

public class DocumentationProvider implements IDocumentationReader {

	@Override
	public boolean appliesTo(IMember member) {
		try {
			if (member.getJSdocRange() != null && member.getJSdocRange().getLength() > 0 ) { //If contains any documentation for now
				return true;
			}
		} catch (JavaScriptModelException e) {
			ExtJSUI.error("Error during appliesTo in DocumentationProvider", e); //$NON-NLS-1$
		}
		
		return false;
	}

	@Override
	public boolean appliesTo(ILocalVariable declaration) {
		try {
			String jsdoc = declaration.getAttachedJavadoc(new NullProgressMonitor());
			if (jsdoc != null && jsdoc.length() > 0) {
				return true;
			}
		} catch (JavaScriptModelException e) {
			ExtJSUI.error("Error during appliesTo in DocumentationProvider", e); //$NON-NLS-1$
		}
		
		return false;
	}

	@Override
	public Reader getDocumentation2HTMLReader(Reader contentReader) {
		
		return new JSDuck2HTMLReader(contentReader); 
	}

	@Override
	public Reader getContentReader(IMember member, boolean allowInherited) throws JavaScriptModelException {
		return null;
	}

	@Override
	public Reader getContentReader(ILocalVariable declaration, boolean allowInherited) throws JavaScriptModelException {
		return null;
	}

}
