package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class PartlyOrderCrossover implements Crossover {

	boolean bothChildren;
	int randomElements;
	private ThreadLocalRandom r = ThreadLocalRandom.current();

	/**
	 * Example:
	 *			elements: 8,1,2,
	 * par1		7 2 8 0 9 1 4 6 5 3
	 * par2		8 0 1 9 5 3 7 6 2 4
	 * res		7 8 1 0 9 2 4 6 5 3
	 * @param randomElements number of elements which will be changed
	 * @param bothChildren true if there should be added both children
	 */
	public PartlyOrderCrossover(int randomElements, boolean bothChildren) {
		this.randomElements = randomElements;
		this.bothChildren = bothChildren;
	}

	public void setRandomElements(int randomElements) {
		this.randomElements = randomElements;
	}

	@Override
	public List<GeneticResult> getNewPopulation(List<Pair> parentsList) {
		List<GeneticResult> list = new ArrayList<>();
		for(Pair pair : parentsList) {
			List<Integer> changeList = getRandomList(pair.getParent1().getProblemSize());
			GeneticResult res1 = getNewGeneticResult(pair.getParent1(), pair.getParent2(), changeList);
			list.add(res1);
			if(bothChildren) {
				GeneticResult res2 = getNewGeneticResult(pair.getParent2(), pair.getParent1(), changeList);
				if (!res1.equals(res2)) list.add(res2);
			}
		}
		return list;
	}

	private GeneticResult getNewGeneticResult(GeneticResult result1, GeneticResult result2, List<Integer> list) {
//		System.out.print("elements: ");
//		for(Integer i : list) System.out.print(i + ",");
//		System.out.print('\n');
//		System.out.println(result1.toString());
//		System.out.println(result2.toString());

		int n = result1.getProblemSize();
		GeneticResult newRes = result1.clone();
		Queue<Integer> order = new LinkedList<>();
		for(int i = 0; i < n; i++) {
			if(list.contains(result2.way[i])) {
				order.add(result2.way[i]);
			}
		}

		for(int i = 0; i < n; i++) {
			if(list.contains(newRes.way[i])) {
				newRes.way[i] = order.remove();
			}
		}
//		System.out.println(newRes.toString());
//		System.out.println(" ");
		newRes.calcObjectiveFunction();
		return newRes;
	}

	private List<Integer> getRandomList(int n) {
		List<Integer> list = new ArrayList<>();
		while(list.size() < randomElements) {
			Integer i = r.nextInt(n);
			while(list.contains(i)) i = r.nextInt(n);
			list.add(i);
		}
		return list;
	}

	@Override
	public Crossover copy() {
		return new PartlyOrderCrossover(randomElements, bothChildren);
	}
}
