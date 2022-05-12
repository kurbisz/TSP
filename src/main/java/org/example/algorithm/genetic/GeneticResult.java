package org.example.algorithm.genetic;

import org.example.data.Result;
import org.example.data.TspData;

public class GeneticResult extends Result {

	private double chanceToCross = 0.4;

	public GeneticResult(TspData tspData) {
		super(tspData);
	}

	public void setChanceToCross(double chanceToCross) {
		this.chanceToCross = chanceToCross;
	}

	public double getChanceToCross() {
		return chanceToCross;
	}

	@Override
	public GeneticResult clone() {
		return null;
	}

	public static GeneticResult getFromResult(Result result) {
		GeneticResult newResult = new GeneticResult(result.problem);
		for(int i = 0; i < result.problem.getSize(); i++) {
			newResult.way[i] = result.way[i];
		}
		newResult.currInd = result.currInd;
		newResult.objFuncResult = result.objFuncResult;
		return newResult;
	}


}
