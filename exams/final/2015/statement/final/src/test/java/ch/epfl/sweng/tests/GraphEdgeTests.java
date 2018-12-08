package ch.epfl.sweng.tests;

import ch.epfl.sweng.GraphEdge;
import ch.epfl.sweng.GraphNode;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class GraphEdgeTests {


    @Test
    public void setSourceDestination(){
        GraphNode<String> currNode = new GraphNode<>("Source");
        GraphNode<String> destNode = new GraphNode<>("Dest");

        GraphEdge<String> graphEdge = new GraphEdge<>(currNode, destNode);

        assertEquals("Source", graphEdge.getSource().getData());
        assertEquals("Dest", graphEdge.getDestination().getData());
    }
}
