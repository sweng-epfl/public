package ch.epfl.sweng;

/**
 * Created by jatentaki on 03.11.17.
 */

public class Superpiece extends Piece {
    private Piece underlyingPiece;

    public Superpiece(Superpiece p) {
        super(p.getPosition(), p.getColor());
        /* care taken to deep copy underlyingPiece. Otherwise we could create several superpieces
           which alias the same decorated piece */
        underlyingPiece = p.underlyingPiece.copy();
    }

    /* we didn't say anything about the constructors you were supposed to implement. This is just a
       sample, valid solution */
    public Superpiece(Position position, Color color, PieceType promotedTo) {
        super(position, color);

        switch (promotedTo) {
            case KNIGHT:
                underlyingPiece = new Knight(position, color);
                break;
            case ROOK:
                underlyingPiece = new Rook(position, color);
                break;
            case BISHOP:
                underlyingPiece = new Bishop(position, color);
                break;
            case QUEEN:
                underlyingPiece = new Queen(position, color);
                break;
            default:
                throw new IllegalArgumentException("wrong promotion specification");
        }
    }

    @Override
    protected boolean isPieceMovementValid(Position.Offset offset) {
        return underlyingPiece.isPieceMovementValid(offset);
    }

    @Override
    public Superpiece copy() {
        return new Superpiece(this);
    }

    public void moveTo(Position firstMove, Position secondMove)
         throws InvalidMoveException, InvalidPositionException
    {
        // create a guinea pig to check if the moves are legal
        Piece underlyingPieceCopy = underlyingPiece.copy();

        underlyingPieceCopy.moveTo(firstMove);
        underlyingPieceCopy.moveTo(secondMove);

        // no exceptions thrown: we can update our state
        this.setPosition(secondMove);
        underlyingPiece = underlyingPieceCopy;
    }

    public void moveTo(char firstColumn, int firstRow, char secondColumn, int secondRow)
         throws InvalidMoveException, InvalidPositionException
    {
        // we will implement this method in terms of `moveTo(Position, Position)`
        Position firstMove = Position.positionIfLegal(firstColumn, firstRow);
        Position secondMove = Position.positionIfLegal(secondColumn, secondRow);
        moveTo(firstMove, secondMove);
    }

    /* since we add a new field (underlyingPiece) to the base class, we also have to reflect that in
       the equals() method. Otherwise we would get that a super-knight equals a super-rook, given
       they are at the same position */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Superpiece that = (Superpiece) o;

        return underlyingPiece.equals(that.underlyingPiece);

    }
}