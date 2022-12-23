package model.data;

import static java.util.Objects.requireNonNull;

/**
 * Represents a reaction to a given title.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public record Reaction(String title, boolean liked) {

    /**
     * Creates a new reaction.
     * @param title the title of the book
     * @param liked true if the user liked the book, false otherwise
     */
    public Reaction {
        requireNonNull(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reaction reaction = (Reaction) o;

        return title.equals(reaction.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
