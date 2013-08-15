package net.w3des.extjs.core.internal.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class Tag {
    final private String name;
    final private List<Object> fields;
    final private int start;
    final private int stop;
    
    @SuppressWarnings("rawtypes")
    public Tag(int start, int stop, String name)
    {
        this.name = name;
        this.start = start;
        this.stop = stop;
        this.fields = new LinkedList();
    }
    
    public int getStart() {
        return start;
    }
    
    public int getStop() {
        return stop;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Object> getFields() {
        return fields;
    }
    
    public Field getField(int index) {
        return (Field) fields.get(index);
    }
}
