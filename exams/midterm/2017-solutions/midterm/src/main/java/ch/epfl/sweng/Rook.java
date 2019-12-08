package ch.epfl.sweng;

/**
 * Class implementing the behaviour of the rook piece in chess.
 *
 * Rook can move any amount of steps along one of the main axes (either horizontal or vertical).
 */

public class Rook extends Piece {
    public Rook(Position position, Color color) {
        super(position, color);
    }

    public Rook(Rook r) { super(r.getPosition(), r.getColor()); }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        /* here was a bug: || instead of ^. It's also possible to use a "manual" implementation
        of xor like

        ((offset.horizontal == 0) && (offset.vertical != 0)) ||
        ((offset.horizontal != 0) && (offset.vertical == 0))

        */
        return (offset.horizontal != 0) ^ (offset.vertical != 0);
    }

    @Override
    public Rook copy() {
        return new Rook(this);
    }
}
