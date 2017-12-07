package ch.epfl.sweng;

public interface IGraphElementVisitor<D> {
    void visit(Graph<D> graph);
    void visit(GraphEdge<D> edge);
    void visit(GraphNode<D> node);
}

