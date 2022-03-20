package org.example;

import org.example.data.EucTspData;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.drawer.Line;
import org.example.drawer.Window;
import org.example.random.EuclideanTSPGen;

public class RunClass {

    public static FileLoader loader;

    public static void main(String[] args) {
        loaderTest("testMetro.tsp");
        generatorTest();
//        windowTest();
        mapTest();
    }

    private static void mapTest() {
        Drawer drawer = new Drawer();
        TspData data = loader.getTspData();
        Result testResult = new Result(data);

        testResult.addWaypoint(((EucTspData) data).getPoints()[3]);
        testResult.addWaypoint(((EucTspData) data).getPoints()[6]);
        testResult.addWaypoint(((EucTspData) data).getPoints()[0]);
        testResult.addWaypoint(((EucTspData) data).getPoints()[3]);

        drawer.showResult(testResult);

    }

    private static void windowTest() {
        Window testWindow = new Window();
        Point testPointA = new Point(1, 30, 50, Point.State.NORMAL);
        Point testPointB = new Point(2, 300, 100, Point.State.VISITED);
        Line testLine = new Line(testPointA, testPointB);
        testWindow.drawingPanel.addObj(testPointA);
        testWindow.drawingPanel.addObj(testPointB);
        testWindow.drawingPanel.addObj(testLine);
        testWindow.drawingPanel.repaint();
    }

    public static void loaderTest(String filename){
        loader = new FileLoader(filename);
        loader.load();
        System.out.println(loader.getTspData().toString());
    }

    public static void generatorTest(){
        EuclideanTSPGen gen = new EuclideanTSPGen(1000.0, 1000.0);
        gen.setType(EuclideanTSPGen.Type.METROPOLIS);
        gen.generate("testMetro", 1000);
    }
}
