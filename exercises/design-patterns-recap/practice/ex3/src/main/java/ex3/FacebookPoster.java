package ex3;

public interface FacebookPoster {

    /** Posts the given message and returns its postId */
    public long postMessage(String message);

    /** Returns the message of the post with the given postId */
    public String getMessage(long postId);
}
