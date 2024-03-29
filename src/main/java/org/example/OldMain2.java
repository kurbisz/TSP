package org.example;

import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.tabuSearchAnalyses.AlgorithmsComparisonTS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OldMain2 {

    public static FileLoader loader;
    private static String folder = "dane" + File.separator;
    private static String[] files = {folder + "d198.tsp", folder + "metro1.tsp", folder + "metro2.tsp",
                                    folder + "ft53.atsp", folder + "metro3.tsp", folder + "kroB100.tsp",
                                    folder + "pr299.tsp", folder + "metro4.tsp", folder + "metro8.tsp",
                                    folder + "ry48p.atsp"};
    public static String[] names = {"S198", "S292", "S124", "A53", "S170", "S100", "S299", "S222", "S91", "A48"};
    public static int[] best = {15780, 48912, 50801, 6905, 1473, 22141, 48191, 1326, 2720, 14422};

    /**
     *
     * @param args array with 1 element which is filename
     */
    public static void main(String[] args) throws IOException {
//        if(args.length != 1) {
//            System.out.println("Argument 1 must be filename!");
//            return;
//        }
//        String file = files[6];
//
//        loaderTest(file);
//
//        TspData data = loader.getTspData();
//        NearestNeighbour nearestNeighbour = new NearestNeighbour(data, NearestNeighbour.Strategy.UPGRADED_MULTI);
//        nearestNeighbour.setThreadCount(6);
//        Result startingRes = nearestNeighbour.calculate();
//        draw(startingRes);

//        TabooSearch2 ts = new TabooSearch2(data, startingRes, true, new SwapTabooList(7, data.getSize()), new Swap(), new IterationsStop(100), null, 1);
//        ts.setAsync(6);
//        Result endRes = ts.calculate();
//        System.out.println("At the beginning: " + startingRes.calcObjectiveFunction());
//        System.out.println("At the end: " + endRes.calcObjectiveFunction());

        FileLoader fileLoader = new FileLoader(files[2]);
        fileLoader.load();
        List<TspData> list = new ArrayList<>();
        for(int i = 0; i < files.length; i++) {
            fileLoader.setFileName(files[i]);
            fileLoader.load();
            TspData data = fileLoader.getTspData();
            data.setName(names[i]);
            list.add(data);
        }
//        fileLoader.setFileName(files[8]);
//        fileLoader.load();
//        TspData d = fileLoader.getTspData();
//        if(d instanceof EucTspData) {
//            Result res = (new TwoOpt(d)).calculate();
//            draw(res);
//        }
//        AlgorithmsComparisonTS.compareOperations("operations.csv", list);
//        AlgorithmsComparisonTS.compareTabooListSize("tabooListSize.csv", list);
//        AlgorithmsComparisonTS.compareAspiration("aspiration.csv", list);
//        AlgorithmsComparisonTS.compareNeighbourhood("neighbourhood.csv", list);
//        AlgorithmsComparisonTS.compareAsynchronous("asynchronous.csv", list);
//        AlgorithmsComparisonTS.compareStart("start.csv", list);
//        AlgorithmsComparisonTS.compareLongTermList("longTermList.csv", list, false);
//        AlgorithmsComparisonTS.compareLongTermList("longTermListKick.csv", list, true);
        System.out.println(list.size());
        AlgorithmsComparisonTS.compareExploreFunctions("exploreFunctions.csv", list, 5);
        AlgorithmsComparisonTS.compareExploreFunctions("exploreFunctions15.csv", list, 15);
//        AlgorithmsComparisonTS.compareTime("progress100.csv", list, 100);
//        AlgorithmsComparisonTS.compareProgress("progress500.csv", list, 500);
//        AlgorithmsComparisonTS.compareTime("progress1000.csv", list, 1000);
//        fileLoader.setFileName(files[0]);
//        fileLoader.load();
//        TspData data = fileLoader.getTspData();
//        AlgorithmsComparisonTS.compareTabooListTime("tabooListTime198.csv", data);
//        fileLoader.setFileName(files[8]);
//        fileLoader.load();
//        data = fileLoader.getTspData();
//        AlgorithmsComparisonTS.compareTabooListTime("tabooListTime91.csv", data);
//        fileLoader.setFileName(files[9]);
//        fileLoader.load();
//        TspData data = fileLoader.getTspData();
//        AlgorithmsComparisonTS.compareTabooListTime("tabooListTime48.csv", data);


//        FileLoader fileLoader = new FileLoader("metro8.tsp");
//        fileLoader.load();
//        TspData tspData = fileLoader.getTspData();
//        TwoOpt twoOpt = new TwoOpt(tspData);
//        Result res = twoOpt.calculate();
//
//        Drawer drawer = new Drawer(res.problem.getName());
//        drawer.showResult(res);

    }

    private static void draw(Result res) {
        Drawer drawer = new Drawer(res.problem.getName());
        drawer.showResult(res);
    }

    public static void loaderTest(String filename){
        loader = new FileLoader(filename);
        loader.load();
        System.out.println(loader.getTspData().toString());
    }

}
