package main.java.benchmark;

import main.java.algorithm.*;
import main.java.graph.*;

import java.util.*;

public class Benchmark {
    private static final Random rand = new Random();
    private static final int WARMUP = 10;

    public static void main(String[] args) {
        int[] sizes = {10, 50, 100, 200, 500};
        double[] densities = {0.25, 0.5, 0.75, 1.0};

        for (Class<? extends Graph> clazz : Arrays.asList(AdjacencyListGraph.class, AdjacencyMatrixGraph.class)) {
            System.out.println("Representation: " + clazz.getSimpleName());
            for (int n : sizes) {
                for (double density : densities) {
                    // JVM warm-up
                    for (int i = 0; i < WARMUP; i++) {
                        Graph gw = createRandomGraph(clazz, n, density);
                        Dijkstra.shortestPaths(gw, 0);
                        BellmanFord.shortestPaths(gw, 0);
                        GraphSearch.dfs(gw, 0, new boolean[n]);
                    }

                    long totalDij = 0, totalBF = 0, totalDFS = 0;
                    for (int iter = 0; iter < 100; iter++) {
                        Graph g = createRandomGraph(clazz, n, density);
                        int src = 0;
                        long t1 = System.nanoTime();
                        Dijkstra.shortestPaths(g, src);
                        totalDij += (System.nanoTime() - t1);

                        long t2 = System.nanoTime();
                        BellmanFord.shortestPaths(g, src);
                        totalBF += (System.nanoTime() - t2);

                        long t3 = System.nanoTime();
                        GraphSearch.dfs(g, src, new boolean[n]);
                        totalDFS += (System.nanoTime() - t3);
                    }

                    System.out.printf(
                            "n=%d, density=%.2f: Dijkstra=%.2fµs, BF=%.2fµs, DFS=%.2fµs%n",
                            n, density,
                            totalDij / 1e3 / 100,
                            totalBF / 1e3 / 100,
                            totalDFS / 1e3 / 100
                    );
                }
            }
        }
    }

    private static Graph createRandomGraph(Class<? extends Graph> clazz, int n, double density) {
        try {
            Graph g = clazz.getConstructor(int.class).newInstance(n);
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (u != v && rand.nextDouble() <= density) {
                        int w = rand.nextInt(100) + 1;
                        g.addEdge(u, v, w);
                    }
                }
            }
            return g;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
