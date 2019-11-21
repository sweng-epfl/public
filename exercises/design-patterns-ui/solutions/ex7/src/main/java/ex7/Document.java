package ex7;

import java.util.LinkedList;
import java.util.List;

public class Document {

    private List<DocumentPart> parts;

    public Document() {
        parts = new LinkedList<>();
    }

    public void add(DocumentPart part) {
        parts.add(part);
    }

    public void accept(Visitor visitor) {
        for (DocumentPart part: parts) {
            part.accept(visitor);
        }
    }
}
