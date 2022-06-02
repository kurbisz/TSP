package org.example.algorithm.genetic.Fillers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.genetic.selections.util.ResultCompare;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BestAgeFiller implements Filler {

    int copyAm;
    int criticalAge;

    public BestAgeFiller(int copyAm, int criticalAge) {
        this.copyAm = copyAm;
        this.criticalAge = criticalAge;
    }

    @Override
    public void fillPopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation) {
        int maxCities = oldPopulation.size();
        Comparator<GeneticResult> comparator = new ResultCompare();
        oldPopulation.sort(comparator);
        if(copyAm > maxCities) {
            copyAm = maxCities;
        }

        for(int i = 0; i < copyAm && i < oldPopulation.size() && currentPopulation.size() < maxCities; i++) {
            if(oldPopulation.get(i).getAge() < ThreadLocalRandom.current().nextInt(criticalAge)) {
                currentPopulation.add(oldPopulation.get(i));
            }
        }
    }

    @Override
    public Filler copy() {
        return new BestAgeFiller(copyAm, criticalAge);
    }
}
