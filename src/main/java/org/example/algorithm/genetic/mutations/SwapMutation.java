package org.example.algorithm.genetic.mutations;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Result;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwapMutation implements Mutation {

	private double chance;
	private int swapAm;
	private ThreadLocalRandom r = ThreadLocalRandom.current();

	public SwapMutation() {
		this(0.8, 1);
	}

	public SwapMutation(double chance, int swapAm) {
		this.chance = chance;
		this.swapAm = swapAm;
	}

	@Override
	public List<GeneticResult> getMutatedPopulation(List<GeneticResult> population) {
		for(GeneticResult result : population) {
			if(r.nextDouble() < chance) {
				for(int i = 0; i < swapAm; i++) {
					swap(result);
				}
			}
		}
		return population;
	}

	private void swap(GeneticResult result) {
		int i = r.nextInt(result.getProblemSize());
		int j = i;
		while(i == j) j = r.nextInt(result.getProblemSize());
		swap(result, i, j);
	}

	private void swap(GeneticResult result, int i, int j) {
		int tmp = result.way[i];
		result.way[i] = result.way[j];
		result.way[j] = tmp;
		result.calcObjectiveFunction();
	}

	@Override
	public Mutation copy() {
		return new SwapMutation(chance, swapAm);
	}
}
