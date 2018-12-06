package ch.epfl.sweng.tests;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Position;
import ch.epfl.sweng.Queen;

import static org.junit.Assert.assertEquals;

// we extend PieceTests<Queen> to inherit test cases for move outside the board and several other
// generic instances
public class QueenTests extends PieceTests<Queen> {
    @Override
    public void setUp() {
        piece = new Queen(position, Color.BLACK);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        // how far are we going to travel. Careful not to move outside the board
        for (int offset : new int[]{1, 2, 3}) {

            // we move forward, stay in place, or backward along rows
            for (int rowDirection : new int[]{1, 0, -1}) {

                // we move forward, stay in place, or backward along columns
                for (int colDirection: new int[]{1, 0, -1}) {

                    Queen q  = new Queen(position, Color.WHITE);
                    int row  = position.getVertical() + offset * colDirection;
                    char col = (char) (position.getHorizontal() + offset * rowDirection);

                    Position destination = Position.positionIfLegal(col, row);

                    /* if colDirection == rowDirection == 0 we would make no move and that's a
                       corner case illegal move. Skip it. */
                    if (!destination.equals(position)) {
                        // can we move forward?
                        q.moveTo(col, row);
                        assertEquals(q.getPosition(), destination);

                        // can we move back?
                        q.moveTo('d', 4);
                        assertEquals(q.getPosition(), position);
                    }
                }
            }
        }
    }


    @Override
    public void testIllegal() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo('e', 6);
    }

}
