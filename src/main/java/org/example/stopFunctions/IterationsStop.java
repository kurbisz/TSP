package org.example.stopFunctions;

public class IterationsStop extends StopFunction {

	public int maxIterations;
	private int actualIterations;

	public IterationsStop(int maxIterations) {
		this.maxIterations = maxIterations;
		this.actualIterations = 0;
	}

	@Override
	public boolean check() {
//		System.out.println("IterationsStop: " + actualIterations + " / " + maxIterations);
		actualIterations++;
		return actualIterations >= maxIterations;
	}

	@Override
	public StopFunction copy() {
		return new IterationsStop(maxIterations);
	}

}
