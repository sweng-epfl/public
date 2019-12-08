package ch.epfl.sweng.tests;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Piece;
import ch.epfl.sweng.Position;

import static org.junit.Assert.assertEquals;

/**
 * This class contains the common behaviour of a test for any chess Piece:
 * - It is impossible to issue a no-move move command
 * - It is impossible to move outside the board
 * - Passing a null pointer as move destination fails as expected
 *
 * Classes testing particular subclasses of Piece should extend the class and implement
 * tests for their specific behaviour.
 *
 * NOTE: in setUp() you have to initialize the `piece` variable with the piece you are testing.
 */
public abstract class PieceTests<P extends Piece> {
    /** the piece being tested. You have to set it up in {@link #setUp) **/
    protected P piece = null;

    /** the position where you want your test piece to be in **/
    protected Position position = DEFAULT_POSITION;

    /** the default test position for your piece **/
    protected static final Position DEFAULT_POSITION = Position.positionIfLegal('d', 4);

    @Test(expected = InvalidMoveException.class)
    public void testNoMove() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo(position);
    }

    @Test(expected = InvalidPositionException.class)
    public void testMoveOutsideOfBoard() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo(';', -1);
    }

    @Test(expected = InvalidPositionException.class)
    public void testMoveNull() throws InvalidPositionException, InvalidMoveException {
        piece.moveTo(null);
    }
    
    /**
     * Has to initialize the `piece` field of this template
     */
    @Before
    public abstract void setUp();

    /**
     * Tests if legal moves with the piece do _not_ fail. Therefore, should not throw
     * @throws InvalidMoveException
     * @throws InvalidPositionException
     */
    @Test
    public abstract void testLegal() throws InvalidMoveException, InvalidPositionException;

    /**
     * Tests if invalid moves with the class throw.
     * where `P` is the argument of this template
     * @throws InvalidMoveException
     * @throws InvalidPositionException
     */
    @Test(expected = InvalidMoveException.class)
    public abstract void testIllegal() throws InvalidMoveException, InvalidPositionException;
}
