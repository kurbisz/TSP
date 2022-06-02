package org.example.algorithm.genetic;

import org.example.data.Result;
import org.example.data.TspData;

public class GeneticResult extends Result {

	private double chanceToCross = 0.4;
	int age = 0;

	public GeneticResult(TspData tspData) {
		super(tspData);
	}

	public GeneticResult(Result res){
		super(res);
		chanceToCross = 0.0;
	}

	public void setChanceToCross(double chanceToCross) {
		this.chanceToCross = chanceToCross;
	}

	public double getChanceToCross() {
		return chanceToCross;
	}


	@Override
	public GeneticResult clone() {
		GeneticResult newResult = GeneticResult.getFromResult(super.clone());
		newResult.setChanceToCross(chanceToCross);
		return newResult;
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

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof GeneticResult)) return false;
		GeneticResult res = (GeneticResult) o;
		for(int i = 0; i < res.problem.getSize(); i++) {
			if(res.way[i] != way[i]) return false;
		}
		return true;
	}

	public int getAge() {
		return age;
	}

	public void incrementAge() {
		age++;
	}


}
