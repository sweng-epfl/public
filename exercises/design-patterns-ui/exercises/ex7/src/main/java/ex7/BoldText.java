package ex7;

public class BoldText extends DocumentPart {

    public BoldText(String text) {
        super(text);
    }

    @Override
    public String toHTML() {
        return String.format("<b>%s</b>", getText());
    }

    @Override
    public String toPlainText() {
        return String.format("**%s**", getText());
    }

}
