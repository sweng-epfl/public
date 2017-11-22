package ch.epfl.sweng;

/**
 * Class implementing the behaviour of the queen piece in chess.
 *
 * Queen can move any amount of steps in any of the 4 axes: horizontal/vertical/both diagonals.
 */

public class Queen extends Piece {
    public Queen(Position position, Color color) {
        super(position, color);
    }

    public Queen(Queen q) { super(q.getPosition(), q.getColor()); }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        boolean likeRook = (offset.horizontal != 0) ^ (offset.vertical != 0);
        boolean likeBishop = Math.abs(offset.vertical) == Math.abs(offset.horizontal);

        // moves either like a rook, xor a bishop.
        return likeRook ^ likeBishop;
    }

    @Override
    public Queen copy() {
        return new Queen(this);
    }
}
