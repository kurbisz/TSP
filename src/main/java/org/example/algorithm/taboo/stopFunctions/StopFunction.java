package org.example.algorithm.taboo.stopFunctions;

public abstract class StopFunction {

	/**
	 * Adds iteration of algorithm and check if it is time to finish it
	 * @return true when its time to finish
	 */
	public abstract boolean check();
	public abstract StopFunction copy();

}
