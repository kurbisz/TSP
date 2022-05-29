package org.example.algorithm.genetic.selections;

import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.genetic.selections.util.ResultCompare;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleBestSelection implements Selection {

    public int pairCount, maxInd;


    public SimpleBestSelection(int pairCount, int maxInd) {
        this.pairCount = pairCount;
        this.maxInd = maxInd;
    }

    @Override
    public List<Pair> getParents(List<GeneticResult> population) {
        List<Pair> parents = new ArrayList<>();
        Comparator<GeneticResult> comparator = new ResultCompare();
        population.sort(comparator);
        for(int i = 0 ; i < pairCount ; i++){
            int ind1 = ThreadLocalRandom.current().nextInt(0, Math.min(population.size(), maxInd));
            int ind2;
            do{
                ind2 = ThreadLocalRandom.current().nextInt(0, Math.min(population.size(), maxInd));
            }while(ind1 == ind2);

            Pair newPair = new Pair(population.get(ind1), population.get(ind2));
            // TODO Do usuniecia albo edycji bo tu jest tworzona nowa klasa czyli nigdy nie jest w liscie
            if(!parents.contains(newPair)){
                parents.add(newPair);
            }
        }
        return parents;
    }

    @Override
    public Selection copy() {
        return new SimpleBestSelection(pairCount, maxInd);
    }
}
