package ch.epfl.sweng.tests;

import ch.epfl.sweng.GraphNode;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphNodeTests {
    private GraphNode<String> graphNode;

    @Before
    public void initialize(){
        graphNode = new GraphNode<>("Current");
    }

    @Test
    public void setData(){
        graphNode.setData("New Name");

        assertEquals("New Name", graphNode.getData());
    }

    @Test
    public void removeSuccessor(){
        GraphNode<String> succ = new GraphNode<>("Successor");
        graphNode.addSuccessor(succ);
//        Test that succ was added as successor
        graphNode.getForwardEdges().forEachRemaining(e -> assertEquals(succ, e.getDestination()));
//        Test that succ was removed as successor
        graphNode.removeSuccessor(succ);
        graphNode.getForwardEdges().forEachRemaining(e -> assertTrue(!e.getDestination().equals(succ)));
    }
}
