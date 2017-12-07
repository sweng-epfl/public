package ch.epfl.sweng;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetAllNodesVisitor<D> implements IGraphElementVisitor<D> {

    private final Set<GraphNode<D>> nodes;

    public GetAllNodesVisitor() {
        nodes = new HashSet<>();
    }

    @Override
    public void visit(Graph<D> graph) {
        graph.getRoot().accept(this);
    }

    @Override
    public void visit(GraphEdge<D> edge) {
        edge.getDestination().accept(this);
    }

    @Override
    public void visit(GraphNode<D> node) {
        if (nodes.contains(node))
            return;

        if (node.getData() != null) // Does not add the root
            nodes.add(node);

        node.getForwardEdges().forEachRemaining(e -> e.accept(this));
    }

    public List<GraphNode<D>> getAllNodes(Graph<D> graph) {
        graph.accept(this);
        return new ArrayList<>(nodes);
    }
}
