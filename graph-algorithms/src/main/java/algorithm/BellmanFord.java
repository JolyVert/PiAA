package main.java.algorithm;

import main.java.graph.Edge;
import main.java.graph.Graph;

import java.util.Arrays;

public class BellmanFord {
    public static int[] shortestPaths(Graph g, int source) {
        int n = g.getVertexCount();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean updated = false;
            for (int u = 0; u < n; u++) {
                if (dist[u] == Integer.MAX_VALUE) continue;
                for (Edge e : g.getNeighbors(u)) {
                    int v = e.to, w = e.weight;
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        updated = true;
                    }
                }
            }
            if (!updated) break;
        }
        return dist;
    }
}
