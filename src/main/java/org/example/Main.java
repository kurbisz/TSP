package org.example;

import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.taboo.Neighbourhoods.Invert;
import org.example.algorithm.taboo.Neighbourhoods.Swap;
import org.example.algorithm.taboo.TabooSearch2;
import org.example.algorithm.taboo.stopFunctions.IterationsStop;
import org.example.algorithm.taboo.tabooList.BasicTabooList;
import org.example.algorithm.taboo.tabooList.InvertTabooList;
import org.example.algorithm.taboo.tabooList.SwapTabooList;
import org.example.analyses.*;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static FileLoader loader;
    private static String folder = "dane" + File.separator;
    private static String[] files = {folder + "d198.tsp", folder + "d657.tsp", folder + "d1291.tsp",
                                    folder + "ft53.atsp", folder + "ftv35.atsp", folder + "kroB100.tsp",
                                    folder + "pr299.tsp", folder + "rbg323.atsp", folder + "rbg443.atsp",
                                    folder + "ry48p.atsp"};
    public static String[] names = {"S198", "S657", "S1291", "A53", "A35", "S100", "S299", "A323", "A443", "A48"};
    public static int[] best = {15780, 48912, 50801, 6905, 1473, 22141, 48191, 1326, 2720, 14422};

    /**
     *
     * @param args array with 1 element which is filename
     */
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Argument 1 must be filename!");
            return;
        }
        String file = files[6];

        loaderTest(file);

        TspData data = loader.getTspData();
        NearestNeighbour nearestNeighbour = new NearestNeighbour(data, NearestNeighbour.Strategy.UPGRADED_MULTI);
        nearestNeighbour.setThreadCount(6);
        Result startingRes = nearestNeighbour.calculate();
//        draw(startingRes);

        TabooSearch2 ts = new TabooSearch2(data, startingRes, true, new SwapTabooList(7, data.getSize()), new Swap(), new IterationsStop(100), null, 1);
//        ts.setAsync(6);
        Result endRes = ts.calculate();
        System.out.println("At the beginning: " + startingRes.calcObjectiveFunction());
        System.out.println("At the end: " + endRes.calcObjectiveFunction());

    }

    public static void loaderTest(String filename){
        loader = new FileLoader(filename);
        loader.load();
        System.out.println(loader.getTspData().toString());
    }

}
