package providers;

import model.Book;
import model.Chapter;

import java.util.List;

/**
 * A provider is used to query and fetch {@link Book}, from an external source.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface BookProvider {

    /**
     * Returns this provider's name.
     */
    String name();

    /**
     * Returns a list of {@link Book} matching a given title
     * Note that this never fails. If a book cannot be fetched,
     * it is just not included in the list.
     *
     * @param title Title to search for, not null
     * @return a list of Book whose title matches the one given  
     * @throws IllegalArgumentException if the title is null
     */
    List<Book> findBooksByTitle(String title);

    /**
     * Fetch a single page from a book {@link Chapter}. For simplicity here a page is
     * represented as a String, but in practice it would be probably an image or a
     * stream of bytes.
     *
     * @param chapter    Chapter containing the page, must be non-null
     * @param pageNumber Number of the page to fetch (1 based index)
     * @return The content of the page
     * @throws IllegalArgumentException if the chapter is null, or cannot be
     * found, or if the pageNumber is not contained in that chapter.
     */
    String fetchPage(Chapter chapter, int pageNumber);
}
