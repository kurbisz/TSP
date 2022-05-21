package org.example.algorithm.genetic.Evaluations;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public interface Evaluator {

    List<GeneticResult> evaluatePopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation);

    Evaluator copy();
}
