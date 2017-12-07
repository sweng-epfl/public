package ch.epfl.sweng;

import java.util.ArrayList;
import java.util.List;

public final class GraphNode<D> {
    private final List<GraphNode<D>> successors;
    private D data;

    /** Creates a new node with the given data. */
    public GraphNode(D data) {
        successors = new ArrayList<>();
        this.data = data;
    }

    /** Gets the node's data. */
    public D getData() {
        return data;
    }

    /** Sets the node's data. */
    public void setData(D data) {
        this.data = data;
    }

    /** Adds the given node as a successor to the node. */
    public void addSuccessor(GraphNode<D> successor) {
        successors.add(successor);
    }

    /** Removes the given node from the node's successors. */
    public void removeSuccessor(GraphNode<D> successor) {
        successors.remove(successor);
    }

    /**
     * Gets all forward edges starting from this node.
     * The order in which they are returned is not specified.
     */
    public List<GraphEdge<D>> getForwardEdges() {
        List<GraphEdge<D>> edges = new ArrayList<>();
        
        for (GraphNode<D> node : successors) {
            edges.add(new GraphEdge<>(this, node));
        }
        
        return edges;
    }
}
