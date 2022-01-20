package model.graph;

import java.util.LinkedList;

public class Graph {
    private final int size;
    private final LinkedList<Edge> []adjacencyList;

    public Graph(int size) {
        this.size = size;
        adjacencyList = new LinkedList[size];
        initializeAdjacencyList();
    }

    private void initializeAdjacencyList() {
        for (int i = 0; i < this.size; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public int addEdge(Node first, Node second, int weight) {
        Edge edge = new Edge(first, second, weight);
        this.adjacencyList[first.getData()].addFirst(edge);
        this.adjacencyList[second.getData()].addFirst(edge);
        return 0;
    }
}
