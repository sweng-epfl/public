package ch.epfl.sweng.exercises.exercise8;

public abstract class DocumentPart {

    private String text;

    public DocumentPart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public abstract String toHTML();
    public abstract String toPlainText();
}
