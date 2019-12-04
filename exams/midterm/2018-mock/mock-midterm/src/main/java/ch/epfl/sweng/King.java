package ch.epfl.sweng;

/**
 * Class implementing the behaviour of the king piece in chess.
 *
 * King can move +-1 step in any direction (vertically/horizontally/both diagonals).
 */

public class King extends Piece {
    public King(Position position, Color color) { super(position, color); }

    public King(King k) { super(k.getPosition(), k.getColor()); }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        return (Math.abs(offset.horizontal) <= 1) && (Math.abs(offset.vertical) <= 1);
    }

    @Override
    public King copy() {
        return new King(this);
    }
}
