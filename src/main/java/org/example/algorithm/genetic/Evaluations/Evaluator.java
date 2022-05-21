package org.example.algorithm.genetic.Evaluations;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public interface Evaluator {

    List<GeneticResult> evaluatePopulation(List<GeneticResult> population);

    Evaluator copy();
}
