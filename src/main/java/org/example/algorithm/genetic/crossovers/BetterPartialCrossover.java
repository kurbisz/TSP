package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BetterPartialCrossover implements Crossover {

	int from, to;

	/**
	 * For example from = 1, to = 4 will cross part '2 1 4 3'
	 * from result '0 2 1 4 3 5 6 7' with another result and repair
	 * indexes which are duplicated in THE SAME order as second result
	 * @param from point from genetic result will be transformed
	 * @param to point to genetic result will be transformed
	 */
	public BetterPartialCrossover(int from, int to) {
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
					list.add(getNewGeneticResult(result, result2));
				}
			}
		}
		return list;
	}

	private GeneticResult getNewGeneticResult(GeneticResult result1, GeneticResult result2) {
		// TODO EDIT
		GeneticResult newRes = result1.clone();
		int am[] = new int[newRes.getProblemSize()];
		for(int i = from; i <= to; i++) {
			newRes.way[i] = result2.way[i];
		}
		for(int i = 0; i < newRes.getProblemSize(); i++) {
			am[newRes.way[i]]++;
		}
		int over = 0;
		int j = 0;
		for(int i = 0; i < newRes.getProblemSize(); i++) {
			while(am[result2.way[j]] != 0) j++;
			if(am[newRes.way[i]] != 2) continue;
			while(am[newRes.way[over]] != 0) over++;
			newRes.way[over] = result1.way[i];
		}
		return newRes;
	}

}
