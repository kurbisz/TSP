package org.example;

import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.Neighbourhoods.Invert;
import org.example.algorithm.TabooSearch;
import org.example.algorithm.TwoOpt;
import org.example.analyses.MultiThreadedComparison;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.drawer.Line;
import org.example.drawer.Window;
import org.example.random.EuclideanTSPGen;

import java.util.Random;

public class RunClass {

    public static FileLoader loader;

    public static void main(String[] args) {
        String file = "dane/d657.tsp";
        loaderTest(file);

        TwoOpt tOpt = new TwoOpt(loader.getTspData());
        Result res2 = tOpt.calculate();
        draw(res2);
        System.out.println(res2.calcObjectiveFunction());

//        TabooSearch ts = new TabooSearch(loader.getTspData());
//        Result res = new Result(loader.getTspData());
//
//        ts.setParameters(res2, new Invert(), false);
//        res = ts.calculate();
//        draw(res);
//        System.out.println(res.calcObjectiveFunction());
//        mapTest(file);
//        generatorTest();
//        windowTest();

//        NearestNeighbour nearestNeighbour = new NearestNeighbour(loader.getTspData(), NearestNeighbour.Strategy.UPGRADED_MULTI);
//        nearestNeighbour.setThreadCount(6);
//        draw(nearestNeighbour.calculate());
//        nearestNeighbour.setThreadCount(100);
//        nearestNeighbour.calculate();
//        System.out.println(nearestNeighbour.getTime()/1000000000.0);
//        KRandom krandom = new KRandom(loader.getTspData(), 100000, true);
//        krandom.setThreads(10);
//        draw(krandom.calculate());
//        krandom.calculate();
//        System.out.println(krandom.getTime()/1000000000.0);


//        MultiThreadedNNComparisoon.calc("mulithreadNNComp_d1544291.csv", loader.getTspData());
//        generateRndMetro(10, 300, 50, 300/5, 300);
//        generateRnd(10, 300, 50);
    }

    private static void draw(Result res) {
        Drawer drawer = new Drawer(res.problem.getName());
        drawer.showResult(res);
    }

    private static void mapTest(String winTitle) {
        Drawer drawer = new Drawer(winTitle);
        TspData data = loader.getTspData();
        Result testResult = new Result(data);

        drawer.showResult(testResult);

    }

    private static void windowTest(String winTitle) {
        Window testWindow = new Window(winTitle);
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

    public static void generateRnd(int number, int maxCities, int minCities){
        EuclideanTSPGen gen = new EuclideanTSPGen(2000.0, 2000.0);
        gen.setType(EuclideanTSPGen.Type.RANDOM);
        Random r = new Random();
        for(int i = 0; i < number; i++){
            gen.generate("rnd" + i, r.nextInt(maxCities - minCities) + minCities);
        }
    }

    public static void generateRndMetro(int number, int maxCities, int minCities, int maxCitiesPerCluster, double maxClusterSize){
        EuclideanTSPGen gen = new EuclideanTSPGen(2000.0, 2000.0);
        gen.setType(EuclideanTSPGen.Type.METROPOLIS);
        gen.setMetroParam(maxClusterSize, maxCitiesPerCluster);
        Random r = new Random();
        for(int i = 0; i < number; i++){
            gen.generate("metro" + i, r.nextInt(maxCities - minCities) + minCities);
        }
    }
}
