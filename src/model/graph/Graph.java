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
}
