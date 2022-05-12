package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Result;

import java.util.List;

public interface Crossover {

	/**
	 *
	 * @param oldPopulation list of all old results which has to be crossed
	 * @return new list of results which will be mutated
	 */
	List<GeneticResult> getNewPopulation(List<GeneticResult> oldPopulation);

}
