package model;

import static java.util.Objects.requireNonNull;

/**
 * A document is a piece of text with a title.
 *
 * @param id      The unique identifier for this document.
 * @param title   The title of the document.
 * @param content The text of the document.
 */
public record Document(String id, String title, String content) {

    public Document {
        requireNonNull(id);
        requireNonNull(title);
        requireNonNull(content);
    }

    /**
     * Returns the formatted document, ready to be printed.
     */
    public String formatted() {
        return title + "\n---\n" + content;
    }
}
