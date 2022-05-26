package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.KRandom;
import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.List;


public class HammingRandomPopulation implements PopulationGenerator {

    int populationSize;
    double minPercentage;

    public HammingRandomPopulation() {
        this(100, 0.985);
    }

    public HammingRandomPopulation(int populationSize, double minPercentage) {
        this.populationSize = populationSize;
        this.minPercentage = minPercentage;
    }

    @Override
    public List<GeneticResult> getNewPopulation(TspData tspData) {
        ArrayList<GeneticResult> population = new ArrayList<>();

        KRandom kRandom = new KRandom(tspData, 100, true);
        kRandom.setThreads(8);

        int cnt = 0;
        while(population.size() < populationSize) {
            cnt++;
            Result res = kRandom.calculate();
            boolean cancel = false;
            for(GeneticResult geneticResult : population) {
                if(isClose(res, geneticResult)) {
                    cancel = true;
                    break;
                }
            }
            if(cancel) continue;
            population.add(new GeneticResult(res));
        }
        System.out.println(cnt);

        for(int i = 0; i < populationSize; i++) {
            Result res = kRandom.calculate();
            population.add(new GeneticResult(res));
        }

        return population;
    }

    @Override
    public PopulationGenerator copy() {
        return new HammingRandomPopulation(populationSize, minPercentage);
    }

    private boolean isClose(Result result1, Result result2) {
        int am = 0;
        for(int i = 0; i < result1.getProblemSize(); i++) {
            if(result1.way[i] != result2.way[i]) am++;
        }
//        System.out.println(result1 + "    " + result2);
        return am < minPercentage * result1.getProblemSize();
    }

}
