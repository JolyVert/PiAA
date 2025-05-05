package main.java.algorithm;

import main.java.graph.Edge;
import main.java.graph.Graph;

import java.util.*;

public class GraphSearch {
    public static void dfs(Graph g, int source, boolean[] visited) {
        visited[source] = true;
        for (Edge e : g.getNeighbors(source)) {
            if (!visited[e.to]) dfs(g, e.to, visited);
        }
    }
}
