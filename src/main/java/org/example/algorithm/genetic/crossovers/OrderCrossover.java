package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.List;

public class OrderCrossover implements Crossover {

	int randomElements = 5;

	public OrderCrossover() {
	}

	public OrderCrossover(int randomElements) {
		this.randomElements = randomElements;
	}

	public void setRandomElements(int randomElements) {
		this.randomElements = randomElements;
	}

	@Override
	public List<GeneticResult> getNewPopulation(List<Pair> parentsList) {
		return null;
	}

	@Override
	public Crossover copy() {
		return new OrderCrossover(randomElements);
	}
}
