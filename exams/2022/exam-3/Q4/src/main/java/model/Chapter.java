package model;

import static java.util.Objects.requireNonNull;

/**
 * A chapter is simply an identifier and an optional title.
 * The content is fetch using the identifier separately.
 *
 * @param id            The unique identifier of this chapter (for a particular provider)
 * @param title         The title (potentially empty or null) of this chapter
 * @param numberOfPages Number of pages contained in this chapter
 *  <p>
 *  !!!!!!!!!!!!!!!!!!!!!!
 *  DO NOT TOUCH THIS FILE
 *  !!!!!!!!!!!!!!!!!!!!!!
 */
public record Chapter(String id, String title, int numberOfPages) {
    public Chapter {
        requireNonNull(id);
    }
}
