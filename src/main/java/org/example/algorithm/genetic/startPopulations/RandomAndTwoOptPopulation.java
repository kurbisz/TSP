package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.KRandom;
import org.example.algorithm.TwoOpt;
import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.List;

public class RandomAndTwoOptPopulation implements PopulationGenerator {

    int randomSize, twoOptSize;

    public RandomAndTwoOptPopulation() {
        this(80, 20);
    }


    public RandomAndTwoOptPopulation(int randomSize, int twoOptSize) {
        this.randomSize = randomSize;
        this.twoOptSize = twoOptSize;
    }

    @Override
    public List<GeneticResult> getNewPopulation(TspData tspData) {
        ArrayList<GeneticResult> population = new ArrayList<>();

        KRandom kRandom = new KRandom(tspData, 100, true);
        kRandom.setThreads(8);

        for(int i = 0; i < randomSize; i++) {
            Result res = kRandom.calculate();
            population.add(new GeneticResult(res));
        }

        for(int i = 0; i < twoOptSize; i++) {
            Result res = kRandom.calculate();
            TwoOpt twoOpt = new TwoOpt(tspData);
            twoOpt.setStarting(res);
            res = twoOpt.calculate();
            population.add(new GeneticResult(res));
        }

        return population;
    }

    @Override
    public PopulationGenerator copy() {
        return new RandomAndTwoOptPopulation(randomSize, twoOptSize);
    }
}
