package stpusingdijkstraalgo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    private DrawUtils drawUtils;

    private Graph graph;

    private Vertex selectedVertex = null;
    private Vertex hoveredVertex = null;
    private Edge hoveredEdge = null;

    private java.util.List<Vertex> path = null;

    private Point cursor;

    public Panel(Graph graph) throws NullPointerException {
        this.graph = graph;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setPath(List<Vertex> path) {
        this.path = path;
        hoveredEdge = null;
        repaint();
    }

    public void showResult() {
        int distance = graph.getShortestDistance();
        int time = graph.getShortestTime();
        String s = "";
        if (time != Integer.MAX_VALUE) {
            s = "Shortest distance calculated : " + distance + "\n" + "Shortest time calculated : " + time + "\n" + "Shortest pathway is highlighted";
        } else {
            s = "Neuron is unreachable!";
        }
        JOptionPane.showMessageDialog(null, s);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawUtils = new DrawUtils(graphics2d);

        if (graph.isSolved()) {
            drawUtils.drawPath(path);
        }

        if (selectedVertex != null && cursor != null) {
            Edge e = new Edge(selectedVertex, new Vertex(cursor));
            drawUtils.drawEdge(e);
        }

        for (Edge edge : graph.getEdges()) {
            if (edge == hoveredEdge) {
                drawUtils.drawHoveredEdge(edge);
            }
            if (edge.getLifetime() > 0) {
                drawUtils.drawEdge(edge);
            } else {
                drawUtils.drawDeadEdge(edge);
            }
        }

        for (Vertex node : graph.getVertices()) {

            drawUtils.drawNode(node);

        }
        if (graph.getVertices().size() > 0 && graph.getMessages().size() > 0) {
            for (Vertex node : graph.getVertices()) {

                if (node == selectedVertex || node == hoveredVertex) {
                    drawUtils.drawHalo(node);
                }
                if (graph.isSource(node)) {
                    drawUtils.drawSourceNode(node);
                } else if (graph.isDestination(node)) {
                    drawUtils.drawDestinationNode(node);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {

        Vertex selected = null;
        for (Vertex node : graph.getVertices()) {
            if (DrawUtils.isWithinBounds(e, node.getCoord())) {
                selected = node;
                break;
            }
        }

        if (selected != null) {
            //delete vertex
            if (e.isShiftDown()) {
                graph.deleteVertex(selected);
                graph.setSolved(false);
                repaint();
                return;
            }

        }

        if (hoveredEdge != null) {
            if (e.isControlDown()) {
                JOptionPane.showMessageDialog(null, hoveredEdge.showStatus());
                return;
            }
            if (e.isShiftDown()) {
                graph.getEdges().remove(hoveredEdge);
                hoveredEdge = null;
                graph.setSolved(false);
                repaint();
                return;
            }

            String dis = JOptionPane.showInputDialog("Enter distance for " + hoveredEdge.toString()
                    + " : ");

            try {
                int distance = Integer.parseInt(dis);

                if (distance > 0) {
                    hoveredEdge.setDistance(distance);
                    graph.setSolved(false);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Distance should be positive");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "The entry must be an integer");
                return;
            }
            String t = JOptionPane.showInputDialog("Enter time for " + hoveredEdge.toString()
                    + " : ");
            try {
                int time = Integer.parseInt(t);

                if (time > 0) {
                    hoveredEdge.setTime(time);
                    graph.setSolved(false);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Time should be positive");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "The entry must be an integer");
                return;
            }
            String life = JOptionPane.showInputDialog("Enter lifetime for " + hoveredEdge.toString()
                    + " : ");
            try {
                int lifetime = Integer.parseInt(life);

                if (lifetime > 0) {
                    hoveredEdge.setLifetime(lifetime);
                    graph.setSolved(false);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Time should be positive");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "The entry must be an integer");
                return;
            }
            return;
        }

        for (Vertex vertex : graph.getVertices()) {
            if (DrawUtils.isOverlapping(e, vertex.getCoord())) {
                JOptionPane.showMessageDialog(null, "Overlapping neuron can't be created");
                return;
            }
        }
        Vertex v = new Vertex(e.getPoint());
        String s = JOptionPane.showInputDialog("Input neuron name:");
        if (graph.containsVertex(s)) {
            JOptionPane.showMessageDialog(null, "Neuron exists");
            return;
        }
        v.setName(s);
        graph.addVertex(v);
        graph.setSolved(false);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {

    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {

        for (Vertex vertex : graph.getVertices()) {
            if (selectedVertex != null && vertex != selectedVertex && DrawUtils.isWithinBounds(e, vertex.getCoord())) {
                Edge new_edge = new Edge(selectedVertex, vertex);
                graph.addEdge(new_edge);
                graph.setSolved(false);
            }
        }
        selectedVertex = null;
        hoveredVertex = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }

    @Override
    public void mouseDragged(MouseEvent e
    ) {
        hoveredVertex = null;

        for (Vertex node : graph.getVertices()) {
            if (selectedVertex == null && DrawUtils.isWithinBounds(e, node.getCoord())) {
                selectedVertex = node;
            } else if (DrawUtils.isWithinBounds(e, node.getCoord())) {
                hoveredVertex = node;
            }
        }

        if (selectedVertex != null) {
            if (e.isControlDown()) {
                selectedVertex.setCoord(e.getX(), e.getY());
                cursor = null;
                repaint();
                return;
            }

            cursor = new Point(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e
    ) {

        if (e.isControlDown()) {
            hoveredVertex = null;
            for (Vertex node : graph.getVertices()) {
                if (DrawUtils.isWithinBounds(e, node.getCoord())) {
                    hoveredVertex = node;
                }
            }
        }

        hoveredEdge = null;

        for (Edge edge : graph.getEdges()) {
            if (DrawUtils.isOnEdge(e, edge)) {
                hoveredEdge = edge;
            }
        }

        repaint();
    }

    public void reset() {
        graph.clear();
        selectedVertex = null;
        hoveredVertex = null;
        hoveredEdge = null;
        repaint();

    }
}
