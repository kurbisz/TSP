package org.example.algorithm.genetic.selections.util;

import org.example.algorithm.genetic.GeneticResult;

import java.util.Comparator;

public class ResultCompare implements Comparator<GeneticResult> {

	@Override
	public int compare(GeneticResult o1, GeneticResult o2) {
		int res1 = o1.objFuncResult;
		int res2 = o2.objFuncResult;
		return Integer.compare(res1, res2);
	}
}
