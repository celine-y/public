package ch.epfl.sweng;

import java.util.ArrayList;
import java.util.Iterator;
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
        GraphEdgeIterator<D> graphEdgeIterator = root.getForwardEdges();

        while (graphEdgeIterator.hasNext()) {
            GraphEdge<D> currE = graphEdgeIterator.next();
            if (currE.getDestination().getData().equals(data)) {
                return currE.getDestination();
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
        GraphEdgeIterator<D> graphEdgeIterator = root.getForwardEdges();
        List<GraphNode<D>> nodes = new ArrayList<>();

        while (graphEdgeIterator.hasNext()){
            GraphEdge<D> edge = graphEdgeIterator.next();
            nodes.add(edge.getDestination());
        }

        return nodes;
    }
}
