package ch.epfl.sweng.tests;

import ch.epfl.sweng.Bishop;
import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Position;

import static org.junit.Assert.assertEquals;

public class BishopTests extends PieceTests<Bishop> {
    @Override
    public void setUp() {
        piece = new Bishop(position, Color.BLACK);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        for (int distance : new int[]{1, 2, 3}) {
            for (int horizontalDirection : new int[]{1, -1}) {
                for (int verticalDirection : new int[]{1, -1}) {
                    Bishop b = new Bishop(position, Color.WHITE);
                    int row  = position.getVertical() + distance * horizontalDirection;
                    char col = (char) (position.getHorizontal() + distance * verticalDirection);

                    Position destination = Position.positionIfLegal(col, row);

                    // can we move forward?
                    b.moveTo(col, row);
                    assertEquals(b.getPosition(), destination);

                    // can we move back?
                    b.moveTo('d', 4);
                    assertEquals(b.getPosition(), position);
                }
            }
        }
    }

    @Override
    public void testIllegal() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo('d', 5);
    }
}