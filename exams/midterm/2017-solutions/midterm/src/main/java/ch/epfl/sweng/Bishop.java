package ch.epfl.sweng;

/**
 * Class implementing the behaviour of the bishop piece in chess.
 *
 * A bishop can only move diagonally.
 */

public class Bishop extends Piece {
    public Bishop(Position position, Color color) { super(position, color); }

    private Bishop(Bishop b) { super(b.getPosition(), b.getColor()); }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        return (Math.abs(offset.vertical) == Math.abs(offset.horizontal));
    }

    @Override
    public Bishop copy() {
        return new Bishop(this);
    }
}
