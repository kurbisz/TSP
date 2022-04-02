package org.example.drawer;

import java.awt.*;

public interface Drawable {
    double pointSize = 7.0;
    Color lineCol = Color.LIGHT_GRAY;
    void draw(Graphics2D g2d);
}
