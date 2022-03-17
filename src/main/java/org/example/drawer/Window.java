package org.example.drawer;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final Color bgdCol = new Color(85, 166, 224);
    private JPanel drawingPanel;

    public Window(){
        setTitle("Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        setBackground(bgdCol);
        setSize(1000,1000);
    }

    public void generateDrawingPanel(){
        drawingPanel = new JPanel();
        drawingPanel.setBackground(bgdCol);
        //TODO
    }
}
