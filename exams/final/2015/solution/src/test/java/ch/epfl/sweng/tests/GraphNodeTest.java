package ch.epfl.sweng.tests;

import ch.epfl.sweng.GraphNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphNodeTest {
    @Test
    public void setDataTest() {
        GraphNode<String> node = new GraphNode<>("foo");
        node.setData("bar");
        assertEquals(node.getData(), "bar");
    }

    @Test
    public void removeSuccessorTest() {
        GraphNode<String> node = new GraphNode<>("foo");
        GraphNode<String> succNode = new GraphNode<>("bar");
        node.addSuccessor(succNode);
        // Test that succNode was added as successor of node.
        // Works because node has only one successor: succNode.
        node.getForwardEdges().forEachRemaining(e -> assertTrue(e.getDestination().equals(succNode)));

        node.removeSuccessor(succNode);
        // Test that succNode was removed from successors of node.
        node.getForwardEdges().forEachRemaining(e -> assertTrue(!e.getDestination().equals(succNode)));

    }
}
