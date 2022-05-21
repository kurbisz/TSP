package org.example.algorithm.genetic.selections;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleBestSelection implements Selection {

    static class resultCompare implements Comparator<GeneticResult> {

        @Override
        public int compare(GeneticResult o1, GeneticResult o2) {
            int res1 = o1.objFuncResult;
            int res2 = o2.objFuncResult;
            return Integer.compare(res1, res2);
        }
    }

    @Override
    public List<Pair> getParents(List<GeneticResult> population) {
        List<Pair> parents = new ArrayList<>();
        Comparator<GeneticResult> comparator = new resultCompare();
        population.sort(comparator);
        int pairCount = population.size() / 2;
        int maxInd = population.size() / 3;
        for(int i = 0 ; i < pairCount ; i++){
            int ind1 = ThreadLocalRandom.current().nextInt(0, maxInd);
            int ind2;
            do{
                ind2 = ThreadLocalRandom.current().nextInt(0, maxInd);
            }while(ind1 == ind2);

            Pair newPair = new Pair(population.get(ind1), population.get(ind2));
            if(!parents.contains(newPair)){
                parents.add(newPair);
            }
        }
        return parents;
    }

    @Override
    public Selection copy() {
        return new SimpleBestSelection();
    }
}
