package model.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public class Node implements Comparable<Node>, Comparator<Node> {
    private final int data;
    private int cost;
    private HashMap<Node, Integer> dijkstraResultsMap;
    private float fairScore;

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Node(int data) {
        this.data = data;
    }

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

    public void setDijkstraResultsMap(HashMap<Node, Integer> dijkstraResultsMap) {
        this.dijkstraResultsMap = dijkstraResultsMap;
    }

    public HashMap<Node, Integer> getDijkstraResultsMap() {
        return dijkstraResultsMap;
    }

    public void setFairScore(float fairScore) {
        this.fairScore = fairScore;
    }

    public float getFairScore() {
        return fairScore;
    }
}
