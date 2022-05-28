package org.example.algorithm.genetic.Fillers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.genetic.selections.util.ResultCompare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BestFiller implements Filler {

    private int copyAm, maxCities;

    /**
     * N best cities will be copied to the new list - list will not be the same size!
     * @param copyAm amount of best cities
     * @param maxCities maximum amount of cities on list
     */
    public BestFiller(int copyAm, int maxCities) {
        this.copyAm = copyAm;
        this.maxCities = maxCities;
    }

    @Override
    public void fillPopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation) {
        int expectedSize = oldPopulation!=null ? oldPopulation.size() : 0;

        Comparator<GeneticResult> comparator = new ResultCompare();
        oldPopulation.sort(comparator);
        for(int i = 0; i < copyAm && currentPopulation.size() < maxCities; i++) {
            currentPopulation.add(oldPopulation.get(i));
        }
    }

    @Override
    public Filler copy() {
        return new BestFiller(copyAm, maxCities);
    }
}
