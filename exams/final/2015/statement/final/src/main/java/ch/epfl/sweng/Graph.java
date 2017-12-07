package ch.epfl.sweng;

import java.util.ArrayList;
import java.util.List;

public final class Graph<D> {
    /** Implementation detail, not part of the graph. */
    private final GraphNode<D> root;

    /** Creates a new, empty graph. */
    public Graph() {
        root = new GraphNode<>(null);
    }

    /**
     * Returns the node that contains the given data. 
     * If no such node exists, it will be created.
     */
    public GraphNode<D> getNode(D data) {
        for (GraphEdge<D> edge : root.getForwardEdges()) {
            if (edge.getDestination().getData().equals(data)) {
                return edge.getDestination();
            }
        }

        GraphNode<D> newNode = new GraphNode<>(data);
        root.addSuccessor(newNode);
        return newNode;
    }

    /**
     * Lists all nodes in the graph. 
     * The order in which nodes are returned is not specified.
     */
    public List<GraphNode<D>> getAllNodes() {
        List<GraphNode<D>> nodes = new ArrayList<>();

        for (GraphEdge<D> edge : root.getForwardEdges()) {
            nodes.add(edge.getDestination());
        }

        return nodes;
    }
}
