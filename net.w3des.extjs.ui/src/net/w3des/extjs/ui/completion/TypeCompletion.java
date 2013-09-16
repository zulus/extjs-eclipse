package net.w3des.extjs.ui.completion;

import java.util.ArrayList;
import java.util.List;

import net.w3des.extjs.core.Utils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.ui.ISharedImages;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;
import org.eclipse.wst.jsdt.ui.text.java.JavaContentAssistInvocationContext;

public class TypeCompletion extends AbstractCompletion {
    public TypeCompletion() {
        super(new String[] { "Ext.override(", "Ext.create(", "Ext.require(", "requires:[", "uses:[", "requires:",
                "uses:", "override:", "extend:", "mixins", "mixins:[" });
    }

    @Override
    protected void computeCompletionProposals(boolean inString, String start, List<ICompletionProposal> proposals,
            JavaContentAssistInvocationContext context, IProgressMonitor monitor) {
        final IType[] types = Utils.findTypes(start, context.getProject());
        final List<String> l = new ArrayList<String>(types.length);

        for (final IType type : types) {
            if (l.contains(type.getTypeQualifiedName())) {
                continue;
            }
            l.add(type.getTypeQualifiedName());

            proposals.add(createTextProposal(inString,
                    JavaScriptUI.getSharedImages().getImage(ISharedImages.IMG_OBJS_CLASS), type.getTypeQualifiedName(),
                    context.getInvocationOffset(), start, null, null));

        }
    }
}
