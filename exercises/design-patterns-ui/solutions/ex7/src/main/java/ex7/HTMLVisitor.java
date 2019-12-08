package ex7;

public class HTMLVisitor implements Visitor {

    private String output = "";

    public String getOutput() {
        return output;
    }

    @Override
    public void visit(PlainText part) {
        output += part.getText();
    }

    @Override
    public void visit(BoldText part) {
        output += String.format("<b>%s</b>", part.getText());
    }

    @Override
    public void visit(HyperLink part) {
        output += String.format("<a href=\"%s\">%s</a>", part.getURL(), part.getText());
    }
}
