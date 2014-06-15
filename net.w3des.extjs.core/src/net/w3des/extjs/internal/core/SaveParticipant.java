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
            ExtJSCore.getLibraryManager().save();
        }
    }

}
