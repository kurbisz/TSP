package org.example.algorithm.genetic.mutations;

import org.example.data.Result;

import java.util.List;

public interface Mutation {

	List<Result> getMutatedPopulation(List<Result> list);

}
