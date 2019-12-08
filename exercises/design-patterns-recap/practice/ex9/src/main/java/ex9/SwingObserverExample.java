package ex9;

/* Code from Head First Design Pattern */

import java.awt.*;
import javax.swing.*;

public class SwingObserverExample {
    JFrame frame;

    public void go() {
        frame = new JFrame();

        JButton button = new JButton("Should I do it?");

        // Your implementation

        frame.getContentPane().add(BorderLayout.CENTER, button);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, button);
        frame.setSize(300, 300);
         frame.setVisible(true);
    }
}