package stpusingdijkstraalgo;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainClass {
    public static void main(String[] args) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {  }
//        System.out.println(new File("").getAbsolutePath());
        JFrame j = new JFrame();
        j.setTitle("Dijkstra Algorithm");

        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(900, 600));
        j.add(new Window());
        j.setVisible(true);

    
    }

    

}
