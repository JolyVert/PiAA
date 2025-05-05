package test.java.algorithm;


import main.java.algorithm.*;
import main.java.graph.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTests {
    private Graph sample() {
        AdjacencyListGraph g = new AdjacencyListGraph(4);
        g.addEdge(0, 1, 1); g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3); g.addEdge(0, 3, 10);
        return g;
    }
    @Test public void testDijkstra() {
        Graph g = sample(); int[] d = Dijkstra.shortestPaths(g, 0);
        assertArrayEquals(new int[]{0,1,3,6}, d);
    }
    @Test public void testBellmanFord() {
        Graph g = sample(); int[] d = BellmanFord.shortestPaths(g, 0);
        assertArrayEquals(new int[]{0,1,3,6}, d);
    }
    @Test public void testDFS() {
        Graph g = sample(); boolean[] vis = new boolean[4];
        GraphSearch.dfs(g, 0, vis);
        assertTrue(vis[0] && vis[1] && vis[2] && vis[3]);
    }
}
