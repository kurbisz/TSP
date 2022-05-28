package org.example.algorithm.genetic.longterm;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public interface LongTermEditor {

	List<GeneticResult> getChanged(List<GeneticResult> list);

	LongTermEditor copy();

}
