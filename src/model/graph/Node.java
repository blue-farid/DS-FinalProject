package model.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

/**
 * The Node class
 */
public class Node implements Comparable<Node>, Comparator<Node> {
    private final int data;
    private int cost;
    private HashMap<Node, Integer> dijkstraResultsMap;
    private float fairScore;

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Instantiates a new Node.
     *
     * @param data the data
     */
    public Node(int data) {
        this.data = data;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public int getData() {
        return data;
    }

    @Override
    public int compare(Node o1, Node o2) {
        return o1.cost - o2.cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return data == node.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return data + "";
    }

    @Override
    public int compareTo(Node o) {
        return this.cost - o.cost;
    }

    /**
     * Sets dijkstra results map.
     *
     * @param dijkstraResultsMap the dijkstra results map
     */
    public void setDijkstraResultsMap(HashMap<Node, Integer> dijkstraResultsMap) {
        this.dijkstraResultsMap = dijkstraResultsMap;
    }

    /**
     * Gets dijkstra results map.
     *
     * @return the dijkstra results map
     */
    public HashMap<Node, Integer> getDijkstraResultsMap() {
        return dijkstraResultsMap;
    }

    /**
     * Sets fair score.
     *
     * @param fairScore the fair score
     */
    public void setFairScore(float fairScore) {
        this.fairScore = fairScore;
    }

    /**
     * Gets fair score.
     *
     * @return the fair score
     */
    public float getFairScore() {
        return fairScore;
    }
}
