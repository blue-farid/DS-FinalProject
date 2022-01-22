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

    public int[] dijkstra(Node src) {
        Set<Node> nodes = this.adjacencyListMap.keySet();
        if (!nodes.contains(src)) {
            return null;
        }
        int[] distances = new int[this.nodesCount];
        initDistances(distances);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        src.setCost(0);
        pq.add(src);
        distances[src.getData() % this.nodesCount] = 0;
        HashSet<Node> dones = new HashSet<>();
        while (!pq.isEmpty() && dones.size() != this.nodesCount) {
            Node minNode = pq.remove();
            if (!dones.contains(minNode)) {
                dones.add(minNode);
                processEdges(src, dones, distances, pq);
            }
        }
        return distances;
    }

    private int processEdges(Node node, Set<Node> dones, int[] distances, PriorityQueue<Node> pq) {
        int edgeDst, newDst;
        List<Edge> edges = this.adjacencyListMap.get(node);

        for (Edge edge : edges) {
            Node edgeNode = edge.getEdgeOf(node);
            edgeNode.setCost(edge.getWeight());
            if (!dones.contains(edgeNode)) {
                edgeDst = edgeNode.getCost();
                newDst = distances[node.getData() % this.nodesCount] + edgeDst;

                if (newDst < distances[edgeNode.getData() % this.nodesCount]) {
                    distances[edgeNode.getData() % this.nodesCount] = newDst;
                }
                pq.add(edgeNode);
            }
        }
        return 0;
    }
    private int initDistances(int[] distances) {
        Arrays.fill(distances, Integer.MAX_VALUE);
        return 0;
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

    public Node getNode(int nodeNum) {
        Set<Node> keys = this.adjacencyListMap.keySet();
        Iterator<Node> itr = keys.iterator();
        while (itr.hasNext()) {
            Node node = itr.next();
            if (node.getData() == nodeNum) {
                return node;
            }
        }
        return null;
    }

    public Set<Node> getNodes() {
        return this.adjacencyListMap.keySet();
    }
}
