package main.java.visualization;

import main.java.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphVisualizer extends JPanel {
    private final Graph graph;
    private final int width = 600, height = 600;
    private boolean[] visited;
    private ArrayList<Point> coords;

    public GraphVisualizer(Graph graph) {
        this.graph = graph;
        int n = graph.getVertexCount();
        visited = new boolean[n];
        coords = new ArrayList<>(n);
        // place nodes in circle
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (width/2 + 200 * Math.cos(angle));
            int y = (int) (height/2 + 200 * Math.sin(angle));
            coords.add(new Point(x, y));
        }
        setPreferredSize(new Dimension(width, height));
    }

    public void startDFS(int source) {
        new Thread(() -> dfs(source)).start();
    }

    private void dfs(int u) {
        visited[u] = true;
        repaint(); sleep();
        for (Edge e : graph.getNeighbors(u)) {
            if (!visited[e.to]) dfs(e.to);
        }
    }

    private void sleep() { try { Thread.sleep(500); } catch (InterruptedException ignored) {} }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int u = 0; u < graph.getVertexCount(); u++) {
            Point p1 = coords.get(u);
            for (Edge e : graph.getNeighbors(u)) {
                Point p2 = coords.get(e.to);
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        for (int i = 0; i < coords.size(); i++) {
            Point p = coords.get(i);
            g2.setColor(visited[i] ? Color.RED : Color.BLUE);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(i), p.x - 4, p.y + 4);
        }
    }

    public static void visualizeDFS(Graph graph, int source) {
        JFrame frame = new JFrame("Graph DFS Visualization");
        GraphVisualizer panel = new GraphVisualizer(graph);
        frame.add(panel); frame.pack(); frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); panel.startDFS(source);
    }
}
