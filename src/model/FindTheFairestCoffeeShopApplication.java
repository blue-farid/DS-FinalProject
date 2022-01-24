package model;

import model.graph.Graph;
import model.graph.Node;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class FindTheFairestCoffeeShopApplication implements Runnable {
    private final ArrayList<Person> people;
    private Graph mainGraph;

    public FindTheFairestCoffeeShopApplication() {
        people = new ArrayList<>();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        this.mainGraph = getGraphFromStdin(scanner);
        readMode(scanner);
    }

    private void readMode(Scanner scanner) {
        while (true) {
            int res = processInput(scanner.nextLine());
            if (res != 0) {
                System.out.println("wrong input!");
            }
        }
    }

    private int processInput(String in) {
        String[] ins = in.split(" ");
        if (ins.length < 1) {
            return -1;
        } else if (ins.length == 1) {
            if (ins[0].equalsIgnoreCase("test")) {
                System.out.println(mainGraph.toString());
            } else if (ins[0].equalsIgnoreCase("exit")) {
                System.exit(0);
            } else if (ins[0].equalsIgnoreCase("fairest")) {
                if (printFairestScore() != 0) {
                    System.out.println("not enough person! add some person and try again!");
                }
            } else if (ins[0].equalsIgnoreCase("people")) {
                System.out.println(people);
            } else {
                return -1;
            }
        } else if (ins.length == 2) {
            if (ins[0].equalsIgnoreCase("join")) {
                try {
                    int nodeNum = Integer.parseInt(ins[1]);
                    Node node = mainGraph.getNode(nodeNum);
                    if (node == null) {
                        return -1;
                    }
                    addPerson(node);
                    System.out.println(node + " has been added successfully!");
                } catch (NumberFormatException e) {
                    return -1;
                }
            } else if (ins[0].equalsIgnoreCase("left")) {
                try {
                    int nodeNum = Integer.parseInt(ins[1]);
                    Node node = mainGraph.getNode(nodeNum);
                    if (node == null) {
                        return -1;
                    }
                    removePerson(node);
                    System.out.println(node + " has been removed successfully!");
                } catch (NumberFormatException e) {
                    if (ins[1].equalsIgnoreCase("all")) {
                        this.people.clear();
                        return 0;
                    }
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
        return 0;
    }

    private void addPerson(Node node) {
        this.people.add(new Person(node));
    }

    private void removePerson(Node node) {
        this.people.remove(new Person(node));
    }

    private Graph getGraphFromStdin(Scanner sc) {
        int nodesCount = sc.nextInt();
        int edgesCount = sc.nextInt();
        Graph graph = new Graph(nodesCount, edgesCount);
        sc.nextLine();
        sc.nextLine(); //ignore the line of nodes input. (that is not needed).
        for (int i = 0; i < edgesCount; i++) {
            graph.addEdge(new Node(sc.nextInt()), new Node(sc.nextInt()), sc.nextInt());
            sc.nextLine();
        }
        return graph;
    }

    private Node[] findTheFairestCoffeeShops() {
        Set<Node> keys = this.mainGraph.getNodes();
        float minFairScore = Float.MAX_VALUE;
        Node[] fairestNodes = new Node[this.mainGraph.getNodesCount()];
        int index = 0;
        for (Node node : keys) {
            if (node.getDijkstraResultsMap() == null) {
                node.setDijkstraResultsMap(this.mainGraph.dijkstra(node));
            }
        }
        for (Node node : keys) {
            float fairScore = calculateFairScore(node);
            node.setFairScore(fairScore);
            if (minFairScore > fairScore) {
                minFairScore = fairScore;
                fairestNodes[0] = node;
            }
        }
        for (Node node : keys) {
            if (fairestNodes[0].getFairScore() == node.getFairScore()) {
                fairestNodes[index++] = node;
            }
        }
        if (index < fairestNodes.length) {
            fairestNodes[index] = null;
        }
        return fairestNodes;
    }

    private float calculateFairScore(Node node) {
        int total = 0;
        int counter = 0;
        for (int i = 0; i < this.people.size(); i++) {
            int dijkstraRes = this.people.get(i).getNode().
                    getDijkstraResultsMap().get(node);

            for (int j = i + 1; j < this.people.size(); j++) {

                total += Math.abs(dijkstraRes - this.people.get(j).getNode().
                        getDijkstraResultsMap().get(node));
                counter++;
            }
        }
        return (float) total / (float) counter;
    }

    private int printFairestScore() {
        if (this.people.size() < 2) {
            return -1;
        }
        Node[] nodes = findTheFairestCoffeeShops();
        String res = "The Fairest Score for " + this.people.toString() + " is [";
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null)
                break;
            res = res.concat(nodes[i].toString() + ", ");
        }
        res += "\b\b]";
        System.out.println(res);
        return 0;
    }
}
