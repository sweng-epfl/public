package ex11.views;

import java.util.List;
import java.util.stream.Collectors;

import ex11.models.ForumThread;

public class ThreadView implements View {
    private final ForumThread data;

    public ThreadView(ForumThread data) {
        this.data = data;
    }

    @Override
    public String render() {
        List<String> posts = data.getPosts()
            .stream()
            .map(post -> new StringBuilder()
                .append("<p>")
                .append(post.author)
                .append(" replied ")
                .append(post.dateRepr())
                .append("<br>")
                .append(post.body)
                .append("<p>")
                .toString())
            .collect(Collectors.toList());
        
        StringBuilder sb = new StringBuilder()
            .append(String.join("", posts))
            .append("<form action='/reply' method='POST'>")
            .append("<input type='hidden' name='threadId' value='" + data.uid + "' />")
            .append("<input type='text' name='author' placeholder='Nickname' />")
            .append("<input type='text' name='body' placeholder='Your reply...' />")
            .append("<input type='submit' value='Submit' />")
            .append("</form>");
        return sb.toString();
    }
}