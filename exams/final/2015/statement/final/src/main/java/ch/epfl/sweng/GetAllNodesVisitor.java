package ch.epfl.sweng;

import java.util.ArrayList;
import java.util.List;

public class GetAllNodesVisitor<D> implements IGraphElementVisitor<D> {
    private  List<GraphNode<D>> graphNodeList;

    public GetAllNodesVisitor(){
        graphNodeList = new ArrayList<>();
    }

    @Override
    public void visit(Graph<D> graph) {
        this.visit(graph.getRoot());
//        graph.getRoot().accept(this);
    }

    @Override
    public void visit(GraphEdge<D> edge) {
        graphNodeList.add(edge.getDestination());
    }

    @Override
    public void visit(GraphNode<D> node) {
        for (GraphEdge<D> edge: node.getForwardEdges()) {
            graphNodeList.add(edge.getDestination());
        }
    }

    public List<GraphNode<D>> getGraphNodeList(){
        return this.graphNodeList;
    }
}
