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
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
