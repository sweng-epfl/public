package ex7;

public class HyperLink extends DocumentPart {

    private String URL;
    public HyperLink(String text, String URL) {
        super(text);
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    @Override
    public String toHTML() {
        return String.format("<a href=\"%s\">%s</a>", URL, getText());
    }

    @Override
    public String toPlainText() {
        return String.format("[%s](%s)", getText(), URL);
    }

}
