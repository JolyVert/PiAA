package main.java.graph;

import java.util.List;

public interface Graph {
    int getVertexCount();
    void addEdge(int u, int v, int weight);
    List<Edge> getNeighbors(int u);
}
