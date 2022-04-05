package org.example;

import org.example.analyses.MultiThreadedComparison;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.drawer.Line;
import org.example.drawer.Window;
import org.example.random.EuclideanTSPGen;

public class RunClass {

    public static FileLoader loader;

    public static void main(String[] args) {
        loaderTest("dane/d1291.tsp");
//        generatorTest();
//        windowTest();
//        mapTest();
//        NearestNeighbour nearestNeighbour = new NearestNeighbour(loader.getTspData(), NearestNeighbour.Strategy.UPGRADED_MULTI);
//        draw(nearestNeighbour.calculate());
//        nearestNeighbour.setThreadCount(6);
//        nearestNeighbour.calculate();
//        System.out.println(nearestNeighbour.getTime()/1000000000.0);
//        KRandom krandom = new KRandom(loader.getTspData(), 100000, true);
//        krandom.setThreads(30);
//        draw(krandom.calculate());
//        krandom.calculate();
//        System.out.println(krandom.getTime()/1000000000.0);
//        TwoOpt tOpt = new TwoOpt(loader.getTspData());
//        draw(tOpt.calculate());
        MultiThreadedComparison.calcNN("mulithreadNNComp_d1291.csv", loader.getTspData());
    }

    private static void draw(Result res) {
        Drawer drawer = new Drawer();
        drawer.showResult(res);
    }

    private static void mapTest() {
        Drawer drawer = new Drawer();
        TspData data = loader.getTspData();
        Result testResult = new Result(data);

        drawer.showResult(testResult);

    }

    private static void windowTest() {
        Window testWindow = new Window();
        Point testPointA = new Point(30, 50, Point.State.NORMAL);
        Point testPointB = new Point(300, 100, Point.State.VISITED);
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
        gen.generate("test3Metro", 150);
    }
}
