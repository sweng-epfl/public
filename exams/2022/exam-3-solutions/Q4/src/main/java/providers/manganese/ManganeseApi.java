package providers.manganese;


import providers.manganese.exceptions.ChapterNotFoundException;
import providers.manganese.exceptions.MangaNotFoundException;
import providers.manganese.exceptions.PageNotFoundException;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * API for the (fake) Manganese service, is it roughly based on the one from MangaDex if you are curious
 * (it is freely available).
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface ManganeseApi {

    /**
     * Representation of a manga, specific to Manganese.
     *
     * @param id          Unique id of this manga
     * @param title       Title of this manga
     * @param description Text describing the manga
     */
    record Manga(String id, String title, String description) {
        public Manga {
            requireNonNull(id);
            requireNonNull(title);
            requireNonNull(description);
        }
    }

    /**
     * Representation of a chapter, specific to Manganese
     *
     * @param id            Unique id of this chapter
     * @param mangaId       Unique id of the manga containing this chapter
     * @param title         Optional title of the chapter (can be empty or null)
     * @param numberOfPages Number of pages in this chapter
     */
    record MangaChapter(String id, String mangaId, String title, int numberOfPages) {
        public MangaChapter {
            requireNonNull(id);
            requireNonNull(mangaId);
        }
    }

    /**
     * Fetch a list of {@link Manga} matching a given title
     *
     * @param title Title to search for
     * @return The list of {@link Manga} matching the title
     */
    List<Manga> searchManga(String title);

    /**
     * Fetch a list of {@link MangaChapter} of a given manga
     *
     * @param mangaId Unique identifier of the manga containing these chapters
     * @return The list of {@link MangaChapter} of this manga
     * @throws MangaNotFoundException if no manga with the given id exists
     */
    List<MangaChapter> fetchChapters(String mangaId) throws MangaNotFoundException;

    /**
     * Fetch a single page from a given chapter.
     * Will throw a {@link ChapterNotFoundException}
     * if no chapter with the given id exists or a {@link PageNotFoundException}
     * if the given page does not exist in the given chapter.
     *
     * @param chapterId  Unique identifier of the chapter
     * @param pageNumber Page number to fetch
     * @return The content of the page
     * @throws ChapterNotFoundException if no chapter with the given id exists
     * @throws PageNotFoundException    if the page is not contained in the chapter
     */
    String fetchPage(String chapterId, int pageNumber) throws ChapterNotFoundException, PageNotFoundException;
}
