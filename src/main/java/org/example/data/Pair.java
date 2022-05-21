package org.example.data;

import org.example.algorithm.genetic.GeneticResult;

public class Pair {
    GeneticResult parent1;
    GeneticResult parent2;

    public Pair(GeneticResult parent1, GeneticResult parent2) {
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public GeneticResult getParent1() {
        return parent1;
    }

    public GeneticResult getParent2() {
        return parent2;
    }
}
