package ch.epfl.sweng;

public final class GraphEdge<D> {
    private final GraphNode<D> source;
    private final GraphNode<D> destination;

    /** Creates a new directed edge linking the given nodes. */
    public GraphEdge(GraphNode<D> source, GraphNode<D> destination) {
        this.source = source;
        this.destination = destination;
    }

    /** Gets the source node of this edge. */
    public GraphNode<D> getSource() {
        return source;
    }

    /** Gets the destination node of this edge. */
    public GraphNode<D> getDestination() {
        return destination;
    }
}
