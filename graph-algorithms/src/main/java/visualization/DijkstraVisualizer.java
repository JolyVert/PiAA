package main.java.visualization;

import main.java.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstraVisualizer extends JPanel {
    private final Graph graph;
    private final int width = 600, height = 600;
    private int[] dist;
    private boolean[] visited;
    private ArrayList<Point> coords;
    private PriorityQueue<int[]> pq;

    public DijkstraVisualizer(Graph graph) {
        this.graph = graph;
        int n = graph.getVertexCount();
        dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE);
        visited = new boolean[n];
        coords = new ArrayList<>(n);
        for (int i = 0; i < n; i++) coords.add(place(i, n));
        setPreferredSize(new Dimension(width, height));
        pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
    }

    private Point place(int i, int n) {
        double angle = 2 * Math.PI * i / n;
        int x = (int) (width/2 + 200 * Math.cos(angle));
        int y = (int) (height/2 + 200 * Math.sin(angle));
        return new Point(x, y);
    }

    public void start(int source) {
        dist[source] = 0; pq.add(new int[]{source, 0});
        new Thread(this::runDijkstra).start();
    }

    private void runDijkstra() {
        while (!pq.isEmpty()) {
            int u = pq.poll()[0];
            if (visited[u]) continue;
            visited[u] = true; repaint(); sleep();
            for (Edge e : graph.getNeighbors(u)) {
                int v = e.to, w = e.weight;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w; pq.add(new int[]{v, dist[v]});
                    repaint(); sleep();
                }
            }
        }
    }

    private void sleep() { try { Thread.sleep(500); } catch (Exception ignored) {} }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int u = 0; u < graph.getVertexCount(); u++) {
            Point p1 = coords.get(u);
            for (Edge e : graph.getNeighbors(u)) {
                Point p2 = coords.get(e.to);
                g2.setColor(Color.LIGHT_GRAY); g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        for (int i = 0; i < coords.size(); i++) {
            Point p = coords.get(i);
            g2.setColor(visited[i] ? Color.RED : Color.BLUE);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Color.WHITE);
            String label = i + (dist[i] < Integer.MAX_VALUE ? "(" + dist[i] + ")" : "");
            g2.drawString(label, p.x - 10, p.y + 4);
        }
    }

    public static void visualize(Graph graph, int source) {
        JFrame frame = new JFrame("Dijkstra Visualization");
        DijkstraVisualizer panel = new DijkstraVisualizer(graph);
        frame.add(panel); frame.pack(); frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); panel.start(source);
    }
}
