package ex3;

import java.util.Date;

class Post {
    private String message;
    private Date postedDate;

    public Post(String message, Date postedDate) {
        this.message = message;
        this.postedDate = postedDate;
    }

    public String getMessage() {
        return message;
    }
}

public interface NewFacebookPoster {

    /** Posts the given message and returns its postId */
    public long post(Post post);

    /** Returns the message of the post with the given postId */
    public Post get(long postId);
}
