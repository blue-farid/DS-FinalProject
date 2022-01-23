package model.graph;

/**
 * The Edge class
 */
public class Edge {
    private final Node first, second;
    private final int weight;

    /**
     * Instantiates a new Edge.
     *
     * @param first  the first
     * @param second the second
     * @param weight the weight
     */
    public Edge(Node first, Node second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + weight + ")";
    }

    /**
     * To string string.
     *
     * @param node the node
     * @return the string
     */
    public String toString(Node node) {
        if (node.equals(first)) {
            return second + "";
        } else if (node.equals(second)) {
            return first + "";
        } else {
            return "";
        }
    }

    /**
     * Gets edge of a node
     *
     * @param node the node
     * @return the edge of the node (input)
     */
    public Node getEdgeOf(Node node) {
        if (node.equals(first)) {
            return second;
        } else if (node.equals(second)) {
            return first;
        } else {
            return null;
        }
    }
}
