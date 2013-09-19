package net.w3des.extjs.ui.completion;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.w3des.extjs.core.Utils;
import net.w3des.extjs.ui.ExtJSUI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.jsdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.wst.jsdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.wst.jsdt.ui.text.java.JavaContentAssistInvocationContext;

abstract public class AbstractCompletion implements IJavaCompletionProposalComputer {

    private int maxLength = 0;
    private String[] before = new String[] { "Ext.override(", "Ext.create(", "Ext.require(", "requires:[", "uses:[", "requires:", "uses:", "override:",
            "extend:", "mixins:", "mixins:[" };

    final private String[] stopBefore = new String[] { ")", ";", "}" };
    private String error = null;

    public AbstractCompletion(String[] before) {
        this.before = before;
        for (final String item : before) {
            maxLength = item.length() > maxLength ? item.length() : maxLength;
        }
    }

    @Override
    public void sessionStarted() {
        error = null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    /**
     * TODO : Rewrite this :P
     */
    public List computeCompletionProposals(ContentAssistInvocationContext rawContext, IProgressMonitor monitor) {
        try {
            if (!(rawContext instanceof JavaContentAssistInvocationContext)) {
                return Collections.EMPTY_LIST;
            }
            final JavaContentAssistInvocationContext context = (JavaContentAssistInvocationContext) rawContext;
            if (!Utils.isExtProject(context.getProject())) {
                return Collections.EMPTY_LIST;
            }
            boolean inString = false;
            int currentOffset = context.getInvocationOffset();
            String content = "";
            try {
                content = context.getDocument().get(0, currentOffset);
            } catch (final BadLocationException e) {
                ExtJSUI.error(e);

                return Collections.EMPTY_LIST;
            }
            String toCompare = "";
            String realString = "";
            String found = null;
            String stringStart = null;
            boolean ignore = false;
            while (currentOffset > 0 && found == null && toCompare.length() - realString.length() <= maxLength) {
                final char token = content.charAt(--currentOffset);
                final String p = new String(new char[] { token });
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

                if ((p.equals("'") || p.equals("\"")) && !inString) {
                    inString = true;
                    ignore = true;
                    stringStart = p;
                    continue;
                } else if (stringStart != null && p.equals(stringStart) && inString) {
                    stringStart = null;
                    continue;
                }

                if (!ignore || (ignore && stringStart == null)) {
                    for (final String n : stopBefore) {
                        if (p.equals(n)) {
                            return Collections.EMPTY_LIST;
                        }
                    }
                }
                for (final String n : before) {
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

            final List<ICompletionProposal> proposals = new LinkedList<ICompletionProposal>();

            computeCompletionProposals(inString, start, proposals, context, monitor);
            final List<String> strs = new LinkedList<String>();
            Iterator<ICompletionProposal> iterator = proposals.iterator();
            while (iterator.hasNext()) {
                ICompletionProposal next = iterator.next();
                if (strs.contains(next.getDisplayString())) {
                    iterator.remove();
                }
                strs.add(next.getDisplayString());
            }

            return proposals;
        } catch (final Throwable e) {
            error = e.getMessage();
            return Collections.EMPTY_LIST;
        }
    }

    protected abstract void computeCompletionProposals(boolean inString, String start, List<ICompletionProposal> proposals,
            JavaContentAssistInvocationContext context, IProgressMonitor monitor);

    protected ICompletionProposal createTextProposal(boolean inString, Image ico, String proposal, int offset, String start,
            IContextInformation contextInformation, String additionalProposalInfo) {

        if (inString) {
            final String completion = proposal.substring(start.length());
            return new CompletionProposal(completion, offset, 0, completion.length(), ico, proposal, contextInformation, additionalProposalInfo);
        }

        return new CompletionProposal("'" + proposal + "'", offset - start.length(), start.length(), proposal.length() + 2, ico, proposal, contextInformation,
                additionalProposalInfo);

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
