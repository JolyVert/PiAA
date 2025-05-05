package main.java;

import main.java.graph.*;
import main.java.visualization.BellmanFordVisualizer;
import main.java.visualization.DijkstraVisualizer;
import main.java.visualization.GraphVisualizer;
import main.java.benchmark.Benchmark;

import java.util.Scanner;

/**
 * Main application to select between benchmark and visualizations.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option:");
        System.out.println("1) Run performance benchmark");
        System.out.println("2) Visualize Dijkstra");
        System.out.println("3) Visualize Bellman-Ford");
        System.out.println("4) Visualize DFS");
        System.out.print("Choice> ");
        int choice = scanner.nextInt();

        // sample graph for visualization
        Graph g = new AdjacencyListGraph(6);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 8);
        g.addEdge(2, 3, 2);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 7);
        g.addEdge(1, 5, 12);

        switch (choice) {
            case 1:
                Benchmark.runBenchmark();
                break;
            case 2:
                DijkstraVisualizer.visualize(g, 0);
                break;
            case 3:
                BellmanFordVisualizer.visualize(g);
                break;
            case 4:
                GraphVisualizer.visualizeDFS(g, 0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}
