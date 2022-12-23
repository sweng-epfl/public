package providers.manganese;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.*;

import model.Chapter;
import providers.manganese.exceptions.ChapterNotFoundException;
import providers.manganese.exceptions.MangaNotFoundException;
import providers.manganese.exceptions.PageNotFoundException;

/**
 * Automated tests for a few common maintainablity problems we saw
 */
public class ManganeseProviderMaintainabilityTest {

    @Test
    @DisplayName("`findBooksByTitle` should let unrelated exception pass through")
    void findBooksByTitleThrowsUnderlyingUnrelatedErrors() {
        var provider = new ManganeseBookProvider(runtimeFailingApi);
        assertThrows(MockRuntimeException.class, () -> { provider.findBooksByTitle("Berserk") ;});
    }

    @Test
    @DisplayName("`fetchPage` should let unrelated exception pass through")
    void fetchPageThrowsUnderlyingUnrelatedErrors() {
        var provider = new ManganeseBookProvider(runtimeFailingApi);
        var validChapter = runtimeFailingApi.chaptersDb.values().iterator().next();
        var chapter = new Chapter(validChapter.id(), validChapter.title(), validChapter.numberOfPages());
        assertThrows(MockRuntimeException.class, () -> { provider.fetchPage(chapter, chapter.numberOfPages()) ;});
    }

    private final MockManganeseApi runtimeFailingApi = new MockManganeseApi() {
        @Override
        public List<MangaChapter> fetchChapters(String mangaId) throws MangaNotFoundException {
            throw new MockRuntimeException("Should definitely not catch this one");
        }

        @Override
        public String fetchPage(String chapterId, int pageNumber)
            throws ChapterNotFoundException, PageNotFoundException {
            throw new MockRuntimeException("Should definitely not catch this one");
        }
    };

    private class MockRuntimeException extends RuntimeException {
        public MockRuntimeException(String reason) {
            super(reason);
        }
    }
}
