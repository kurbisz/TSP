package org.example.algorithm.genetic.longterm;

import org.example.algorithm.genetic.GeneticResult;
import org.example.algorithm.genetic.startPopulations.RandomPopulation;
import org.example.data.TspData;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLongTermEditor implements LongTermEditor {

	int changed;
	ThreadLocalRandom r = ThreadLocalRandom.current();

	public RandomLongTermEditor() {
		this(10);
	}

	public RandomLongTermEditor(int changed) {
		this.changed = changed;
	}

	@Override
	public List<GeneticResult> getChanged(List<GeneticResult> list) {
		TspData tspData = null;
		for(int i = 0; i < changed; i++) {
			int remove = r.nextInt(list.size());
			if(tspData==null) tspData = list.remove(remove).problem;
		}
		RandomPopulation randomPopulation = new RandomPopulation(changed);
		list.addAll(randomPopulation.getNewPopulation(tspData));
		return list;
	}

	@Override
	public LongTermEditor copy() {
		return new RandomLongTermEditor(changed);
	}
}
