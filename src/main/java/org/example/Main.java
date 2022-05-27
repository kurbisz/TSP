package org.example;

import org.example.algorithm.TwoOpt;
import org.example.algorithm.genetic.GeneticAlgorithm;
import org.example.algorithm.genetic.crossovers.NeighboursCrossover;
import org.example.algorithm.genetic.mutations.SwapMutation;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.drawer.Drawer;
import org.example.random.EuclideanTSPGen;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static FileLoader loader;
    private static String fol = "dane" + File.separator;
    private static String[] files = {fol + "A38.atsp", fol + "A43.atsp", fol + "A44.atsp",
            fol + "A47.atsp", fol + "A48.atsp", fol + "A53.atsp", fol + "A55.atsp", fol + "A64.atsp",
            fol + "A70.atsp", fol + "A124.atsp", fol + "S41.tsp", fol + "S46.tsp", fol + "S54.tsp",
            fol + "S64.tsp", fol + "S80.tsp", fol + "S88.tsp", fol + "S104.tsp", fol + "S113.tsp",
            fol + "S114.tsp", fol + "S116.tsp"};
    public static String[] names = new String[files.length];
    public static int[] best = {1530, 5620, 1613, 1776, 14422, 6905, 1608, 1839, 38673, 36230};

    /**
     *
     * @param args array with 1 element which is filename
     */
    public static void main(String[] args) throws IOException {
        initNames();

        String f = "test2Metro.tsp";
//        String f = files[1];
        FileLoader fileLoader = new FileLoader(f);
        fileLoader.load();
        TspData data = fileLoader.getTspData();
        data.setName(f);

        System.out.println(new TwoOpt(data).calculate().calcObjectiveFunction());
        int n = data.getSize();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(data);
//        geneticAlgorithm.setStartPopulation(new HammingRandomPopulation());
//        geneticAlgorithm.setStartPopulation(new RandomAndTwoOptPopulation());
//        geneticAlgorithm.setCrossoverTemplate(new PartlyOrderCrossover(3, true));
        geneticAlgorithm.setCrossoverTemplate(new NeighboursCrossover(1));
//        geneticAlgorithm.setStopFunctionTemplate(new IterationsStop(15));
        geneticAlgorithm.setMutationTemplate(new SwapMutation(0.8, 1));
//        geneticAlgorithm.setMutationTemplate(new InvertMutation(0.8, 1));
//        geneticAlgorithm.setMutationTemplate(new NearestNeighbourMutation(0.1, n/8, 7*n/8));
//        geneticAlgorithm.setSelection(new BestWithBestSelection());
//        Result result = geneticAlgorithm.calculate();
//        System.out.println(result.objFuncResult);

        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            EuclideanTSPGen euclideanTSPGen = new EuclideanTSPGen(50 + r.nextInt(500), 50 + r.nextInt(500));
            int rand = 40 + r.nextInt(80);
            if (r.nextDouble() < 0.5) euclideanTSPGen.setType(EuclideanTSPGen.Type.METROPOLIS);
            euclideanTSPGen.generate("dane/S" + rand, rand);
        }


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

    private static void initNames() {
        int i = 0;
        for(String file : files) {
            names[i++] = file.replaceAll(".tsp", "");
        }
    }

}
