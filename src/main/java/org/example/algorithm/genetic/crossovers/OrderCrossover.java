package org.example.algorithm.genetic.crossovers;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public class OrderCrossover implements Crossover {

	int randomElements = 5;

	public void setRandomElements(int randomElements) {
		this.randomElements = randomElements;
	}

	@Override
	public List<GeneticResult> getNewPopulation(List<GeneticResult> oldPopulation) {
		return null;
	}
}