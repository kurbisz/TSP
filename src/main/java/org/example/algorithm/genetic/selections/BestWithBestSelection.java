package org.example.algorithm.genetic.selections;

import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.genetic.selections.util.ResultCompare;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Wybieranie par (1,2), (3,4), ... według najlepszego wyniku
public class BestWithBestSelection implements Selection {

    public int pairCount;

    public BestWithBestSelection(int pairCount) {
        this.pairCount = pairCount;
    }

    @Override
    public List<Pair> getParents(List<GeneticResult> population) {
        List<Pair> parents = new ArrayList<>();
        Comparator<GeneticResult> comparator = new ResultCompare();
        population.sort(comparator);
        if(pairCount % 2 == 1) pairCount--;
        for(int i = 0; i < pairCount; i+=2) {
            Pair newPair = new Pair(population.get(2*i), population.get(2*i+1));
            parents.add(newPair);
        }
        return parents;
    }

    @Override
    public Selection copy() {
        return new BestWithBestSelection(pairCount);
    }
}
