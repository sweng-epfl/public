package ch.epfl.sweng.tests;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Knight;
import ch.epfl.sweng.Position;

import static org.junit.Assert.assertEquals;

public class KnightTests extends PieceTests<Knight> {
    @Override
    public void setUp() {
        piece = new Knight(position, Color.BLACK);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        for (int distHori : new int[]{-2, -1, 1, 2}) {
            for (int distVert : new int[]{-2, -1, 1, 2}) {
                if (Math.abs(distHori) != Math.abs(distVert)) {
                    Knight k = new Knight(position, Color.WHITE);
                    int row  = position.getVertical() + distHori;
                    char col = (char) (position.getHorizontal() + distVert);

                    Position destination = Position.positionIfLegal(col, row);
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
        piece.moveTo('d', 6);
    }
}
