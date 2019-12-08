package ex7;

public class PlainTextVisitor implements Visitor {

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
        output += String.format("**%s**", part.getText());
    }

    @Override
    public void visit(HyperLink part) {
        output += String.format("[%s](%s)", part.getText(), part.getURL());
    }
}
