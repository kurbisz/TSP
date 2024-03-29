package org.example.drawer;

import org.example.Point;
import org.example.data.EucTspData;
import org.example.data.Result;

import java.util.ArrayList;

public class Drawer {
    Window mainWindow;
    Window.DrawingSurface surface;
    ArrayList<Line> lines;

    public Drawer(String winTitle){
        lines = new ArrayList<>();
        mainWindow = new Window(winTitle);
        surface = mainWindow.drawingPanel;
    }

    public void showResult(Result result){
        Point[] points = ((EucTspData)result.problem).getPoints();
        double max = 0.0;
        for(Point p: points){
            p.setState(Point.State.NORMAL);
            if(p.getX() > max) max = p.getX();
            if(p.getY() > max) max = p.getY();
        }
        double scaleX = surface.getWidth()/ max;
        double scaleY = surface.getHeight()/ max;
        for(Point p: points){
            p.scale(scaleX, scaleY);
        }
        int last = result.way[0];
        int next = 0;
        points[last].setState(Point.State.STARTING);
        for(int i=1; i<result.way.length ; i++){
            next = result.way[i];
            points[next].setState(Point.State.VISITED);
            lines.add(new Line(points[last], points[next]));
            last = next;
        }
        lines.add(new Line(points[next], points[result.way[0]]));
        generateMap((EucTspData) result.problem);
    }

    public void generateMap(EucTspData data){
        surface.refresh();
        for(Point p: data.getPoints()){
            surface.addObj(p);
        }
        for(Line l: lines){
            surface.addObj(l);
        }
        surface.repaint();
    }
}
