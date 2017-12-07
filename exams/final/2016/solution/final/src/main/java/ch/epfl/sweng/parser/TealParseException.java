package ch.epfl.sweng.parser;

/**
 * Thrown by a Teal parser when an error occurs.
 */
public final class TealParseException extends Exception {
    /**
     * Initializes a new TealParseException with the given message.
     *
     * @param message The error message.
     */
    public TealParseException(final String message) {
        super(message);
    }
}
