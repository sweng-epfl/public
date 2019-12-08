package ex11.models;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Models a forum thread
 */
public class ForumThread {
    private final List<ForumPost> posts;
    public final String uid;
    public final String author;
    public final String title;
    private final Date date;

    public ForumThread(String author, String title) {
        this.author = author;
        this.title = title;
        this.date = new Date();
        this.posts = new ArrayList<>();
        // unique ID is hash of date + author + title
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String base = new StringBuilder()
                .append(Long.toString(date.getTime()))
                .append(author)
                .append(title)
                .toString();
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            this.uid = bytesToHex(hash);
        } catch (Exception e) {
            // Bad Java API design (stringly-typed algorithm instance)
            System.err.println(e.toString());
            throw new IllegalStateException("Could not generate unique ID");
        }
    }

    /**
     * Returns a string representation for the date
     * 
     * @return string of date
     */
    public String dateRepr() {
        return date.toString();
    }

    /**
     * Returns an immutable view of all posts of the thead
     * 
     * @return all posts in this thread
     */
    public List<ForumPost> getPosts() {
        return Collections.unmodifiableList(new ArrayList<>(posts));
    }

    /**
     * Adds a post
     */
    public void addPost(ForumPost post) {
        this.posts.add(post);
    }

    /**
     * From https://www.baeldung.com/sha-256-hashing-java
     */
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}