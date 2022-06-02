package org.example.algorithm.genetic.Fillers;

import org.example.algorithm.genetic.GeneticResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AgeFiller implements Filler{
    int criticalAge;

    public AgeFiller(int criticalAge) {
        this.criticalAge = criticalAge;
    }

    @Override
    public void fillPopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation) {
        int expectedSize = oldPopulation.size();

        ArrayList<Integer> usedIndexes = new ArrayList<>();

        while(currentPopulation.size() < expectedSize){
            int ind = ThreadLocalRandom.current().nextInt(0, expectedSize);
            if(!usedIndexes.contains(ind) && oldPopulation.get(ind).getAge() < ThreadLocalRandom.current().nextInt(criticalAge)){
                currentPopulation.add(oldPopulation.get(ind));
                usedIndexes.add(ind);
            }
        }
    }

    @Override
    public Filler copy() {
        return new AgeFiller(criticalAge);
    }
}
