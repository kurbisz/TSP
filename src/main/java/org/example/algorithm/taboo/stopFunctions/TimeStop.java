package org.example.algorithm.taboo.stopFunctions;

public class TimeStop extends StopFunction {

	public long maxTime;
	private long startTime;

	public TimeStop(long maxTime) {
		this.maxTime = maxTime;
		this.startTime = System.nanoTime();
	}

	@Override
	public boolean check() {
		return (System.nanoTime() - startTime) > maxTime;
	}

}
