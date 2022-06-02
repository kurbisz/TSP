package org.example.algorithm.genetic.Evaluations;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public class AgeEvaulator implements Evaluator{

    @Override
    public List<GeneticResult> evaluatePopulation(List<GeneticResult> currentPopulation, List<GeneticResult> oldPopulation) {
        for(GeneticResult g: currentPopulation){
            g.incrementAge();
        }
        return currentPopulation;
    }

    @Override
    public Evaluator copy() {
        return new AgeEvaulator();
    }
}
