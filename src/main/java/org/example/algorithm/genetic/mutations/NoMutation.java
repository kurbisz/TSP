package org.example.algorithm.genetic.mutations;

import org.example.data.Result;

import java.util.List;

public class NoMutation implements Mutation {
	@Override
	public List<Result> getMutatedPopulation(List<Result> list) {
		return list;
	}
}
