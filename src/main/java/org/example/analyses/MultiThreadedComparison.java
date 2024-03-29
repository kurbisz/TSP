package org.example.analyses;

import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.ThreeOpt;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MultiThreadedComparison {

    final static int trials = 10;

    public static void calcNN(String fileName, TspData tspData) {
        try {
            File file = new File(fileName);
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("NoOfThreads;TimeInMilis\n");
            ArrayList<Integer> noOfThreads = new ArrayList<>();
            ArrayList<ArrayList<Long>> results = new ArrayList<>();
            for (int i = 1; i <= 16; i++) {
                noOfThreads.add(i);
                results.add(new ArrayList<Long>());
            }

            for (int i = 0; i < trials; i++) {
                System.out.println(fileName + ": " + i);
                for(int j = 0; j < noOfThreads.size(); j++) {
                    NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData, NearestNeighbour.Strategy.UPGRADED_MULTI);
                    nearestNeighbour.setThreadCount(noOfThreads.get(j));
                    nearestNeighbour.calculate();
                    results.get(j).add(nearestNeighbour.getTime());
                }
            }
            for(int i = 0 ; i < results.size(); i++) {
                fileWriter.write(calcAvgMilis(results.get(i)) + ";" + noOfThreads.get(i) + "\n");
            }


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calcKRandom(String fileName, TspData tspData) {
        try {
            File file = new File(fileName);
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("NoOfThreads;TimeInMilis\n");
            ArrayList<Integer> noOfThreads = new ArrayList<>();
            ArrayList<ArrayList<Long>> results = new ArrayList<>();
            for (int i = 1; i <= 16; i++) {
                noOfThreads.add(i);
                results.add(new ArrayList<Long>());
            }

            for (int i = 0; i < trials; i++) {
                System.out.println(fileName + ": " + i);
                for(int j = 0; j < noOfThreads.size(); j++) {
                    KRandom kRandom = new KRandom(tspData, 100000, true);
                    kRandom.setThreads(noOfThreads.get(j));
                    kRandom.calculate();
                    results.get(j).add(kRandom.getTime());
                }
            }
            for(int i = 0 ; i < results.size(); i++) {
                fileWriter.write(calcAvgMilis(results.get(i)) + ";" + noOfThreads.get(i) + "\n");
            }


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double calcAvgMilis(ArrayList<Long> results) {
        long sum = 0;
        for(long result : results) {
            sum += result;
        }
        return (double)sum / (double)results.size()/100000.0;
    }

}
