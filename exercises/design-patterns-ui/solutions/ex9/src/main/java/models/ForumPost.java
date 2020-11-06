package models;

import java.util.Date;

/**
 * Models a post in a thread
 */
public class ForumPost {
    public final String author;
    public final String body;
    private final Date date;

    public ForumPost(String author, String body) {
        this.author = author;
        this.body = body;
        this.date = new Date();
    }

    /**
     * Returns a string representation for the date
     * 
     * @return string of date
     */
    public String dateRepr() {
        return date.toString();
    }
}
