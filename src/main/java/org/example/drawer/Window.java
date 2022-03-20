package org.example.drawer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    private final Color bgdCol = new Color(125, 192, 239);
    public DrawingSurface drawingPanel;

    public Window(){
        setTitle("Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        setBackground(bgdCol);
        setSize(1000,1000);
        drawingPanel = new DrawingSurface();
        drawingPanel.setBackground(bgdCol);
        add(drawingPanel);
        setVisible(true);
    }

    public class DrawingSurface extends JPanel{

        private ArrayList<Drawable> objects;

        DrawingSurface(){
            objects = new ArrayList<>();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            for(Drawable obj: objects){
                obj.draw(g2d);
            }
        }

        public void refresh(){
            objects.clear();
        }

        public void addObj(Drawable obj){
            objects.add(obj);
        }
    }
}
