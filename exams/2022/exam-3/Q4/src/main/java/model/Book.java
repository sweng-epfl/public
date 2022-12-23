package model;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A Book
 *
 * @param id          The unique id of the book (for a particular provider)
 * @param title       The title of this book
 * @param description Text describing this book
 * @param chapters    List of available {@link Chapter} of this book, in order
 *  <p>
 *  !!!!!!!!!!!!!!!!!!!!!!
 *  DO NOT TOUCH THIS FILE
 *  !!!!!!!!!!!!!!!!!!!!!!
 */
public record Book(String id, String title, String description, List<Chapter> chapters) {

    public Book {
        requireNonNull(id);
        requireNonNull(title);
        requireNonNull(description);
        requireNonNull(chapters);
    }
}
