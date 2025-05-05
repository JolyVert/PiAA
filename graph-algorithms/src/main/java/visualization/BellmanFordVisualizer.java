package main.java.visualization;

import main.java.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BellmanFordVisualizer extends JPanel {
    private final Graph graph;
    private final int width = 600, height = 600;
    private int[] dist;
    private ArrayList<Point> coords;
    private int iteration;

    public BellmanFordVisualizer(Graph graph) {
        this.graph = graph;
        int n = graph.getVertexCount();
        dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        coords = new ArrayList<>();
        for (int i = 0; i < n; i++) coords.add(place(i, n));
        setPreferredSize(new Dimension(width, height));
        new Thread(this::runBellmanFord).start();
    }

    private Point place(int i, int n) {
        double angle = 2 * Math.PI * i / n;
        int x = (int)(width/2 + 200*Math.cos(angle));
        int y = (int)(height/2 + 200*Math.sin(angle));
        return new Point(x, y);
    }

    private void runBellmanFord() {
        int n = graph.getVertexCount();
        for (int k = 1; k < n; k++) {
            iteration = k;
            for (int u = 0; u < n; u++) {
                if (dist[u]==Integer.MAX_VALUE) continue;
                for (Edge e:graph.getNeighbors(u)){
                    int v=e.to, w=e.weight;
                    if(dist[u]+w<dist[v]) dist[v]=dist[u]+w;
                    repaint(); sleep();
                }
            }
        }
    }

    private void sleep(){ try{Thread.sleep(400);}catch(Exception ignored){} }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        for(int u=0;u<graph.getVertexCount();u++){
            Point p1=coords.get(u);
            for(Edge e:graph.getNeighbors(u)){
                Point p2=coords.get(e.to);
                g2.setColor(Color.LIGHT_GRAY); g2.drawLine(p1.x,p1.y,p2.x,p2.y);
            }
        }
        for(int i=0;i<coords.size();i++){
            Point p=coords.get(i);
            g2.setColor(dist[i]<Integer.MAX_VALUE?Color.RED:Color.BLUE);
            g2.fillOval(p.x-15, p.y-15,30,30);
            g2.setColor(Color.WHITE);
            String lbl=i+(dist[i]<Integer.MAX_VALUE?"("+dist[i]+")":"");
            g2.drawString(lbl,p.x-12,p.y+4);
        }
        g2.setColor(Color.BLACK);
        g2.drawString("Iteration: "+iteration,10,20);
    }

    public static void visualize(Graph graph) {
        JFrame frame=new JFrame("Bellman-Ford Visualization");
        BellmanFordVisualizer panel=new BellmanFordVisualizer(graph);
        frame.add(panel); frame.pack(); frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
