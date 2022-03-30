package org.example;

import org.example.algorithm.*;
import org.example.analyses.AlgorithmsComparison;
import org.example.analyses.DifferentKRandom;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String folder = "dane" + File.separator;
    private static String[] files = {folder + "d198.tsp", folder + "d657.tsp", folder + "d1291.tsp",
                                    folder + "ft53.atsp", folder + "ftv35.atsp", folder + "kroB100.tsp",
                                    folder + "pr299.tsp", folder + "rbg323.atsp", folder + "rbg443.atsp",
                                    folder + "ry48p.atsp"};
    public static String[] names = {"S198", "S657", "S1291", "A53", "A35", "S100", "S299", "A323", "A443", "A48"};

    /**
     *
     * @param args array with 1 element which is filename
     */
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Argument 1 must be filename!");
            return;
        }
        FileLoader fileLoader = new FileLoader(args[0]);
        fileLoader.load();

        TspData tspData = fileLoader.getTspData();
        //System.out.println(tspData.toString());

        List<TspData> list = new ArrayList<>();
        for(int i = 0; i < files.length; i++) {
            fileLoader.setFileName(files[i]);
            fileLoader.load();
            TspData data = fileLoader.getTspData();
            data.setName(names[i]);
            list.add(data);
        }
        //AlgorithmsComparison.compareKRAndNN("compareKrAndNn.csv", list);
        //AlgorithmsComparison.compareTwoOptAndNN("compareTwoOptAndNn.csv", list);
        AlgorithmsComparison.compareKrAndTwoOpt("compareKrAndTwoOpt.csv", list);


        //System.out.println("Liczba dostepnych procesorow: " + Runtime.getRuntime().availableProcessors());

//        KRandom algorithm = new KRandom(tspData, 1000000, true);
//        algorithm.setThreads(8);
//        Result r = algorithm.calculate();
//        System.out.println("Wynik: " + r.calcObjectiveFunction());
//        System.out.println("Rozwiazanie: " + r);
//        System.out.println("Czas: " + algorithm.getTime());
//        Drawer drawer = new Drawer();
//        drawer.showResult(r);

//        DifferentKRandom.calc("kRandom.csv", tspData);
        //DifferentKRandom.calcAverage("kRandomAverage.csv", tspData);

        //System.out.println(algorithm.objectiveFunction());
    }

}
