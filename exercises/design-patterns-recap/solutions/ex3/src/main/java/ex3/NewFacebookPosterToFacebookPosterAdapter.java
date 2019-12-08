package ex3;

import java.util.Date;

public class NewFacebookPosterToFacebookPosterAdapter implements FacebookPoster {

    private NewFacebookPoster poster;

    public NewFacebookPosterToFacebookPosterAdapter(NewFacebookPoster poster) {
        this.poster = poster;
    }

    @Override
    public long postMessage(String message) {
        Post m = new Post(message, new Date());
        long postId = poster.post(m);
        return postId;
    }

    @Override
    public String getMessage(long postId) {
        return poster.get(postId).getMessage();
    }
}
