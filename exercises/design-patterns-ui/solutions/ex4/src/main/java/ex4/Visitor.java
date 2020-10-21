package ex4;

public interface Visitor {
    void visit(PlainText part);
    void visit(BoldText part);
    void visit(HyperLink part);
}
