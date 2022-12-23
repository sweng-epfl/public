package providers.manganese.exceptions;

/**
 * An exception which is thrown when requesting a manga that does not exist.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class MangaNotFoundException extends ManganeseException {
    public MangaNotFoundException(String mangaId) {
        super("Manga with id = %s does not exist.".formatted(mangaId));
    }
}
