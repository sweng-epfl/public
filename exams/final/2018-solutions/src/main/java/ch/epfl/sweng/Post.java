package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

public abstract class Post {
    private final User author;
    private String text;

    /**
     * Constructs a post made by the specified user, with the specified text.
     */
    Post(User author, String text) {
        this.author = author;
        this.text = text;
    }

    /**
     * Gets the author of this post.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Gets the text of this post.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of this post.
     */
    public void setText(String text) {
        this.text = text;
    }
}