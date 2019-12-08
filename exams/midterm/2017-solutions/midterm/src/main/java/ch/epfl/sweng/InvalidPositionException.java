package ch.epfl.sweng;

/**
 * An exception for when attempting a {@link Position Position} which is invalid, or passing
 * a null instance to some method which expects a valid Position. If the Position
 * is well-formed, but invalid in context of a particular move to be made, throw
 * {@link InvalidMoveException InvalidMoveException} instead
 */

public class InvalidPositionException extends Exception {
    public InvalidPositionException(String cause) { super(cause); }
    public InvalidPositionException() { super(); }
}
