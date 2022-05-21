package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

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

//	@Override
//	public List<GeneticResult> getNewPopulation(List<Pair> parentsList) {
//		List<GeneticResult> list = new ArrayList<>();
//		for(GeneticResult result : parentsList) {
//			for(GeneticResult result2 : parentsList) {
//				if(!result.equals(result2)
//						&& result.getChanceToCross() < ThreadLocalRandom.current().nextDouble()
//						&& result2.getChanceToCross() < ThreadLocalRandom.current().nextDouble()) {
//					list.add(getNewGeneticResult(result, result2));
//				}
//			}
//		}
//		return list;
//	}


	@Override
	public List<GeneticResult> getNewPopulation(List<Pair> parentsList) {
		return null;
	}

	private GeneticResult getNewGeneticResult(GeneticResult result1, GeneticResult result2) {
		// TODO CHECK
		GeneticResult newRes = result1.clone();
		int am[] = new int[newRes.getProblemSize()];
		for(int i = from; i <= to; i++) {
			newRes.way[i] = result2.way[i];
		}
		for(int i = 0; i < newRes.getProblemSize(); i++) {
			am[newRes.way[i]]++;
		}
		int over = 0;
		for(int i = 0; i < newRes.getProblemSize(); i++) {
			if(am[newRes.way[i]] != 2) continue;
			while(am[newRes.way[over]] != 0) over++;
			newRes.way[over] = result1.way[i];
		}
		return newRes;
	}

	@Override
	public Crossover copy() {
		return new PartialCrossover(from, to);
	}
}
