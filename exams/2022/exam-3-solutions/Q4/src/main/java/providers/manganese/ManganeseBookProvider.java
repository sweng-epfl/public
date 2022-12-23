package providers.manganese;

import model.Book;
import model.Chapter;
import providers.BookProvider;
import providers.manganese.exceptions.ChapterNotFoundException;
import providers.manganese.exceptions.MangaNotFoundException;
import providers.manganese.exceptions.PageNotFoundException;

import java.util.List;
import java.util.Objects;

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
            throw new IllegalArgumentException("API cannot be null");
        }
        this.api = api;
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }

        var mangas = api.searchManga(title);
        return mangas.stream().map(m -> {
            try {
                var chapters = api.fetchChapters(m.id())
                        .stream()
                        .map(c -> new Chapter(c.id(), c.title(), c.numberOfPages()))
                        .toList();
                return new Book(m.id(), m.title(), m.description(), chapters);
            } catch (MangaNotFoundException e) {
                return null;
            }
        }).filter(Objects::nonNull).toList();
    }

    @Override
    public String fetchPage(Chapter chapter, int pageNumber) {
        if (chapter == null) {
            throw new IllegalArgumentException("Chapter cannot be null");
        }
        try {
            return api.fetchPage(chapter.id(), pageNumber);
        } catch (PageNotFoundException | ChapterNotFoundException e) {
            throw new IllegalArgumentException("Unknown page or chapter");
        }
    }

    @Override
    public String name() {
        return NAME;
    }
}
