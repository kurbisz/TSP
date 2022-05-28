package org.example.algorithm.genetic.longterm;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public interface LongTermChecker {

	boolean check(List<GeneticResult> currentPopulation, GeneticResult bestResult);

	LongTermChecker copy();

}
