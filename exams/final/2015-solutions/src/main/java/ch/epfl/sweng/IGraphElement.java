package ch.epfl.sweng;

public interface IGraphElement<D> {
    void accept(IGraphElementVisitor<D> visitor);
}
