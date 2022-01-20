package model.graph;

public class Edge {
    private final int first, second, weight;

    public Edge(int first, int second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getWeight() {
        return weight;
    }
}
