package org.example.drawer;

import org.example.Point;
import org.example.data.EucTspData;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;

public class Drawer {
    Window mainWindow;
    Window.DrawingSurface surface = mainWindow.drawingPanel;
    Point[] points;
    Line[] lines;

    public Drawer(TspData data){
        mainWindow = new Window();
    }

    public void showResult(Result result){
        ArrayList<Integer> toUpdate = new ArrayList<>();
        for(int pInd: result.way){
            toUpdate.add(pInd);
        }
    }

    public void draw(){

        for(Point p: points){
            surface.addObj(new Point(p.getInd(), p.getX(), p.getY(), Point.State.NORMAL));
        }
        for(Line l: lines){
            surface.addObj(l);
        }
    }

    public void generateMap(EucTspData data){
        points = new Point[data.getSize()];
        int i = 1;
        for(Point p: data.getPoints()){
        }
    }
}
