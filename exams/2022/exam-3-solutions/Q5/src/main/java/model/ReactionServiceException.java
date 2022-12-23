package model;

/**
 * Exception thrown for authentication issues.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public final class ReactionServiceException extends RuntimeException {

    /**
     * Creates a new reaction service exception.
     * @param message the message of the exception
     */
    public ReactionServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new reaction service exception.
     * @param message the message of the exception
     * @param cause the cause of the exception
     */
    public ReactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}