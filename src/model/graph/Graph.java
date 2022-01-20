package model.graph;

import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
    private final int nodesCount;
    private final int edgesCount;
    private final HashMap<Node, LinkedList<Edge>> adjacencyListMap;

    public Graph(int nodesCount, int edgesCount) {
        this.nodesCount = nodesCount;
        this.edgesCount = edgesCount;
        this.adjacencyListMap = new HashMap<>(edgesCount);
    }

    public boolean addEdge(Node first, Node second, int weight) {
        boolean res = true;
        Edge edge = new Edge(first, second, weight);
        LinkedList<Edge> firstLs = this.adjacencyListMap.get(first);
        LinkedList<Edge> secondLs = this.adjacencyListMap.get(second);
        if (firstLs == null) {
            firstLs = new LinkedList<>();
            res = false;
        }
        if (secondLs == null) {
            secondLs = new LinkedList<>();
            res = false;
        }
        firstLs.addFirst(edge);
        secondLs.addFirst(edge);
        return res;
    }
}
