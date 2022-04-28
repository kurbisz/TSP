package org.example.algorithm.taboo.stopFunctions;

public class IterationsStop extends StopFunction {

	public int maxIterations;
	private int actualIterations;

	public IterationsStop(int maxIterations) {
		this.maxIterations = maxIterations;
		this.actualIterations = 0;
	}

	@Override
	public boolean check() {
		actualIterations++;
		return actualIterations >= maxIterations;
	}

}
