package net.w3des.extjs.core.internal.parser;

/**
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class FQN extends Field {

    public FQN(int start, int stop, String content) {
        super(start, stop, content.substring(1, content.length() - 1));
    }
    
    @Override
    public int getStartContent() {
        return super.getStartContent() + 1;
    }
    
    @Override
    public int getStopContent() {
        return super.getStopContent() - 1;
    }

}
