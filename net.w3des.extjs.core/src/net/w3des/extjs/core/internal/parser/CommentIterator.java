package net.w3des.extjs.core.internal.parser;

import java.util.Iterator;

public class CommentIterator implements Iterator<Comment> {
    private int pos = 0;
    
    private JSDocParser parser;
    
    public CommentIterator(JSDocParser parser) {
        this.parser = parser;
    }
    
    @Override
    public boolean hasNext() {
        return pos + 1 < parser.count();
    }

    @Override
    public Comment next() {
        return parser.get(pos++);
    }

    @Override
    public void remove() {
    }

}
