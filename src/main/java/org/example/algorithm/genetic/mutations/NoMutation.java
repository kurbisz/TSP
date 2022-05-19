package org.example.algorithm.genetic.mutations;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public class NoMutation implements Mutation {

	@Override
	public List<GeneticResult> getMutatedPopulation(List<GeneticResult> list) {
		return null;
	}
}
