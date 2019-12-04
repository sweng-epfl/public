package ch.epfl.sweng.tests;

import ch.epfl.sweng.GraphEdge;
import ch.epfl.sweng.GraphNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphEdgeTest {

    @Test
    public void srcDestTest() {
        GraphNode<String> src = new GraphNode<>("src");
        GraphNode<String> dest = new GraphNode<>("dest");
        GraphEdge<String> edge = new GraphEdge<>(src, dest);

        assertEquals(edge.getSource(), src);
        assertEquals(edge.getDestination(), dest);
    }
}
