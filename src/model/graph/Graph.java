package model.graph;

import model.hashUtils.MyHashMap;

import java.util.*;

/**
 * The Graph class
 */
public class Graph {
    private final int nodesCount;
    private final int edgesCount;
    private final HashMap<Node, LinkedList<Edge>> adjacencyListMap;

    /**
     * Instantiates a new Graph.
     *
     * @param nodesCount the nodes count
     * @param edgesCount the edges count
     */
    public Graph(int nodesCount, int edgesCount) {
        this.nodesCount = nodesCount;
        this.edgesCount = edgesCount;
        this.adjacencyListMap = new HashMap<>(edgesCount);
    }


    /**
     * do dijkstra algorithm on Graph
     *
     * @param src the source node
     * @return the results (shortest path distances from source Node to each node) that store on a HashMap
     */
    public MyHashMap<Node, Integer> dijkstra(Node src) {
        Set<Node> nodes = this.adjacencyListMap.keySet();
        if (!nodes.contains(src)) {
            return null;
        }
        MyHashMap<Node, Integer> distances = new MyHashMap<>(nodesCount);
        initDistances(distances);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        src.setCost(0);
        pq.add(src);
        distances.put(src, 0);
        HashSet<Node> settled = new HashSet<>();
        while (!pq.isEmpty() && settled.size() != this.nodesCount) {
            Node minNode = pq.remove();
            if (!settled.contains(minNode)) {
                settled.add(minNode);
                processEdges(minNode, settled, distances, pq);
            }
        }
        return distances;
    }

    /**
     * process Edges of a Node (a part of dijkstra algorithm)
     * @param node the node
     * @param settled settled nodes set
     * @param distances distances HashMap
     * @param pq PriorityQueue of nodes
     */
    private void processEdges(Node node, Set<Node> settled, HashMap<Node, Integer> distances, PriorityQueue<Node> pq) {
        int edgeDst, newDst;
        List<Edge> edges = this.adjacencyListMap.get(node);

        for (Edge edge : edges) {
            Node edgeNode = edge.getEdgeOf(node);
            edgeNode.setCost(edge.getWeight());
            if (!settled.contains(edgeNode)) {
                edgeDst = edgeNode.getCost();
                newDst = distances.get(node) + edgeDst;

                if (newDst < distances.get(edgeNode)) {
                    distances.put(edgeNode, newDst);
                }
                pq.add(edgeNode);
            }
        }
    }

    /**
     * initial distances for each node with MAX value
     * @param distances HashMap
     */
    private void initDistances(HashMap<Node, Integer> distances) {
        Set<Node> keys = this.adjacencyListMap.keySet();
        for(Node node: keys) {
            distances.put(node, Integer.MAX_VALUE);
        }
    }

    /**
     * Add edge to graph
     *
     * @param first  the first node
     * @param second the second node
     * @param weight the weight of edge
     * @return the result
     */
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

    @Override
    public String toString() {
        String res = "";
        for (Node node : this.adjacencyListMap.keySet()) {
            List<Edge> edges = this.adjacencyListMap.get(node);
            res = res.concat(node.toString());
            res = res.concat(" edges: ");
            for (Edge edge : edges) {
                res = res.concat(edge.toString(node).concat(", "));
            }
            res = res.concat("\n");
        }
        return res;
    }

    /**
     * Gets nodes count.
     *
     * @return the nodes count
     */
    public int getNodesCount() {
        return nodesCount;
    }

    /**
     * Gets edges count.
     *
     * @return the edges count
     */
    public int getEdgesCount() {
        return edgesCount;
    }

    /**
     * Gets node.
     *
     * @param nodeNum the node data
     * @return the node or null if node not found
     */
    public Node getNode(int nodeNum) {
        Set<Node> keys = this.adjacencyListMap.keySet();
        for (Node node : keys) {
            if (node.getData() == nodeNum) {
                return node;
            }
        }
        return null;
    }

    /**
     * Gets nodes.
     *
     * @return the nodes set
     */
    public Set<Node> getNodes() {
        return this.adjacencyListMap.keySet();
    }

    private void DFSUtil(Node node, HashMap<Node, Boolean> visitedMap) {
        System.out.print(node + ", ");
        visitedMap.put(node, true);
        List<Edge> edges = this.adjacencyListMap.get(node);
        for (Edge edge: edges) {
            Node edgeNode = edge.getEdgeOf(node);
            if (visitedMap.get(edgeNode) == null) {
                DFSUtil(edgeNode, visitedMap);
            }
        }
    }
    public void DFS() {
        DFS(getNodes().iterator().next());
    }

    public void DFS(Node src) {
        if (src == null) {
            return;
        }
        HashMap<Node, Boolean> visitedMap = new HashMap<>();
        System.out.print("[");
        DFSUtil(src, visitedMap);
        System.out.println("\b\b]");
    }
}
