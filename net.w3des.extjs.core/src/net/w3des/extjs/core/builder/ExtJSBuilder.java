package net.w3des.extjs.core.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Extra data collector
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class ExtJSBuilder extends IncrementalProjectBuilder {

    public static final String ID = "net.w3des.extjs.core.builder.extJSBuilder";

    public ExtJSBuilder() {
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        return null;
    }

}
