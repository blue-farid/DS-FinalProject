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
            }   else if (ins[0].equalsIgnoreCase("FairestScore")) {
                if(printFairestScore() != 0) {
                    System.out.println("not enough person! add some person and try again!");
                }
            } else if (ins[0].equalsIgnoreCase("people")) {
                System.out.println(people);
            }
            else {
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
                } catch (NumberFormatException e) {
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

    private Node findTheFairestCoffeeShop() {
        Set<Node> keys = mainGraph.getNodes();
        float minFairScore = Float.MAX_VALUE;
        Node fairestNode = null;
        for (Node node : keys) {
            node.setDijkstraResults(mainGraph.dijkstra(node));
        }
        for (Node node: keys) {
            float fairScore = calculateFairScore(node);
            if (minFairScore > fairScore) {
                minFairScore = fairScore;
                fairestNode = node;
            }
        }
        return fairestNode;
    }

    private float calculateFairScore(Node node) {
        int total = 0;
        int counter = 0;
        for (int i = 0; i < this.people.size(); i++) {
            int dijkstraRes = this.people.get(i).getNode().
                    getDijkstraResults()[node.getData() % mainGraph.getNodesCount()];

            for (int j = 0; j < this.people.size() - 1; j++) {
                if (j == i)
                    continue;
                total += Math.abs(dijkstraRes - this.people.get(j).getNode().
                        getDijkstraResults()[node.getData() % mainGraph.getNodesCount()]);
                counter++;
            }
        }
        return (float) total / (float) counter;
    }
    private int printFairestScore() {
        if (this.people.size() < 2) {
            return -1;
        }
        String res = "The Fairest Score for " + this.people.toString() + " is "
                + findTheFairestCoffeeShop();
        System.out.println(res);
        return 0;
    }
}
