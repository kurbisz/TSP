package org.example;

import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.taboo.ExploreFunctions.Kick;
import org.example.algorithm.taboo.Neighbourhoods.*;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.Neighbourhoods.Moves.SwapMove;
import org.example.algorithm.taboo.TabooSearch;
import org.example.algorithm.TwoOpt;
import org.example.algorithm.taboo.TabooSearch2;
import org.example.algorithm.taboo.tabooList.BasicTabooList;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.drawer.Line;
import org.example.drawer.Window;
import org.example.random.EuclideanTSPGen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

public class RunClass {

    public static FileLoader loader;

    public static void main(String[] args) {

//        String file = "dane/d657.tsp";
//        String file = "dane/d198.tsp";
//        String file = "dane/d1291.tsp";
//        String file = "dane/pr299.tsp";
//          String file = "kroB100.tsp";
        String file = "metro6.tsp";
//        String file = "metro7.tsp";
//        String file = "metro8.tsp";
//        String file = "rnd3.tsp";
//        String file = "test.tsp";
//        loaderTest(file);
//        neighbourhoodTest(file);
//        movesTest();
//        kikTest(file);
//        TspData data = loader.getTspData();
//        Result result = new Result(data);
//        draw(result);
//        TwoOpt tOpt = new TwoOpt(loader.getTspData());
//        Result res2 = tOpt.calculate();
//        draw(res2);
//        System.out.println(res2.calcObjectiveFunction());

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
//        TspData data = loader.getTspData();
//        KRandom krandom = new KRandom(data, 100000, true);
//        krandom.setThreads(10);
//        NearestNeighbour nearestNeighbour = new NearestNeighbour(data, NearestNeighbour.Strategy.UPGRADED_MULTI);
//        nearestNeighbour.setThreadCount(6);
//        Result startingRes = nearestNeighbour.calculate();
//        draw(startingRes);

//        TabooSearch2 ts = new TabooSearch2(data, startingRes, true, new BasicTabooList(7), new Invert(), new IterationsStop(10000), null, new Kick(100, 5), 6);
//        Result endRes = ts.calculate();
//        System.out.println("At the beginning: " + startingRes.calcObjectiveFunction());
//        System.out.println("At the end: " + endRes.calcObjectiveFunction());
//        draw(endRes);
//        krandom.calculate();
//        System.out.println(krandom.getTime()/1000000000.0);


//        MultiThreadedNNComparisoon.calc("mulithreadNNComp_d1544291.csv", loader.getTspData());
//        generateRndMetro(10, 300, 50, 300/5, 300);
//        generateRnd(10, 300, 50);
    }

    private static void kikTest(String filename) {
        loader = new FileLoader(filename);
        loader.load();
        TspData data = loader.getTspData();
        Result result = new Result(data);
        Kick kick = new Kick(3, 5);
        System.out.println("Original result: ");
        for(int p : result.way){
            System.out.print(p + " ");
        }
        System.out.println();
        System.out.println("Obj. function: "+result.objFuncResult);
        System.out.println();

        for(int i = 0; i < 10; i++) {
            if(kick.shouldExplore(result)) {
                kick.explore(result);
                for(int p : result.way){
                    System.out.print(p + " ");
                }
                System.out.println();
                System.out.println("Obj. function: "+result.objFuncResult);
                System.out.println();
            }
        }
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

    public static void neighbourhoodTest(String filename){
        loader = new FileLoader(filename);
        ArrayList<Map.Entry<Result, Move>> neighbours;

        loader.load();
        TspData data = loader.getTspData();
        Result res = new Result(data);
        Result orig = new Result(res);
        res.calcObjectiveFunction();
//        Neighbourhood n = new CloseSwap();
//        Neighbourhood n = new Insert();
        Neighbourhood n = new Swap();
//        Neighbourhood n = new Invert();

        System.out.println("Original result: ");
        for(int p : res.way){
            System.out.print(p + " ");
        }
        System.out.println();
        System.out.println("Obj. function: "+res.objFuncResult);
        System.out.println();

        if(data.isSymmetric()){
            neighbours = n.getNeighbourhoodSymmetric(res);
        } else {
            neighbours = n.getNeighbourhoodAsymmetric(res);
        }
        boolean containsOriginal = false;
        for(Map.Entry<Result, Move> entry : neighbours){
            boolean isIdenticalAsOriginal = true;
            int i = 0;
            for(int p : entry.getKey().way){
                System.out.print(p + " ");
                if(p != orig.way[i]){
                    isIdenticalAsOriginal = false;
                }
                i++;
            }
            if(isIdenticalAsOriginal){
                containsOriginal = true;
            }
            System.out.println();
            System.out.println("Obj. function: "+entry.getKey().objFuncResult);
            System.out.println();
        }

        if(containsOriginal){
            System.out.println("Original result is contained in neighbourhood");
        }

    }

    public static void movesTest( ){
        //swap move test
        //symmetric
        SwapMove m1 = new SwapMove(0, 1, true);
        SwapMove m2 = new SwapMove(0, 1, true);
        SwapMove m3 = new SwapMove(1, 0, true);
        System.out.println(m1.checkEqual(m2));
        System.out.println(m1.checkEqual(m3));

        //asymmetric
        m1 = new SwapMove(0, 1, false);
        m2 = new SwapMove(0, 1, false);
        m3 = new SwapMove(1, 0, false);
        System.out.println(m1.checkEqual(m2));
        System.out.println(m1.checkEqual(m3));


    }
}
