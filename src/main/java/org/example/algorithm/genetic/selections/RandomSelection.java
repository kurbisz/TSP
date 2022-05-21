package org.example.algorithm.genetic.selections;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.List;

public class RandomSelection implements Selection {

    int pairCount;

    /**
     * Domyślny konstruktor
     * ustawia ilość par do wybrania na -1, co skutkuje tym, że
     * zostanie wybranych n/2 par (n - wielkość problemu)
     * (finalne pairCount zostanie ustawione w funkcji getParents)
     */
    public RandomSelection(){
        this.pairCount = -1;
    }

    /**
     * Konstruktor pozwalający na ustawienie ilości par do wybrania
     * @param pairCount - ilość par do wybrania
     */
    public RandomSelection(int pairCount) {
        this.pairCount = pairCount;
    }

    @Override
    public List<Pair> getParents(List<GeneticResult> population) {
        if (pairCount == -1) {
            pairCount = population.size()/2;
        }
        List<Pair> parents = new ArrayList<>();

        for (int i = 0; i < pairCount; i++) {
            int index1 = (int) (Math.random() * population.size());
            int index2;
            do{
                index2 = (int) (Math.random() * population.size());
            } while (index1 == index2);
            parents.add(new Pair(population.get(index1), population.get(index2)));
        }
        return parents;
    }

    @Override
    public Selection copy() {
        return new RandomSelection(pairCount);
    }
}
