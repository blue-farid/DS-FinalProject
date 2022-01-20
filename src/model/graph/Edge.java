package model.graph;

import java.util.Scanner;

public class Edge {
    private final Node first, second;
    private final int weight;

    public Edge(Node first, Node second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public Node getFirst() {
        return first;
    }

    public Node getSecond() {
        return second;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public String toString(Node node) {
        if (node.equals(first)) {
            return second + "";
        } else if(node.equals(second)) {
            return first + "";
        } else {
            return toString();
        }
    }
}
