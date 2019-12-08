package ex11.views;

import java.util.List;
import java.util.stream.Collectors;

import ex11.models.ForumThread;

public class ForumView implements View {
    private final List<ForumThread> data;

    public ForumView(List<ForumThread> data) {
        this.data = data;
    }

    @Override
    public String render() {
        List<String> topics = data.stream()
            .map((thread) -> "<a href='/forum?id=" + thread.uid + "'>" + thread.title + "</a>")
            .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder()
            .append("<form action='/new' method='POST'>")
            .append("<input type='text' name='author' placeholder='Nickname' />")
            .append("<input type='text' name='title' placeholder='New topic...' />")
            .append("<input type='submit' value='Submit' />")
            .append("</form>")
            .append(String.join("<br/>", topics));

        return sb.toString();
    }
}