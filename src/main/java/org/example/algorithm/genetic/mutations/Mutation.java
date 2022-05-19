package org.example.algorithm.genetic.mutations;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public interface Mutation {

	List<GeneticResult> getMutatedPopulation(List<GeneticResult> list);

}
