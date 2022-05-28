package org.example.algorithm.genetic.longterm;

import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.genetic.selections.util.ResultCompare;
import org.example.algorithm.genetic.startPopulations.RandomAndTwoOptPopulation;
import org.example.algorithm.genetic.startPopulations.RandomPopulation;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BestLongTermEditor implements LongTermEditor {

	int bestAm;
	ThreadLocalRandom r = ThreadLocalRandom.current();

	public BestLongTermEditor() {
		this(10);
	}

	public BestLongTermEditor(int bestAm) {
		this.bestAm = bestAm;
	}

	@Override
	public List<GeneticResult> getChanged(List<GeneticResult> list) {
		Comparator<GeneticResult> comparator = new ResultCompare();
		list.sort(comparator);
		int size = list.size();
		List<GeneticResult> newList = new ArrayList<>();
		TspData tspData = null;
		for(int i = 0; i < bestAm && i < size; i++) {
			if(tspData == null) tspData = list.get(i).problem;
			newList.add(list.get(i));
		}
		RandomAndTwoOptPopulation randomPopulation = new RandomAndTwoOptPopulation(0, size - bestAm);
		list.addAll(randomPopulation.getNewPopulation(tspData));
		return list;
	}

	@Override
	public LongTermEditor copy() {
		return new BestLongTermEditor(bestAm);
	}
}
