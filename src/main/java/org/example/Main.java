package org.example;

import org.example.algorithm.*;
import org.example.analyses.AlgorithmsComparison;
import org.example.analyses.DifferentKRandom;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

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

//        Algorithm algorithm = new NearestNeighbour(tspData);
//        Result r = algorithm.calculate();
//        System.out.println("Wynik: " + r.calcObjectiveFunction());
//        System.out.println("Rozwiazanie: " + r);
//        Drawer drawer = new Drawer();
//        drawer.showResult(r);

//        DifferentKRandom.calc("kRandom.csv", tspData);
//        DifferentKRandom.calcAverage("kRandomAverage.csv", tspData);

        List<TspData> list = new ArrayList<>();
        list.add(tspData);
        AlgorithmsComparison.compareKRAndNN("compareKrAndNn.csv", list);

        //System.out.println(algorithm.objectiveFunction());
    }

}
