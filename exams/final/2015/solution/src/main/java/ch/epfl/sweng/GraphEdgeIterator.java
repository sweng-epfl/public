package ch.epfl.sweng;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class GraphEdgeIterator<D> implements Iterator<GraphEdge<D>> {

    private final GraphNode<D> currNode;
    private final List<GraphNode<D>> nodes;
    private int index;

    public GraphEdgeIterator(final GraphNode<D> currNode, final List<GraphNode<D>> nodes) {
        this.currNode = currNode;
        this.nodes = nodes;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < nodes.size();
    }

    @Override
    public GraphEdge<D> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return new GraphEdge<>(currNode, nodes.get(index++));
    }
}

