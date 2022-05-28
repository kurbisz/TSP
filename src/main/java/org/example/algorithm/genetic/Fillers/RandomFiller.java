package org.example.algorithm.genetic.Fillers;

import org.example.algorithm.genetic.GeneticResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFiller implements Filler {


    @Override
    public void fillPopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation) {
        int expectedSize = oldPopulation!=null ? oldPopulation.size() : 0;
        ArrayList<Integer> usedIndexes = new ArrayList<>();
        while(currentPopulation.size() < expectedSize){
            int ind;
            do{
                ind = ThreadLocalRandom.current().nextInt(0, expectedSize);
            }while(usedIndexes.contains(ind));
            currentPopulation.add(oldPopulation.get(ind));
            usedIndexes.add(ind);
        }
    }

    @Override
    public Filler copy() {
        return new RandomFiller();
    }
}
