package main.java.graph;

import java.util.*;

public class AdjacencyListGraph implements Graph {
    private final List<List<Edge>> adj;
    public AdjacencyListGraph(int n) {
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }
    public int getVertexCount() { return adj.size(); }
    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(v, weight));
    }
    public List<Edge> getNeighbors(int u) { return Collections.unmodifiableList(adj.get(u)); }
}
