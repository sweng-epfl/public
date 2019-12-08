package ex7;

public class LatexVisitor implements Visitor {

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
        output += String.format("\\textbf{%s}", part.getText());
    }

    @Override
    public void visit(HyperLink part) {
        output += String.format("\\href{%s}{%s}", part.getURL(), part.getText());
    }
}
