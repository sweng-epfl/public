package ch.epfl.sweng.exercises.exercise8_solutions;

public interface Visitor {
    void visit(PlainText part);
    void visit(BoldText part);
    void visit(HyperLink part);
}
