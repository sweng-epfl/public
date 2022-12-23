package providers.manganese;

import model.Book;
import model.Chapter;
import providers.BookProvider;

import java.util.List;

/**
 * Provider for the Manganese API, which provides various mangas.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods and constructors
 * You CAN add new `private` members
 * You MUST NOT add interface implementations or new non-`private` members, unless explicitly instructed to do so
 * You MUST NOT edit the names, parameters, checked exceptions, or return types of existing members
 * You MUST NOT delete existing members
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class ManganeseBookProvider implements BookProvider {

    private static final String NAME = "Manganese";
    private final ManganeseApi api;

    /**
     * Return a new Manganese provider based on a given Api
     *
     * @param api Api that this provider will use
     */
    public ManganeseBookProvider(ManganeseApi api) {
        if (api == null) {
            throw new IllegalArgumentException();
        }
        this.api = api;
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String fetchPage(Chapter chapter, int pageNumber) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String name() {
        return NAME;
    }
}
