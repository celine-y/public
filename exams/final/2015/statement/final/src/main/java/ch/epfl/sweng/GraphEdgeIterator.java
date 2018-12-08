package ch.epfl.sweng;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class GraphEdgeIterator<D> implements Iterator<GraphEdge<D>> {
    private final GraphNode<D> currNode;
    private final List<GraphNode<D>> nodes;
    private int position;

    public GraphEdgeIterator(GraphNode<D> currNode, List<GraphNode<D>> nodes){
        this.currNode = currNode;
        this.nodes = nodes;
        position = 0;
    }

    @Override
    public boolean hasNext() {
        if (position < nodes.size()){
            return true;
        }
        return false;
    }

    @Override
    public GraphEdge<D> next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        } else {
            return new GraphEdge<>(currNode, nodes.get(position++));
        }
    }
}
