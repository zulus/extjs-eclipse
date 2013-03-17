package net.w3des.extjs.ui.jsduck;

import java.io.IOException;
import java.io.Reader;

import org.eclipse.wst.jsdt.internal.ui.text.html.SubstitutionTextReader;

@SuppressWarnings({ "restriction"})
public class JSDuck2HTMLReader extends SubstitutionTextReader {
	
	private static final String EMPTY_STRING= ""; //$NON-NLS-1$
	
	boolean processingSimpleTag = true;
	
	protected JSDuck2HTMLReader(Reader reader) {
		super(reader);
		setSkipWhitespace(false);
	}

	@Override
	protected String computeSubstitution(int c) throws IOException {
		if (c == '@' && !processingSimpleTag) {
			return processSimpleTag();
		}
		
		if (c == '\n') {
			return checkEndLine();
		}
		return processText();
	}
	
	private String processSimpleTag() {
		StringBuffer bug = new StringBuffer();
		processingSimpleTag = true;
		char item;
		
		return null;
	}
	
	private String simpleTagToHtml() {
		return null;
	}
	
	private String processText() {
		return null;
	}
	
	private String checkEndLine() {
		
		return null;
	}

}
