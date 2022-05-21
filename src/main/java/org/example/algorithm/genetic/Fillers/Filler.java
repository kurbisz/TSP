package org.example.algorithm.genetic.Fillers;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public interface Filler {

    void fillPopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation);

    Filler copy();
}
