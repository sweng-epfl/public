package ch.epfl.sweng.tests;

import org.junit.Test;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Pawn;
import ch.epfl.sweng.Position;

import static org.junit.Assert.assertEquals;

public class PawnTests extends PieceTests<Pawn> {
    private Pawn pieceWhite;

    @Override
    public void setUp() {
        piece = new Pawn(position, Color.BLACK);
        pieceWhite = new Pawn(position, Color.WHITE);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        pieceWhite.moveTo('d', 5);
        assertEquals(pieceWhite.getPosition(), Position.positionIfLegal('d', 5));

        piece.moveTo('d', 3);
        assertEquals(piece.getPosition(), Position.positionIfLegal('d', 3));

        // can't move back: that's how pawns work :)
    }

    // testing the case of a black pawn moving upwards
    @Override
    public void testIllegal() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo('d', 5);
    }

    @Test(expected = InvalidMoveException.class)
    public void testMoveBackwards() throws InvalidMoveException, InvalidPositionException {
        pieceWhite.moveTo('d', 3);
    }
}
