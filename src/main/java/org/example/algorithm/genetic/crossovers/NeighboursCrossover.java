package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NeighboursCrossover implements Crossover {

	int am;
	private ThreadLocalRandom r = ThreadLocalRandom.current();

	/**
	 * If list of possible neighbours is empty then find closest path
	 * Example:
	 *			start: 9   am: 1
	 * par1 	9 3 0 8 5 7 2 6 4 1
	 * par2		8 0 3 4 1 9 6 2 5 7
	 * res		9 1 4 6 2 5 7 8 0 3
	 * @param am amount of neighbours which will be compared
	 */
	public NeighboursCrossover(int am) {
		this.am = am;
	}

	@Override
	public List<GeneticResult> getNewPopulation(List<Pair> parentsList) {
		List<GeneticResult> list = new ArrayList<>();
		for(Pair pair : parentsList) {
			list.add(getNewGeneticResult(pair.getParent1(), pair.getParent2()));
		}
		return list;
	}


	private GeneticResult getNewGeneticResult(GeneticResult result1, GeneticResult result2) {
		int n = result1.getProblemSize();
		boolean used[] = new boolean[n];
		GeneticResult newRes = result1.clone();
		newRes.way[0] = newRes.way[r.nextInt(n)];
		used[newRes.way[0]] = true;
//		System.out.println("start: " + newRes.way[0] + "   am: " + am);
//		System.out.println(result1.toString());
//		System.out.println(result2.toString());
		for(int i = 1; i < n; i++) {
			List<Integer> neighbours = new ArrayList<>();
			addNeighbours(result1, newRes.way[i-1], neighbours, used);
			addNeighbours(result2, newRes.way[i-1], neighbours, used);
			int closest = getClosest(result1, newRes.way[i-1], neighbours);
			if(closest == -1) {
				for(int k = 0; k < n; k++) {
					if(!used[k]) {
						neighbours.add(k);
					}
				}
				closest = getClosest(result1, newRes.way[i-1], neighbours);
			}
//			System.out.println("SIZE: " + neighbours.size());
			used[closest] = true;
			newRes.way[i] = closest;
		}
//		System.out.println(newRes.toString());
//		System.out.println(" ");

		newRes.calcObjectiveFunction();
		return newRes;
	}

	private void addNeighbours(GeneticResult result, int actual, List<Integer> res, boolean[] used) {
		int n = result.getProblemSize();
		for(int i = 0; i < n; i++) {
			if(result.way[i] == actual) {
				for(int j = 1; j <= am; j++) {
					Integer left = result.way[(n+i-j)%n];
					Integer right = result.way[(i+j)%n];
					if(!used[left] && !res.contains(left)) res.add(left);
					if(!used[right] && !res.contains(right)) res.add(right);
				}
				break;
			}
		}
	}

	private int getClosest(GeneticResult result, int actualCity, List<Integer> list) {
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

	@Override
	public Crossover copy() {
		return new NeighboursCrossover(am);
	}
}
