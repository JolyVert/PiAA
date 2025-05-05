package main.java.algorithm;

import main.java.graph.Edge;
import main.java.graph.Graph;

import java.util.*;

public class Dijkstra {
    public static int[] shortestPaths(Graph g, int source) {
        int n = g.getVertexCount();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0], d = cur[1];
            if (d != dist[u]) continue;
            for (Edge e : g.getNeighbors(u)) {
                int v = e.to, w = e.weight;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }
        return dist;
    }
}
