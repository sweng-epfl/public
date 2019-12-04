package ch.epfl.sweng;

/**
 * Class implementing the behaviour of the knight piece in chess.
 *
 * Knight moves +-1 position in one direction (horizontally/vertically), and +-2 positions in the
 * other. As a sanity check: this should amount to 8 total possible moves
 */

public class Knight extends Piece {
    public Knight(Position position, Color color) {
        super(position, color);
    }

    public Knight(Knight k) { super(k.getPosition(), k.getColor()); }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        int horizontalAbs = Math.abs(offset.horizontal);
        int verticalAbs   = Math.abs(offset.vertical);

        boolean onceByOne = (horizontalAbs == 1) ^ (verticalAbs == 1);
        boolean onceByTwo = (horizontalAbs == 2) ^ (verticalAbs == 2);

        return onceByOne && onceByTwo;
    }

    @Override
    public Knight copy() {
        return new Knight(this);
    }
}
