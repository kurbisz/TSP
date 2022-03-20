package org.example.drawer;

import org.example.Point;
import org.example.data.EucTspData;
import org.example.data.Result;

import java.util.ArrayList;

public class Drawer {
    Window mainWindow;
    Window.DrawingSurface surface;
    ArrayList<Line> lines;

    public Drawer(){
        lines = new ArrayList<>();
        mainWindow = new Window();
        surface = mainWindow.drawingPanel;
    }

    public void showResult(Result result){
        Point last = result.way[0];
        result.way[0].setState(Point.State.VISITED);
        for(int i=1; i<result.currInd; i++){
            Point next = result.way[i];
            next.setState(Point.State.VISITED);
            lines.add(new Line(last, next));
            last = next;
        }
        generateMap((EucTspData) result.problem);
    }

    public void generateMap(EucTspData data){
        surface.refresh();
        for(Point p: data.getPoints()){
            surface.addObj(p);
        }
        for(Line l: lines){
            surface.addObj(l);
            //
        }
        surface.repaint();
    }
}
