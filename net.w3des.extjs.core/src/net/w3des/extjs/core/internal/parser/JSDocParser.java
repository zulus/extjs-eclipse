package net.w3des.extjs.core.internal.parser;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import net.w3des.extjs.core.internal.ExtJSCore;

/**
 * This class allow to read each comments in reader
 * 
 * @author zulus
 */
public class JSDocParser {
    final private int[][] positions;

    final private Reader reader;
    
    final static private HashMap<String, Integer> tags = new HashMap<String, Integer>(){
        private static final long serialVersionUID = 6490916684021302678L;
    {
        this.put("class", 1);
        this.put("memberOf", 1);
        this.put("param", 2);
        this.put("property", 2);
        this.put("cfg", 2);
        this.put("method", 2);
        this.put("event", 1);
    }};
    
    final static int STATE_NULL = 1;
    final static int STATE_READ_TAG = 2;
    final static int STATE_READ_FIELD = 4;
    final static int STATE_READ_ADDON = 16;
    final static int STATE_IGNORE = 32;
    final static int STATE_ON_TAG = 64;
    final static int STATE_END_TAG = 128;
    final static int STATE_END_FIELD = 256;
    final static int STATE_END_ADDON = 512;
    final static int STATE_END = 1024;
    private int pos;
    
    /**
     * cached
     */
    final Comment[] parsed;

    public JSDocParser(Reader reader, int[][] positions) {
        this.reader = reader;
        if (positions == null) {
            positions = new int[0][0];
        }
        this.positions = positions;
        this.parsed = new Comment[positions.length];
    }

    public JSDocParser(char[] content, int[][] positions) {
        this(new CharArrayReader(content), positions);
    }

    public JSDocParser(InputStream content, int[][] positions) {
        this(new InputStreamReader(content), positions);
    }

    public int count() {
        return positions.length;
    }

    public Comment get(int index) {
        if (index < 0 || index >= positions.length) {
            throw new RuntimeException("Invalid index");
        } else if (parsed[index] == null) {
            parse(index);
        }

        return parsed[index];
    }

    private void parse(int index) {
        final Comment comment = parsed[index] = new Comment(positions[index][0], positions[index][1]);
        
        if (positions[index][1] < 0) {
            positions[index][1] = positions[index][1] * -1;
        }
        if (positions[index][0] < 0) {
            positions[index][0] = positions[index][0] * -1;
        }
        if (positions[index][1] - positions[index][0] < 4) {
            return;
        }
        char[] content = new char[positions[index][1] - positions[index][0]];
        
        try {
            if (pos >= positions[index][0]) {
                reader.reset();
                reader.skip(positions[index][0]);
            } else {
                reader.skip(positions[index][0] - pos);
            }
            reader.read(content);
            pos = positions[index][1];
          
        } catch (IOException e) {
            if (ExtJSCore.getDefault() != null) {
                ExtJSCore.error(e);
            }
            return;
        }
        
        int state = STATE_NULL;
        int startPos = 0;
        StringBuilder build = new StringBuilder();
        Tag tag = null;
        
        for (int i = 0; i < content.length; i++) {
            char ch = content[i];
            if (ch == '\r' || ch == '\n' || i +1 == content.length) {
                if ((state & STATE_READ_TAG) == STATE_READ_TAG) {
                    state |= STATE_END_TAG;
                } else if ((state & STATE_READ_FIELD) == STATE_READ_FIELD || (state & STATE_READ_ADDON) == STATE_READ_ADDON) {
                    state |= STATE_END_FIELD;
                    state &= ~STATE_END_TAG;
                    state &= ~STATE_READ_FIELD;
                }
                
                state |= STATE_END;
            } else if (((ch == ' ' || ch == '\t' || (state & STATE_IGNORE) == STATE_IGNORE) ) && (state & STATE_READ_FIELD) == 0 && (state & STATE_READ_TAG) == 0) {
                continue;
            } else if ((state & STATE_END_TAG) == STATE_END_TAG) {
                if (tag.getFields().size() >= tags.get(tag.getName())) {
                    state |= STATE_READ_ADDON;
                }
                state |= STATE_READ_FIELD;
            }
            
            if ((state & STATE_NULL) == STATE_NULL) {
                if (ch == '@') {
                    state |= STATE_READ_TAG;
                    startPos = i;
                    state &= ~STATE_NULL;
                    continue;
                }
            } else if ((state & STATE_READ_TAG) == STATE_READ_TAG) {
                if (ch == ' ' || ch == '\t') {
                    state |= STATE_END_TAG;
                } else {
                    build.append(ch);
                }
            } else if ((state & STATE_READ_FIELD) == STATE_READ_FIELD) {
                if ((ch == ' ' || ch == '\t' ) && (state & STATE_READ_ADDON) == 0) {
                    state |= STATE_END_FIELD;
                } else {
                    build.append(ch);
                }
            }
            if ((state & STATE_END_TAG) == STATE_END_TAG && (state & STATE_READ_FIELD) != STATE_READ_FIELD) {
                String name = build.toString();
                if (!tags.containsKey(name)) {
                    state = STATE_IGNORE;
                } else {
                    tag = new Tag(positions[index][0] + startPos, positions[index][0] + i - 1, name);
                    comment.addTag(tag);
                    state &= ~STATE_READ_TAG;
                }
                build = new StringBuilder();
            } else if ((state & STATE_END_FIELD) == STATE_END_FIELD) {
                Field f;
                String cnt = build.toString();
                if (build.charAt(0) == '{' && build.charAt(build.length() - 1) == '}') { //TODO better checking
                    f = new FQN(positions[index][0] + startPos, positions[index][0] + i - 1, cnt);
                } else {
                    f = new Field(positions[index][0] + startPos, positions[index][0] + i - 1, cnt);
                }
                tag.getFields().add(f);

                state &= ~STATE_READ_FIELD;
                state &= ~STATE_END_FIELD;
                build = new StringBuilder(); 
            }
            if ((state & STATE_END) == STATE_END) {
                state = STATE_NULL;
            }
        }
    }

    public CommentIterator iterator() {
        return new CommentIterator(this);
    }
}
