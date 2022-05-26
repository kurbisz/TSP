package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PartialCrossover implements Crossover {

	boolean bothChildren;
	int from, to;

	/**
	 * Example:
	 * 				from: 4    to: 5
	 * par1:		3 4 9 1 2 6 8 0 5 7
	 * par2:		5 6 2 8 0 1 9 3 7 4
	 * result:		3 4 9 2 0 1 8 6 5 7
	 * @param from point from genetic result will be transformed
	 * @param to point to genetic result will be transformed
	 * @param bothChildren true if there should be added both children
	 */
	public PartialCrossover(int from, int to, boolean bothChildren) {
		this.from = from;
		this.to = to;
		this.bothChildren  = bothChildren;
	}

	@Override
	public List<GeneticResult> getNewPopulation(List<Pair> parentsList) {
		List<GeneticResult> list = new ArrayList<>();
		for(Pair pair : parentsList) {
			GeneticResult res1 = getNewGeneticResult(pair.getParent1(), pair.getParent2());
			list.add(res1);
			if(bothChildren) {
				GeneticResult res2 = getNewGeneticResult(pair.getParent2(), pair.getParent1());
				if (!res1.equals(res2)) list.add(res2);
			}
		}
		return list;
	}


	private GeneticResult getNewGeneticResult(GeneticResult result1, GeneticResult result2) {
//		System.out.println("from: " + from + "    to: " + to);
//		System.out.println(result1.toString());
//		System.out.println(result2.toString());
		GeneticResult newRes = result1.clone();
		int am[] = new int[newRes.getProblemSize()];
		for(int i = from; i <= to; i++) {
			newRes.way[i] = result2.way[i];
		}
		for(int i = 0; i < newRes.getProblemSize(); i++) {
			am[newRes.way[i]]++;
		}
//		System.out.println(newRes.toString());
		int over = 0;
		for(int i = 0; i < newRes.getProblemSize(); i++) {
			if(i >= from && i <= to) continue;
			if(am[newRes.way[i]] != 2) continue;
			while(over < newRes.getProblemSize() && am[over] != 0) over++;
			if(over >= newRes.getProblemSize()) break;
			newRes.way[i] = over;
			am[over]--;
		}
//		System.out.println(newRes.toString());
//		System.out.println(" ");
		newRes.calcObjectiveFunction();
		return newRes;
	}

	@Override
	public Crossover copy() {
		return new PartialCrossover(from, to, bothChildren);
	}
}
