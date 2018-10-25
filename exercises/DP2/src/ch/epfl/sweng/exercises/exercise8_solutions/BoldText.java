package ch.epfl.sweng.exercises.exercise8_solutions;

public class BoldText extends DocumentPart {

    public BoldText(String text) {
        super(text);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
