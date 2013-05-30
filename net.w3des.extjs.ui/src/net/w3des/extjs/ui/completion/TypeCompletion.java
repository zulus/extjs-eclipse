package net.w3des.extjs.ui.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.Util;
import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;
import org.eclipse.wst.jsdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.wst.jsdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.wst.jsdt.ui.text.java.JavaContentAssistInvocationContext;

public class TypeCompletion implements IJavaCompletionProposalComputer {
	final private String[] before = new String[] { "Ext.override(", "Ext.create(", "Ext.require(", "requires:[",
			"uses:[", "requires:", "uses:", "override:", "extend:", "mixins", "mixins:[" };

	final private String[] stopBefore = new String[] { ")", ";", "*", "/" };
	String error = null;
	
	public TypeCompletion() {
	}
	
	@Override
	public void sessionStarted() {
		error = null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List computeCompletionProposals(ContentAssistInvocationContext rawContext, IProgressMonitor monitor) {
		try {
			if (!(rawContext instanceof JavaContentAssistInvocationContext)) {
				return Collections.EMPTY_LIST;
			}

			JavaContentAssistInvocationContext context = (JavaContentAssistInvocationContext) rawContext;

			if (!Util.isExtProject(context.getProject())) {
				return Collections.EMPTY_LIST;
			}
			boolean inString = false;
			int currentOffset = context.getInvocationOffset();
			String content = "";
			try {
				content = context.getDocument().get(0, currentOffset);
			} catch (BadLocationException e) {
				ExtJSUI.error(e);

				return Collections.EMPTY_LIST;
			}
			String toCompare = "";
			String realString = "";
			String found = null;
			boolean ignore = false;
			while (currentOffset > 0 && found == null) {
				char token = content.charAt(--currentOffset);
				String p = new String(new char[] { token });
				if (token == ' ' || token == '\t' || token == '\n' || token == '\r') {
					continue;
				}

				if (p.equals(",")) {
					ignore = true;
					continue;
				}

				toCompare = p + toCompare;
				if (!ignore) {
					realString = p + realString;
				}

				if ((p.equals("'") || p.equals("\"")) && !ignore) {
					inString = true;
					ignore = true;
					continue;
				}

				if (!ignore) {
					for (String n : stopBefore) {
						if (p.equals(n)) {
							return Collections.EMPTY_LIST;
						}
					}
				}

				for (String n : before) {
					if (n.length() > 0 && toCompare.startsWith(n)) {
						found = n;
						break;
					}
				}
			}

			if (found == null) {
				return Collections.EMPTY_LIST;
			}
			String start = null;
			if (ignore) {
				start = realString.substring(1);
			} else {
				start = toCompare.substring(found.length() + (inString ? 1 : 0));
			}

			List<ICompletionProposal> proposals = new LinkedList<ICompletionProposal>();

			IType[] types = Util.findTypes(start, context.getProject());
			List<String> l = new ArrayList<String>(types.length);

			for (IType type : types) {
				if (l.contains(type.getTypeQualifiedName())) {
					continue;
				}
				l.add(type.getTypeQualifiedName());

				CompletionProposal proposal;
				String completion = type.getTypeQualifiedName().substring(start.length());
				int completionOffset = context.getInvocationOffset();
				if (!inString) {
					completionOffset -= start.length();
					proposal = new CompletionProposal("'" + type.getTypeQualifiedName() + "'", completionOffset,
							start.length(), type.getTypeQualifiedName().length() + 2, JavaScriptUI.getSharedImages()
									.getImage(ISharedImages.IMG_OBJS_CLASS), type.getTypeQualifiedName(), null, null);
				} else {
					proposal = new CompletionProposal(completion, completionOffset, 0, completion.length(),
							JavaScriptUI.getSharedImages().getImage(ISharedImages.IMG_OBJS_CLASS),
							type.getTypeQualifiedName(), null, null);
				}

				proposals.add(proposal);
			}

			return proposals;
		} catch (Throwable e) {
			ExtJSUI.error(e);
			error = e.getMessage();
			return Collections.EMPTY_LIST;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List computeContextInformation(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getErrorMessage() {
		return error;
	}

	@Override
	public void sessionEnded() {
	}

}
