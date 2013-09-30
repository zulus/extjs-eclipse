package net.w3des.extjs.internal.core;

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.runtime.CoreException;

public class SaveParticipant implements ISaveParticipant {

    @Override
    public void doneSaving(ISaveContext context) {
    }

    @Override
    public void prepareToSave(ISaveContext context) throws CoreException {

    }

    @Override
    public void rollback(ISaveContext context) {
    }

    @Override
    public void saving(ISaveContext context) throws CoreException {
        switch (context.getKind()) {
        case ISaveContext.FULL_SAVE:
            ExtJSCore.getProjectManager().save();
        }
    }

}
