package net.w3des.extjs.core.test.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import net.w3des.extjs.core.internal.parser.Comment;
import net.w3des.extjs.core.internal.parser.CommentIterator;
import net.w3des.extjs.core.internal.parser.JSDocParser;
import net.w3des.extjs.core.internal.parser.Tag;

import org.junit.Before;
import org.junit.Test;

public class ParserTest { 
    
    private JSDocParser parser;
    
    @Before
    public void prepare() {
        String comments[] = new String[]{
                "/*\n* @class myClass \n */",
                "// @myTag with name",
                "/* @property {reference} name \n */",
                "/* @cfg {ref} name description with spaces */"
        };
        StringBuilder builder = new StringBuilder();
        int[][] positions = new int[comments.length][2];
        int pos = 0;
        for (int i = 0; i < comments.length; i++) {
            builder.append(comments[i]);
            builder.append("\n");
            positions[i][0] = pos;
            positions[i][1] = pos + comments[i].length()-1;
            
            pos += comments[i].length();
        }
        
        parser = new JSDocParser(new StringReader(builder.toString()), positions);
    }
    
    @Test
    public void iteratorTest() {
        CommentIterator iterator = parser.iterator();
        
        assertEquals(4, parser.count());
        while (iterator.hasNext()) {
            Comment c = iterator.next();
            assertNotNull(c);
        }
    }
    
    @Test
    public void readerTest() {
        Comment c = parser.get(0);
        assertTrue(c.hasTag("class"));
        Tag t = c.getTag("class");
        assertNotNull(t);
        assertEquals(1, t.getFields().size());
        assertEquals("myClass", t.getField(0).getContent());
        
        c = parser.get(1);
        assertFalse(c.hasTag("myTag"));
        
        c = parser.get(2);
        assertTrue(c.hasTag("property"));
        assertEquals(2, c.getTag("property").getFields().size());
        assertEquals("reference", c.getTag("property").getField(0).getContent());
        
        c = parser.get(3);
        assertTrue(c.hasTag("cfg"));
        assertEquals(3, c.getTag("cfg").getFields().size());

    }
}
