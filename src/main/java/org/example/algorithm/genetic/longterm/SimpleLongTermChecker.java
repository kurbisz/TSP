package org.example.algorithm.genetic.longterm;

import org.example.algorithm.genetic.GeneticResult;

import java.util.List;

public class SimpleLongTermChecker implements LongTermChecker {

	int checkPeriod;
	int am = 0;
	int bestResultDist = Integer.MAX_VALUE;

	/**
	 * Check if there is no upgrade after 'checkPeriod' steps
	 */
	public SimpleLongTermChecker() {
		this(5000);
	}

	public SimpleLongTermChecker(int checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	@Override
	public boolean check(List<GeneticResult> currentPopulation, GeneticResult bestResult) {
		int newDist = bestResult.objFuncResult;
		if(newDist < bestResultDist) {
			bestResultDist = newDist;
			am = 0;
		}
		else if(am >= checkPeriod) {
			System.out.println(newDist);
			bestResultDist = Integer.MAX_VALUE;
			return true;
		}
		else am++;
		return false;
	}

	@Override
	public LongTermChecker copy() {
		return new SimpleLongTermChecker(checkPeriod);
	}
}
