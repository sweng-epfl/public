package providers.manganese.exceptions;

/**
 * An exception which is thrown when requesting a chapter that does not exist.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class ChapterNotFoundException extends ManganeseException {
    public ChapterNotFoundException(String chapterId) {
        super("Chapter with id = %s does not exist".formatted(chapterId));
    }
}
