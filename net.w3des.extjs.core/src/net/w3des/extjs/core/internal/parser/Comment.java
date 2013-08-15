package net.w3des.extjs.core.internal.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class Comment {
    final private int start;
    final private int end;
    final private List<Tag> tags;

    public Comment(int start, int end) {
        this.start = start;
        this.end = end;
        tags = new LinkedList<Tag>();
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public boolean hasTag(String name) {
        for (Iterator<Tag> iterator = tags.iterator(); iterator.hasNext();) {
            if (iterator.next().getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public Tag getTag(String name) {
        for (Iterator<Tag> iterator = tags.iterator(); iterator.hasNext();) {
            Tag t = iterator.next();
            if (t.getName().equals(name)) {
                return t;
            }
        }

        return null;
    }

    public Tag[] findTags(String name) {
        final LinkedList<Tag> list = new LinkedList<Tag>();
        for (Iterator<Tag> iterator = tags.iterator(); iterator.hasNext();) {
            Tag t = iterator.next();
            if (t.getName().equals(name)) {
                list.add(t);
            }
        }

        return list.toArray(new Tag[list.size()]);
    }
}
