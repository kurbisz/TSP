package org.example;

import org.example.algorithm.TwoOpt;
import org.example.data.EucTspData;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.tabooSearchAnalyses.AlgorithmsComparisonTS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

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

        FileLoader fileLoader = new FileLoader(files[2]);
        fileLoader.load();
        TspData data = fileLoader.getTspData();
        data.setName(names[2]);

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
