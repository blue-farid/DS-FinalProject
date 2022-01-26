package model;

import consoleUtils.Console;
import consoleUtils.ConsoleColor;
import model.graph.Graph;
import model.graph.Node;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 * The Find the fairest coffee shop application.
 */
public class FindTheFairestCoffeeShopApplication implements Runnable {
    private final ArrayList<Person> people;
    private Graph mainGraph;

    /**
     * Instantiates a new Find the fairest coffee shop application.
     */
    public FindTheFairestCoffeeShopApplication() {
        people = new ArrayList<>();
    }

    /**
     * the App starts from here.
     */
    @Override
    public void run() {
        Console.initializeConsole();
        Console.setColor(ConsoleColor.TEXT_CYAN_BRIGHT);
        Scanner scanner = new Scanner(System.in);
        System.out.println("initialize the graph please...");
        while (true) {
            try {
                this.mainGraph = getGraphFromStdin(scanner);
                if (this.mainGraph != null) {
                    Console.setColor(ConsoleColor.TEXT_CYAN_BRIGHT);
                    System.out.println("the graph initialized successfully!");
                    break;
                }
            } catch (Exception e) {
                Console.setColor(ConsoleColor.TEXT_CYAN_BRIGHT);
                scanner = new Scanner(System.in); // to ignore bad inputs.
                System.out.println("something went wrong! try again!");
            }
        }
        readMode(scanner);
    }

    /**
     * the App goes to read mode, until termination.
     *
     * @param scanner read inputs with Scanner
     */
    private void readMode(Scanner scanner) {
        while (true) {
            Console.setColor(ConsoleColor.TEXT_RED_BRIGHT);
            int res = processInput(scanner.nextLine());
            if (res != 0) {
                System.out.println("wrong input!");
            }
        }
    }

    /**
     * process inputs and send the right response to client.
     *
     * @param in process the 'in' String
     * @return return 0 if and only if everything goes fine, and an negative integer if something failed.
     */
    private int processInput(String in) {
        Console.setColor(ConsoleColor.TEXT_CYAN_BRIGHT);
        String[] ins = in.split(" ");
        if (ins.length < 1) {
            return -1;
        } else if (ins.length == 1) {
            if (ins[0].equalsIgnoreCase("graph")) {
                System.out.println(mainGraph.toString());
            } else if (ins[0].equalsIgnoreCase("exit")) {
                Console.setColor(ConsoleColor.RESET);
                System.exit(0);
            } else if (ins[0].equalsIgnoreCase("fairest")) {
                if (printFairestScore() != 0) {
                    System.out.println("not enough person! add some person and try again!");
                }
            } else if (ins[0].equalsIgnoreCase("people")) {
                System.out.println(people);
            } else if (ins[0].equalsIgnoreCase("clear")) {
                Console.clearScreen();
            } else if (ins[0].equalsIgnoreCase("dfs")) {
                this.mainGraph.DFS();
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
                    if (ins[1].equalsIgnoreCase("all")) {
                        addAllPersons();
                        System.out.println("all of the nodes have been added successfully!");
                        return 0;
                    }
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
                        System.out.println("the people list has been cleared successfully!");
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

    private void addAllPersons() {
        for (Node node: this.mainGraph.getNodes()) {
            addPerson(node);
        }
    }
    /**
     * add a Person to people list
     *
     * @param node pass the Node to Person constructor
     * @return return the result
     */
    private boolean addPerson(Node node) {
        return this.people.add(new Person(node));
    }

    /**
     * remove a Person from people list
     *
     * @param node pass the Node to Person constructor
     * @return return the result
     */
    private boolean removePerson(Node node) {
        return this.people.remove(new Person(node));
    }

    /**
     * get graph from client based on a special format.
     *
     * @param sc read from stdin with Scanner
     * @return return the Graph
     */
    private Graph getGraphFromStdin(Scanner sc) throws Exception {
        Console.setColor(ConsoleColor.TEXT_RED_BRIGHT);
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

    /**
     * find the fairest coffee shops nodes
     *
     * @return return the fairest coffee shops nodes on an array
     */
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

    /**
     * calculate the fair score of a node based on a special formula
     *
     * @param node the node
     * @return return the fair score that is a float
     */
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

    /**
     * prints fairest node (fairest coffee shop) in a specific format on console
     *
     * @return return 0 if and only if everything goes fine,
     * and an negative integer if something failed.
     */
    private int printFairestScore() {
        if (this.people.size() < 2) {
            return -1;
        }
        Node[] nodes = findTheFairestCoffeeShops();
        String res = "The Fairest node/nodes for " + this.people.toString() + " is/are [";
        for (Node node : nodes) {
            if (node == null)
                break;
            res = res.concat(node.toString() + ", ");
        }
        res += "\b\b]";
        System.out.println(res);
        return 0;
    }
}
