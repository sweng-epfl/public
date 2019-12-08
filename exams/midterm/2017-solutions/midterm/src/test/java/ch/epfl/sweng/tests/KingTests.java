package ch.epfl.sweng.tests;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.King;
import ch.epfl.sweng.Position;

import static org.junit.Assert.assertEquals;

public class KingTests extends PieceTests<King> {
    @Override
    public void setUp() {
        piece = new King(position, Color.BLACK);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        for (int row : new int[]{3, 4, 5}) {
            for (char col : new char[]{'C', 'D', 'E'}) {
                Position destination = Position.positionIfLegal(col, row);
                King k = new King(position, Color.WHITE);

                if (!destination.equals(position)) {
                    // can we move forward?
                    k.moveTo(col, row);
                    assertEquals(k.getPosition(), destination);

                    // can we move back?
                    k.moveTo('d', 4);
                    assertEquals(k.getPosition(), position);
                }
            }
        }
    }

    @Override
    public void testIllegal() throws InvalidMoveException, InvalidPositionException {
        King k = new King(position, Color.BLACK);
        k.moveTo('d', 6);
    }
}
