package model;

/**
 * The VideoFile represents a video file (content) along with some metadata (uniqueID, title, author)
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class VideoFile {

    public final int uniqueID;
    public final String title;
    public final String author;
    public final String content; // simplified content; in a real app, would be some kind of byte stream

    public VideoFile(int uniqueID, String title, String author, String content) {
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null!");
        }
        if (author == null) {
            throw new IllegalArgumentException("author cannot be null!");
        }
        if (content == null) {
            throw new IllegalArgumentException("content cannot be null!");
        }
        this.uniqueID = uniqueID;
        this.title = title;
        this.author = author;
        this.content = content;
    }

}
