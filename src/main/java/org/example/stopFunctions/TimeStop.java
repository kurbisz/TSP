package org.example.stopFunctions;

public class TimeStop extends StopFunction {

	public long maxTime;
	private final long startTime;

	public TimeStop(long maxTime) {
		this.maxTime = maxTime;
		this.startTime = System.nanoTime();
	}

	@Override
	public boolean check() {
		return (System.nanoTime() - startTime) > maxTime;
	}

	@Override
	public StopFunction copy() {
		return new TimeStop(maxTime);
	}

}
