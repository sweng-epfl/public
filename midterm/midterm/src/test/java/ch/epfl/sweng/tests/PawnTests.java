package ch.epfl.sweng.tests;

import org.junit.Test;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.Pawn;
import ch.epfl.sweng.PieceType;
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

    // ************  following tests relate to the promote method   ********************************

    @Test
    public void testLegalPromotion() throws InvalidMoveException {
        Pawn white = new Pawn(Position.positionIfLegal('a', 8), Color.WHITE);
        Pawn black = new Pawn(Position.positionIfLegal('a', 1), Color.BLACK);
        white.promote(PieceType.KNIGHT);
        black.promote(PieceType.BISHOP);
    }

    @Test(expected = InvalidMoveException.class)
    public void testIllegalWhitePromotion() throws InvalidMoveException {
        Pawn white = new Pawn(Position.positionIfLegal('a', 1), Color.WHITE);
        white.promote(PieceType.KNIGHT);
    }

    @Test(expected = InvalidMoveException.class)
    public void testIllegalBlackPromotion() throws InvalidMoveException {
        Pawn black = new Pawn(Position.positionIfLegal('a', 8), Color.BLACK);
        black.promote(PieceType.KNIGHT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentPromotion() throws InvalidMoveException {
        Pawn white = new Pawn(Position.positionIfLegal('a', 8), Color.WHITE);
        white.promote(PieceType.KING);
    }
}
