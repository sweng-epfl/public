package ch.epfl.sweng;

/**
 * Created by jatentaki on 03.11.17.
 */

public class Superpiece extends Piece {
    private Piece underlying;

    public Superpiece(Superpiece p) {
        super(p.getPosition(), p.getColor());
        /* care taken to deep copy underlying. Otherwise we could create several superpieces
           which alias the same decorated piece */
        underlying = p.underlying.copy();
    }

    /* we didn't say anything about the constructors you were supposed to implement. This is just a
       sample, valid solution */
    public Superpiece(Position position, Color color, PieceType promotedTo) {
        super(position, color);

        switch (promotedTo) {
            case KNIGHT:
                underlying = new Knight(position, color);
                break;
            case ROOK:
                underlying = new Rook(position, color);
                break;
            case BISHOP:
                underlying = new Bishop(position, color);
                break;
            case QUEEN:
                underlying = new Queen(position, color);
                break;
            default:
                throw new IllegalArgumentException("wrong promotion specification (" +
                                                   promotedTo.toString() + ")");
        }
    }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        return underlying.isPieceMovementValid(offset);
    }

    @Override
    public Superpiece copy() {
        return new Superpiece(this);
    }

    public void moveTo(Position firstMove, Position secondMove)
         throws InvalidMoveException, InvalidPositionException
    {
        // create a guinea pig to check if the moves are legal
        Piece underlyingCopy = underlying.copy();

        underlyingCopy.moveTo(firstMove);
        underlyingCopy.moveTo(secondMove);

        // no exceptions thrown: we can update our state
        this.setPosition(secondMove);
        underlying = underlyingCopy;
    }

    public void moveTo(char firstColumn, int firstRow, char secondColumn, int secondRow)
         throws InvalidMoveException, InvalidPositionException
    {
        // we will implement this method in terms of `moveTo(Position, Position)`
        Position firstMove = Position.positionIfLegal(firstColumn, firstRow);
        Position secondMove = Position.positionIfLegal(secondColumn, secondRow);
        moveTo(firstMove, secondMove);
    }

    /* since we add a new field (underlying) to the base class, we also have to reflect that in
       the equals() method. Otherwise we would get that a super-knight equals a super-rook, given
       they are at the same position */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Superpiece that = (Superpiece) o;

        return underlying.equals(that.underlying);

    }
}
