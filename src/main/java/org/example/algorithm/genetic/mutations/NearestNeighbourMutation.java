package org.example.algorithm.genetic.mutations;

import org.example.algorithm.genetic.GeneticResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NearestNeighbourMutation implements Mutation {

	int from, to;
	double chance;
	private ThreadLocalRandom r = ThreadLocalRandom.current();

	public NearestNeighbourMutation(double chance, int from, int to) {
		this.chance = chance;
		this.from = from;
		this.to = to;
	}

	@Override
	public List<GeneticResult> getMutatedPopulation(List<GeneticResult> population) {
		for(GeneticResult result : population) {
			if(r.nextDouble() < chance) {
				mutate(result);
			}
		}
		return population;
	}

	@Override
	public Mutation copy() {
		return new NearestNeighbourMutation(chance, from, to);
	}


	private void mutate(GeneticResult result) {
		GeneticResult newResult = result.clone();
//		System.out.println("mut: " + newResult);
		int n = result.getProblemSize();
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < from; i++) list.add(newResult.way[i]);
		for(int i = to+1; i < n; i++) list.add(newResult.way[i]);
		int j = (to+1)%n;
		while(j != from) {
			newResult.way[j] = getClosest(result, list, newResult.way[(n+j-1)%n]);
			list.remove((Integer) newResult.way[j]);
			j = (j+1)%n;
		}
		result.calcObjectiveFunction();
//		System.out.println("mut: " + newResult);
//		System.out.println("");
	}

	private Integer getClosest(GeneticResult result, List<Integer> list, int actualCity) {
		int min = -1;
		int minDist = Integer.MAX_VALUE;
		for(Integer city : list) {
			int distance = result.problem.getDistance(actualCity, city);
			if(distance < minDist) {
				min = city;
				minDist = distance;
			}
		}
		return min;
	}


}
