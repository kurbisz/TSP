package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PartialCrossover implements Crossover {

	int from, to;

	/**
	 * For example from = 1, to = 4 will cross part '2 1 4 3'
	 * from result '0 2 1 4 3 5 6 7' with another result and repair
	 * indexes which are duplicated in ascending order
	 * @param from point from genetic result will be transformed
	 * @param to point to genetic result will be transformed
	 */
	public PartialCrossover(int from, int to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public List<GeneticResult> getNewPopulation(List<GeneticResult> oldPopulation) {
		List<GeneticResult> list = new ArrayList<>();
		for(GeneticResult result : oldPopulation) {
			for(GeneticResult result2 : oldPopulation) {
				if(!result.equals(result2)
						&& result.getChanceToCross() < ThreadLocalRandom.current().nextDouble()
						&& result2.getChanceToCross() < ThreadLocalRandom.current().nextDouble()) {

				}
			}
		}
		return list;
	}

	private GeneticResult getNewGeneticResult(GeneticResult result1, GeneticResult result2) {
		GeneticResult newRes = result1.clone();
		return newRes;
	}

}
