package org.example.algorithm.genetic.mutations;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Result;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InvertMutation implements Mutation {

	private double chance;
	private int invertAm;
	private ThreadLocalRandom r = ThreadLocalRandom.current();

	public InvertMutation() {
		this(0.5, 1);
	}

	public InvertMutation(double chance, int invertAm) {
		this.chance = chance;
		this.invertAm = invertAm;
	}

	@Override
	public List<GeneticResult> getMutatedPopulation(List<GeneticResult> population) {
		for(GeneticResult result : population) {
			if(r.nextDouble() < chance) {
				for(int i = 0; i < invertAm; i++) {
					invert(result);
				}
			}
		}
		return population;
	}

	private void invert(GeneticResult result) {
		int i = r.nextInt(result.getProblemSize());
		int j = i;
		while(i == j) j = r.nextInt(result.getProblemSize());
		reverse(result, i, j);
	}

	private void reverse(GeneticResult result, int i, int j) {
		int n = result.getProblemSize();
		int k = 0;
		int am = j-i;
		if(am < 0) am+=n;
		while(k < am/2+1) {
			int from = k+i;
			if(from >= n) from -= n;
			int to = j-k;
			if(to < 0) to += n;
			int tmp = result.way[from];
			result.way[from] = result.way[to];
			result.way[to] = tmp;
			k++;
		}
		result.calcObjectiveFunction();
	}

	@Override
	public Mutation copy() {
		return new InvertMutation(chance, invertAm);
	}
}
