package net.w3des.extjs.core;

import org.eclipse.wst.jsdt.core.infer.IInferenceFile;

/**
 * Hack that allow infer by xtype
 * 
 * @author Dawid Pakula
 */
public interface IInferHelper {
    /**
     * Get type name for alias
     * 
     * @param alias
     * @param file
     * @return
     */
    public char[] getType(char[] alias, IInferenceFile file);

    /**
     * Scan after doInfer()
     */
    public void scan();
}
