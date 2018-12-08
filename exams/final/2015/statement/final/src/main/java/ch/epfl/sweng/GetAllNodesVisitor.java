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
        graph.getRoot().accept(this);
    }

    @Override
    public void visit(GraphEdge<D> edge) {
        edge.getDestination().accept(this);
    }

    @Override
    public void visit(GraphNode<D> node) {
        if (graphNodeList.contains(node)){
            return;
        }

        if (node.getData() != null){
            graphNodeList.add(node);
        }

        node.getForwardEdges().forEachRemaining(e -> e.accept(this));
    }

    public List<GraphNode<D>> getGraphNodeList(){
        return this.graphNodeList;
    }
}
