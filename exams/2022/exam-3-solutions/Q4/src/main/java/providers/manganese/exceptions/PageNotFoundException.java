package providers.manganese.exceptions;

/**
 * An exception which is thrown when requesting a page that does not exist.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class PageNotFoundException extends ManganeseException {
    public PageNotFoundException(int pageNumber, String chapterId) {
        super("Page %d in %s does not exist.".formatted(pageNumber, chapterId));
    }
}
