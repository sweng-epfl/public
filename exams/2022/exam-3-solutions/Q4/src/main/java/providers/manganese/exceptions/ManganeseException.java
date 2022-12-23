package providers.manganese.exceptions;

import providers.manganese.ManganeseApi;

/**
 * Abstract type for any exception thrown by the {@link ManganeseApi}.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public abstract class ManganeseException extends Exception {
    public ManganeseException(String errorMessage) {
        super(errorMessage);
    }
}
