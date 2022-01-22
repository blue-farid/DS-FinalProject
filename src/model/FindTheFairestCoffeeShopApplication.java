package model;

import model.graph.Graph;
import model.graph.Node;

import java.util.ArrayList;
import java.util.Scanner;

public class FindTheFairestCoffeeShopApplication implements Runnable {
    private Graph mainGraph;
    private final ArrayList<Person> people;
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
            if (ins[0].equals("test")) {
                System.out.println(mainGraph.toString());
            } else if (ins[0].equals("exit")) {
                System.exit(0);
            } else {
                return -1;
            }
        } else if (ins.length == 2) {
            if (ins[0].equals("join")) {
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
            } else if (ins[0].equals("left")) {
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
}
