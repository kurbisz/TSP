package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.KRandom;
import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa, która generuje populację startową
 * z losowymi osobnikami uzyskanymi za pomocą KRandom
 */
public class RandomPopulation implements PopulationGenerator {

    int populationSize;

    /**
     * Domyślny konstruktor
     * ustawia rozmiar populacji na 100
     */
    public RandomPopulation() {
        populationSize = 100;
    }

    /**
     * Konstruktor pozwalający na ustawienie rozmiaru populacji
     * @param populationSize - żądany rozmiar zwracanej populacji
     */
    public RandomPopulation(int populationSize) {
        this.populationSize = populationSize;
    }

    @Override
    public List<GeneticResult> getNewPopulation(TspData tspData) {
        ArrayList<GeneticResult> population = new ArrayList<>();

        KRandom kRandom = new KRandom(tspData, 100, true);
        kRandom.setThreads(8);

        for(int i = 0; i < populationSize; i++) {
            Result res = kRandom.calculate();
            population.add(new GeneticResult(res));
        }

        return population;
    }

    @Override
    public PopulationGenerator copy() {
        return new RandomPopulation(populationSize);
    }
}
