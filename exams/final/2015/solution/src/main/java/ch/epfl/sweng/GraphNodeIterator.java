package ch.epfl.sweng;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GraphNodeIterator<D> implements Iterator<GraphNode<D>> {

    private final Iterator<GraphEdge<D>> edgeIterator;

    public GraphNodeIterator(final GraphNode<D> root) {
        edgeIterator = root.getForwardEdges();
    }

    @Override
    public boolean hasNext() {
        return edgeIterator.hasNext();
    }

    @Override
    public GraphNode<D> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return edgeIterator.next().getDestination();
    }
}
