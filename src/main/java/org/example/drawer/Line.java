package org.example.drawer;

import java.awt.*;
import java.awt.geom.Line2D;
import org.example.Point;

public class Line extends Line2D.Double implements Drawable{
    Color color;

    public Line(Point start, Point end){
        super(start.getX(), start.getY(), end.getX(), end.getY());
        color = lineCol;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.fill(this);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(this);
    }
}
