package ch.epfl.sweng;

/**
 * An abstract class containing the common behaviour of all chess pieces. The piece has a fixed
 * {@link Color color} and a mutable position on the board. It can be moved across the board by
 * using the methods {@link #moveTo(char, int)} and {@link #moveTo(Position)}. The rules common for
 * all pieces are that the move take null parameter and that it cannot cause the piece to remain
 * in place (move destination == current position).
 */

public abstract class Piece {
    private final Color color;
    private Position position;

    protected Piece(Position position, Color color) {
        this.color = color;
        this.position = position;
    }

    /**
     * a method overridden by each piece kind to check if a movement is valid. Since the destination
     * is already checked for being within the board and not equal to the piece's current position,
     * only the relative movement, encoded in {@link Position.Offset} needs to be checked.
     * @param offset - the {@link Position.Offset} instance needed to validate the move
     * @return true if the play is valid, false otherwise
     */
    protected abstract boolean isPieceMovementValid(Position.Offset offset);

    // setters and getters
    public Position getPosition() { return position; }

    public Color getColor() { return color; }

    public void setPosition(Position position) { this.position = position; }

    /**
     * A method for moving the piece across the board
     * @param destination {@link Position} the destination of the move
     * @throws InvalidPositionException in case the position is null
     * @throws InvalidMoveException in case the move violates the rules governing the piece
     */
    public void moveTo(Position destination) throws InvalidPositionException, InvalidMoveException {
        if (destination == null) throw new InvalidPositionException("null pointer as destination");

        Position.Offset offset = getPosition().offsetTo(destination);

        if (!offset.isZero())
            throw new InvalidMoveException("A no-op move issued");

        if (!isPieceMovementValid(offset))
            throw new InvalidMoveException();

        position = destination;
    }

    /**
     * As in {@link #moveTo(Position)}), except builds the {@link Position} on the fly, possibly
     * throwing in case it is not within the board
     * @param column an integer between 1 and BOARD_END_VERTICAL
     * @param row an upper/lower case char between 'A' and BOARD_END_HORIZONTAL
     * @throws InvalidPositionException in case the destination is outside the board (violation of
     * the invariants described above)
     * @throws InvalidMoveException in case the move violates the rules governing the piece
     */
    public void moveTo(char column, int row) throws InvalidPositionException,
                                                    InvalidMoveException
    {
        Position destination = Position.positionIfLegal(column, row);

        if (destination != null)
            moveTo(destination);
        else
            throw new InvalidPositionException();

    }

    /**
     * @return a deep copy of the piece
     */
    public abstract Piece copy();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (getColor() != piece.getColor()) return false;
        return getPosition().equals(piece.getPosition());
    }
}
