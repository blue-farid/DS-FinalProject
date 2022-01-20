package model.graph;

import java.util.*;

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
        this.adjacencyListMap.put(first, firstLs);
        this.adjacencyListMap.put(second, secondLs);
        return res;
    }

    public String toString() {
        String res = "";
        Iterator<Node> itr = this.adjacencyListMap.keySet().iterator();
        while (itr.hasNext()) {
            Node node = itr.next();
            List<Edge> edges = this.adjacencyListMap.get(node);
            res = res.concat(node.toString());
            res = res.concat(" edges: ");
            for (int i = 0; i < edges.size(); i++) {
                res = res.concat(edges.get(i).toString(node).concat(", "));
            }
            res = res.concat("\n");
        }
        return res;
    }

    public int getNodesCount() {
        return nodesCount;
    }

    public int getEdgesCount() {
        return edgesCount;
    }
}
