package ch.epfl.sweng.tests;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Position;
import ch.epfl.sweng.Rook;

import static org.junit.Assert.assertEquals;

public class RookTests extends PieceTests<Rook> {
    @Override
    public void setUp() {
        piece = new Rook(position, Color.BLACK);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        for (int distance : new int[]{1, 2, 3}) {
            for (int direction : new int[]{1, -1}) {
                Rook r = new Rook(position, Color.WHITE);
                int row  = position.getVertical() + distance * direction;
                char col = (char) (position.getHorizontal() + distance * direction);

                Position destHori = Position.positionIfLegal(col, position.getVertical());

                // can we move forward horizontally?
                r.moveTo(col, position.getVertical());
                assertEquals(r.getPosition(), destHori);

                // can we move back?
                r.moveTo('d', 4);
                assertEquals(r.getPosition(), position);

                Position destVert = Position.positionIfLegal(position.getHorizontal(), row);

                // can we move forward vertically?
                r.moveTo(position.getHorizontal(), row);
                assertEquals(r.getPosition(), destVert);

                // can we move back?
                r.moveTo('d', 4);
                assertEquals(r.getPosition(), position);
            }
        }
    }

    @Override
    public void testIllegal() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo('c', 5);
    }
}