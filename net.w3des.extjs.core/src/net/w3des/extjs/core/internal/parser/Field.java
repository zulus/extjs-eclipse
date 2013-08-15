package net.w3des.extjs.core.internal.parser;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class Field {
    final int start;
    final int stop;
    final String content;
    
    public Field(int start, int stop, String content) {
        this.start = start;
        this.stop = stop;
        this.content = content;
    }
    
    public int getStart() {
        return start;
    }
    
    public int getStop() {
        return stop;
    }
    
    public int getStartContent() {
        return start;
    }
    
    public int getStopContent() {
        return stop;
    }
    
    public String getContent() {
        return content;
    }
}
