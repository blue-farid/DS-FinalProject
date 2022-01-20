package model.graph;

import java.util.Objects;

public class Node implements Comparable<Node>{
    private final int data;
    private int cost;

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
}
