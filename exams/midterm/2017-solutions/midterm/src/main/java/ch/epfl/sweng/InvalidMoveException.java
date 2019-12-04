package ch.epfl.sweng;

/**
 * An exception for when an invalid move is performed. Note that this means a move to a position
 * which violates the rules for the particular piece. If the position itself is invalid, see
 * {@link InvalidPositionException InvalidPositionException}.
 *
 * This exception is meant to be subclassed to implement class-specific variations, signalling
 * erroneous movements of specific pieces.
 */

public class InvalidMoveException extends Exception {
    public InvalidMoveException(String cause) {
        super(cause);
    }
    public InvalidMoveException() { super(); }
}
