package stpusingdijkstraalgo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class Window extends JPanel {

    private Graph graph;
    private Panel graphPanel;

    public Window() {
        super.setLayout(new BorderLayout());
        setGraphPanel();
    }

    private void setGraphPanel() {
        graph = new Graph();
        graphPanel = new Panel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        setTopPanel();
        setButtons();
    }

    private void setTopPanel() {
        JLabel info = new JLabel("Brain Game");
        info.setForeground(new Color(230, 220, 250));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(130, 50, 250));
        panel.add(info);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(panel, BorderLayout.NORTH);
    }

    private void setButtons() {
        JButton run = new JButton();
        setupIcon(run, "run");
        JButton reset = new JButton();
        setupIcon(reset, "reset");
        final JButton info = new JButton();
        setupIcon(info, "info");
        JButton setMessage = new JButton("Set Impulse");
        JButton showMessage = new JButton("Show Impulse");
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.add(reset);
        buttonPanel.add(run);
        buttonPanel.add(info);
        buttonPanel.add(setMessage);
        buttonPanel.add(showMessage);

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset();
            }
        });

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Click on empty space to create new neuron\n"
                        + "Drag from neuron to neuron to create an synapse\n"
                        + "Click on synapse to set the time,distance and lifetime\n\n"
                        + "Combinations:\n"
                        + "Ctrl  + Drag               :    Reposition neuron\n"
                        + "Ctrl  + Click                :    Get status of synapse\n"
                        + "Shift + Click               :    Delete neuron/synapse\n");
            }
        });

        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(graph.hasMessage()){
                    graphPanel.setPath(graph.runAlgo());
                    graphPanel.showResult();
                    }else{
                        JOptionPane.showMessageDialog(null, "No more impulses to send");
                    }
                } catch (IllegalStateException ise) {
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        });
        setMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String source = JOptionPane.showInputDialog("Enter Source Neuron ID  " 
                    + " : ");
               String destination = JOptionPane.showInputDialog("Enter Destination Neuron ID  " 
                    + " : ");
                if(source == null||destination==null){
                    return;
                }
                if(source.equals(destination)){
                    JOptionPane.showMessageDialog(null, "Source and destination neuron cannnot be the same.");
                }
                if (graph.containsVertex(source) && graph.containsVertex(destination)) {
                    graph.addMessasge(graph.getVertex(source), graph.getVertex(destination));
                } else {
                    JOptionPane.showMessageDialog(null, "Neuron does not exist!");
                }
            
            return;
            }
        });
        showMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, graph.printMessages());
               
            return;
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupIcon(JButton button, String img) {
        try {
            Image icon = ImageIO.read(new File(
                    img + ".png"));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
        }
    }

}
