package org.example;

import org.example.algorithm.Algorithm;
import org.example.algorithm.KRandom;
import org.example.algorithm.SimpleAlgorithm;
import org.example.algorithm.TwoOpt;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.IOException;

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

        Algorithm algorithm = new TwoOpt(tspData, false);
        Result r = algorithm.calculate();
        System.out.println("Wynik: " + r.calcObjectiveFunction());
        System.out.println("Rozwiazanie: " + r);
        //System.out.println(algorithm.objectiveFunction());
    }

}
