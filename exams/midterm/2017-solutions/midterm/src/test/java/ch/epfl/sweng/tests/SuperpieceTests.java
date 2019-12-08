package ch.epfl.sweng.tests;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.Color;
import ch.epfl.sweng.InvalidMoveException;
import ch.epfl.sweng.InvalidPositionException;
import ch.epfl.sweng.PieceType;
import ch.epfl.sweng.Position;
import ch.epfl.sweng.Superpiece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * We'll be testing on the case of a promoted knight
 */

public class SuperpieceTests extends PieceTests<Superpiece> {
    @Before @Override
    public void setUp() {
        piece = new Superpiece(position, Color.WHITE, PieceType.KNIGHT);
    }

    @Override
    public void testLegal() throws InvalidMoveException, InvalidPositionException {
        Position firstHop = Position.positionIfLegal(
            (char) (PieceTests.DEFAULT_POSITION.getHorizontal() + 2),
            PieceTests.DEFAULT_POSITION.getVertical() + 1
        );

        piece.moveTo(
            (char) (PieceTests.DEFAULT_POSITION.getHorizontal() + 2),
            PieceTests.DEFAULT_POSITION.getVertical() + 1
        );

        assertEquals(piece.getPosition(), firstHop);

        Position secondHop = Position.positionIfLegal(
                (char) (firstHop.getHorizontal() - 2),
                firstHop.getVertical() - 1
        );

        piece.moveTo(secondHop);

        assertEquals(piece.getPosition(), secondHop);

        // since normal movement works, let's doublemove back

        piece.moveTo('f', 5, 'd', 4);

        assertEquals(piece.getPosition(), PieceTests.DEFAULT_POSITION);

        // and forward again, with the other overload

        Position interm = Position.positionIfLegal('f', 5);
        Position final_ = Position.positionIfLegal('h', 4);

        piece.moveTo(interm, final_);

        assertEquals(piece.getPosition(), final_);
    }

    @Override
    public void testIllegal() throws InvalidMoveException, InvalidPositionException {
        piece.moveTo('d', 5);
    }

    @Test
    public void testIllegalDoubleMovePosition() {
        Position position1 = Position.positionIfLegal('d', 5);
        Position position2 = Position.positionIfLegal('f', 5);

        try {
            piece.moveTo(position1, position2);
        } catch (InvalidMoveException e) {
            // that was expected
        } catch (InvalidPositionException e) {
            // that was not
            fail();
        }

        // exception guarantee on first step
        assertEquals(piece.getPosition(), PieceTests.DEFAULT_POSITION);

        try {
            piece.moveTo(position2, position2);
        } catch (InvalidMoveException e) {
            // that was expected
        } catch (InvalidPositionException e) {
            // that was not
            fail();
        }

        // exception guarantee on second step
        assertEquals(piece.getPosition(), PieceTests.DEFAULT_POSITION);
    }

    @Test
    public void testIllegalDoubleMoveCoords() {
        // repeat the previous test case, this time with explicit coordinates

        try {
            piece.moveTo('d', 5, 'f', 5);
        } catch (InvalidMoveException e) {
            // that was expected
        } catch (InvalidPositionException e) {
            // that was not
            fail();
        }

        // exception guarantee on first step
        assertEquals(piece.getPosition(), PieceTests.DEFAULT_POSITION);

        try {
            piece.moveTo('f', 5, 'f', 5);
        } catch (InvalidMoveException e) {
            // that was expected
        } catch (InvalidPositionException e) {
            // that was not
            fail();
        }

        // exception guarantee on second step
        assertEquals(piece.getPosition(), PieceTests.DEFAULT_POSITION);
    }

    /* the following tests use the public constructor of Superpiece. Depending on your design,
       they may instead reside in PawnTests, for example */
    @Test
    public void testLegalPromotionKeyword() {
        new Superpiece(position, Color.BLACK, PieceType.KNIGHT);
        new Superpiece(position, Color.BLACK, PieceType.ROOK);
        new Superpiece(position, Color.BLACK, PieceType.BISHOP);
        new Superpiece(position, Color.BLACK, PieceType.QUEEN);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPromotionKeyword() {
        new Superpiece(position, Color.BLACK, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPromotionKing() {
        new Superpiece(position, Color.BLACK, PieceType.KING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPromotionPawn() {
        new Superpiece(position, Color.BLACK, PieceType.PAWN);
    }
}