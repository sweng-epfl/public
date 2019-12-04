package ch.epfl.sweng;

import java.util.List;

public final class Graph<D> implements IGraphElement<D> {
    /** Implementation detail, not part of the graph. */
    private final GraphNode<D> root;

    /** Creates a new, empty graph. */
    public Graph() {
        root = new GraphNode<>(null);
    }


    /**
     * Return the root of the gaph. Needed in GetAllNodesVisitor.
     */
    public GraphNode<D> getRoot() {
        return root;
    }

    /**
     * Returns the node that contains the given data. 
     * If no such node exists, it will be created.
     */
    public GraphNode<D> getNode(D data) {
        GraphEdgeIterator<D> iterator = root.getForwardEdges();

        while (iterator.hasNext()) {
            GraphNode<D> next = iterator.next().getDestination();
            if (next.getData().equals(data)) {
                return next;
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
        GetAllNodesVisitor<D> visitor = new GetAllNodesVisitor<>();
        return visitor.getAllNodes(this);
    }

    @Override
    public void accept(IGraphElementVisitor<D> visitor) {
        visitor.visit(this);
    }
}
