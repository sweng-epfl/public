package providers.manganese;

import model.Book;
import model.Chapter;

import org.junit.jupiter.api.*;
import providers.BookProvider;
import providers.manganese.exceptions.ChapterNotFoundException;
import providers.manganese.exceptions.MangaNotFoundException;
import providers.manganese.exceptions.PageNotFoundException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A test suite for {@link ManganeseBookProvider}.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You MUST use this file to test {@link ManganeseBookProvider}
 * You CAN edit everything in this file
 * You MUST NOT rename or delete this file
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
final class ManganeseProviderTest {

    @Test
    @DisplayName("`findBooksByTitle` throws an `IllegalArgumentException` given a null title")
    void findBooksByTitleThrowsIllegalArgExceptionOnNullArg() {
        var api = new MockManganeseApi();
        BookProvider bookProvider = new ManganeseBookProvider(api);
        assertThrows(IllegalArgumentException.class, () -> {
            bookProvider.findBooksByTitle(null);
        });
    }

    @Test
    @DisplayName("`findBooksByTitle` returns the book with the given title")
    void searchWithASpecificTitleContainsThatSpecificBook() {
        var api = new MockManganeseApi();
        var specificManga = api.mangasDb.get(0);
        BookProvider bookProvider = new ManganeseBookProvider(api);

        var result = bookProvider.findBooksByTitle(specificManga.title());

        assertThat(result.stream().map(Book::title).toList(), contains(specificManga.title()));
        assertThat(result.stream().map(Book::id).toList(), contains(specificManga.id()));
        assertThat(result.stream().map(Book::description).toList(), contains(specificManga.description()));
    }

    @Test
    @DisplayName("`findBooksByTitle` includes all chapters in the returned books")
    void booksFetchedContainsAllTheirChapters() {
        var api = new SingleBookApi();
        var provider = new ManganeseBookProvider(api);

        var result = provider.findBooksByTitle(api.manga.title());
        assertThat(result.size(), is(1));

        var book = result.get(0);
        assertThat(book.chapters().size(), is(api.chapters.size()));
        for (int i = 0; i < api.chapters.size(); i++) {
            var chapter = api.chapters.get(i);
            var bookChapter = book.chapters().get(i);
            assertThat(bookChapter.id(), is(chapter.id()));
            assertThat(bookChapter.title(), is(chapter.title()));
            assertThat(bookChapter.numberOfPages(), is(chapter.numberOfPages()));
        }
    }

    @Test
    @DisplayName("`findBooksByTitle` returns nothing when there is no book with that title")
    void searchWithUnknownTitleReturnsNothing() {
        var api = new MockManganeseApi();
        BookProvider bookProvider = new ManganeseBookProvider(api);
        var result = bookProvider
                .findBooksByTitle("That time I got reincarnated as Alain Berset, in another world.");
        assertThat(result, is(empty()));
    }

    @Test
    @DisplayName("`findBooksByTitle` returns nothing when the underlying API fails")
    void searchWithFailingApiReturnsEmpty() {
        BookProvider provider = new ManganeseBookProvider(failingApi);

        var result = provider.findBooksByTitle("When They Cry");
        assertThat(result, is(empty()));
    }

    @Test
    @DisplayName("`findBooksByTitle` returns only the successfully-fetched books when the underlying API is unstable")
    void searchWithPartiallyFailingApiReturnsOnlySuccess() {
        var api = new UnstableApi();
        var provider = new ManganeseBookProvider(api);

        var result = provider.findBooksByTitle("When They Cry");
        assertThat(result.size(), is(1));

        var book = result.get(0);
        assertThat(book.title(), is(api.manga.title()));
    }

    @Test
    @DisplayName("`fetchPage` throws an `IllegalArgumentException` given a null chapter")
    void fetchPageThrowsIllegalArgExceptionOnNullArg() {
        var api = new SingleBookApi();
        var provider = new ManganeseBookProvider(api);

        assertThrows(IllegalArgumentException.class, () -> provider.fetchPage(null, 10));
    }

    @Test
    @DisplayName("`fetchPage` throws an `IllegalArgumentException` given an unknown chapter")
    void fetchPageThrowsIllegalArgOnUnknownChapter() {
        var api = new SingleBookApi();
        var provider = new ManganeseBookProvider(api);

        assertThrows(IllegalArgumentException.class, () -> provider.fetchPage(new Chapter("123456", "...", 20), 10));
    }

    @Test
    @DisplayName("`fetchPage` throws an `IllegalArgumentException` given a page number that is too low")
    void fetchPageThrowsIllegalArgOnPageTooLow() {
        var api = new SingleBookApi();
        var provider = new ManganeseBookProvider(api);
        var mangaChapter = api.chapters.get(0);
        var chapter = new Chapter(mangaChapter.id(), mangaChapter.title(), mangaChapter.numberOfPages());

        assertThrows(IllegalArgumentException.class, () -> provider.fetchPage(chapter, 0));
    }

    @Test
    @DisplayName("`fetchPage` throws an `IllegalArgumentException` given a page number that is too high")
    void fetchPageThrowsIllegalArgOnPageTooHigh() {
        var api = new SingleBookApi();
        var provider = new ManganeseBookProvider(api);
        var mangaChapter = api.chapters.get(0);
        var chapter = new Chapter(mangaChapter.id(), mangaChapter.title(), mangaChapter.numberOfPages());

        assertThrows(IllegalArgumentException.class, () -> provider.fetchPage(chapter, chapter.numberOfPages()+1));
    }

    @Test
    @DisplayName("`fetchPage` fetches the expected page contents given valid arguments")
    void fetchPageWithCorrectArgumentsFetchContent() {
        var api = new SingleBookApi();
        var provider = new ManganeseBookProvider(api);
        var mangaChapter = api.chapters.get(0);
        var chapter = new Chapter(mangaChapter.id(), mangaChapter.title(), mangaChapter.numberOfPages());
        var content = provider.fetchPage(chapter, chapter.numberOfPages());

        assertThat(content, is(chapter.id() + chapter.numberOfPages()));
    }




    private final ManganeseApi failingApi = new ManganeseApi() {

        private final Manga manga = new Manga("1234", "Umineko When They Cry", "Lorem ipsum");

        @Override
        public List<Manga> searchManga(String title) {
            if (title == null) {
                throw new IllegalArgumentException();
            }
            if (manga.title().contains(title)) {
                return List.of(manga);
            }
            return List.of();
        }

        @Override
        public List<MangaChapter> fetchChapters(String mangaId) throws MangaNotFoundException {
            throw new MangaNotFoundException(mangaId);
        }

        @Override
        public String fetchPage(String chapterId, int pageNumber) throws ChapterNotFoundException {
            throw new ChapterNotFoundException(chapterId);
        }
    };

    private static class UnstableApi implements ManganeseApi {
        final ManganeseApi.Manga manga =
                new Manga("1234", "Umineko When They Cry", "Lorem ipsum");
        final ManganeseApi.Manga failingManga =
                new Manga("07151129", "Higurashi When They Cry", "Lorem ipsum");
        final List<MangaChapter> chapters = List.of(
                new MangaChapter("0", manga.id(), "chapter 1", 10),
                new MangaChapter("1", manga.id(), "chapter 2", 11),
                new MangaChapter("2", manga.id(), "chapter 3", 12)
        );

        @Override
        public List<Manga> searchManga(String title) {
            if (title == null) {
                throw new IllegalArgumentException();
            }
            return List.of(manga, failingManga)
                .stream().filter(m -> m.title().contains(title)).toList();
        }

        @Override
        public List<MangaChapter> fetchChapters(String mangaId) throws MangaNotFoundException {
            if (mangaId.equals(manga.id()))
                return chapters;
            throw new MangaNotFoundException(mangaId);
        }

        @Override
        public String fetchPage(String chapterId, int pageNumber) {
            return null;
        }
    }

    private static class SingleBookApi implements ManganeseApi {


        final ManganeseApi.Manga manga =
                new Manga("1234", "Umineko When They Cry", "Lorem ipsum");
        final List<MangaChapter> chapters = List.of(
                new MangaChapter("0", manga.id(), "chapter 1", 10),
                new MangaChapter("1", manga.id(), "chapter 2", 11),
                new MangaChapter("2", manga.id(), "chapter 3", 12)
        );

        @Override
        public List<Manga> searchManga(String title) {
            if (title == null) {
                throw new IllegalArgumentException();
            }
            if (manga.title().contains(title)) {
                return List.of(manga);
            }
            return List.of();
        }

        @Override
        public List<MangaChapter> fetchChapters(String mangaId) throws MangaNotFoundException {
            if (mangaId.equals(manga.id())) {
                return chapters;
            }
            throw new MangaNotFoundException(mangaId);
        }

        @Override
        public String fetchPage(String chapterId, int pageNumber)
            throws ChapterNotFoundException, PageNotFoundException {

            if (chapterId == null) {
                throw new IllegalArgumentException();
            }

            var chapter = chapters.stream().filter(m -> m.id().equals(chapterId)).findFirst();
            if (chapter.isEmpty()) {
                throw new ChapterNotFoundException(chapterId);
            }
            var content = chapter.get();
            if (!(1 <= pageNumber && pageNumber <= content.numberOfPages())) {
                throw new PageNotFoundException(pageNumber, chapterId);
            }

            return chapterId + pageNumber;
        }
    }
}
