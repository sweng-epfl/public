package ex3;

import java.util.HashMap;
import java.util.Map;

public class FacebookPosterImpl implements FacebookPoster {

    private static long postId = 0;
    private static Map<Long, String> posts;

    public FacebookPosterImpl() {
        posts = new HashMap<>();
    }

    @Override
    public long postMessage(String message) {
        posts.put(postId, message);
        postId++;
        return postId - 1;
    }

    @Override
    public String getMessage(long postId) {
        return posts.get(postId);
    }
}
