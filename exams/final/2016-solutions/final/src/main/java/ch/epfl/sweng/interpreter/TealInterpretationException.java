package ch.epfl.sweng.interpreter;

/**
 * Thrown by a Teal interpreter when a problem occurs during interpretation.
 */
public final class TealInterpretationException extends RuntimeException {
    /**
     * Initializes a new TealInterpretationException with the given message.
     *
     * @param message The error message.
     */
    public TealInterpretationException(final String message) {
        super(message);
    }
}