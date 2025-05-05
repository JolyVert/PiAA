package main.java.graph;

import java.util.*;

public class AdjacencyMatrixGraph implements Graph {
    private final int[][] mat;
    public AdjacencyMatrixGraph(int n) {
        mat = new int[n][n];
        for (int[] row : mat) Arrays.fill(row, Integer.MAX_VALUE);
    }
    public int getVertexCount() { return mat.length; }
    public void addEdge(int u, int v, int weight) {
        mat[u][v] = weight;
    }
    public List<Edge> getNeighbors(int u) {
        List<Edge> list = new ArrayList<>();
        for (int v = 0; v < mat[u].length; v++) {
            if (mat[u][v] != Integer.MAX_VALUE) list.add(new Edge(v, mat[u][v]));
        }
        return list;
    }
}
