package net.w3des.extjs.ui.handler;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;

public class AddExtJS42Support extends AddExtJSSupport {

    @Execute
    public static void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection) throws ExecutionException {
        execute(selection, "4.2");
    }

}
