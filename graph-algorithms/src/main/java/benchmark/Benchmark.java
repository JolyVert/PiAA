package main.java.benchmark;

import main.java.algorithm.*;
import main.java.graph.*;

import java.util.*;

public class Benchmark {
    private static final Random rand = new Random();
    private static final int WARMUP = 10;
    private static final int NUM_ITER = 100;

    /**
     * Run the performance benchmark for various graph sizes and densities,
     * performing NUM_ITER measurements per configuration and saving the average.
     */
    public static void runBenchmark() {
        int[] sizes = {10, 50, 100, 200, 500};
        double[] densities = {0.25, 0.5, 0.75, 1.0};

        List<String> results = new ArrayList<>();
        results.add("Representation,n,density,Dijkstra_us,BF_us,DFS_us");

        for (Class<? extends Graph> clazz : Arrays.asList(
                AdjacencyListGraph.class,
                AdjacencyMatrixGraph.class)) {
            for (int n : sizes) {
                for (double density : densities) {
                    warmup(clazz, n, density);
                    long totalDij = 0, totalBF = 0, totalDFS = 0;
                    for (int iter = 0; iter < NUM_ITER; iter++) {
                        Graph g = createRandomGraph(clazz, n, density);
                        int src = 0;
                        totalDij += time(() -> Dijkstra.shortestPaths(g, src));
                        totalBF  += time(() -> BellmanFord.shortestPaths(g, src));
                        totalDFS += time(() -> GraphSearch.dfs(g, src, new boolean[n]));
                    }
                    double avgDij = totalDij / (NUM_ITER * 1e3);
                    double avgBF  = totalBF  / (NUM_ITER * 1e3);
                    double avgDFS = totalDFS / (NUM_ITER * 1e3);
                    results.add(String.format(
                            "%s | %d | %.2f | %.2f | %.2f | %.2f",
                            clazz.getSimpleName(), n, density,
                            avgDij, avgBF, avgDFS
                    ));
                }
            }
        }
        // print CSV-like results
        results.forEach(System.out::println);
    }

    private static void warmup(Class<? extends Graph> clazz, int n, double density) {
        for (int i = 0; i < WARMUP; i++) {
            Graph g = createRandomGraph(clazz, n, density);
            Dijkstra.shortestPaths(g, 0);
            BellmanFord.shortestPaths(g, 0);
            GraphSearch.dfs(g, 0, new boolean[n]);
        }
    }

    private static long time(Runnable fn) {
        long t1 = System.nanoTime(); fn.run();
        return System.nanoTime() - t1;
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
