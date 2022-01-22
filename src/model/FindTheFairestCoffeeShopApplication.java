package model;

import model.graph.Graph;
import model.graph.Node;

import java.util.Scanner;

public class FindTheFairestCoffeeShopApplication implements Runnable{

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Graph graph = getGraphFromStdin(scanner);
        System.out.println(graph.toString());
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
