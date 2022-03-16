package org.example.algorithm;

import org.example.data.TspData;

public abstract class Algorithm {

	private TspData tspData;

	protected int[] result;

	public Algorithm(TspData tspData) {
		this.tspData = tspData;
		int size = tspData.getSize();
		this.result = new int[size];
		for(int i = 0; i < size; i++) {
			result[i] = i;
		}
	}

	public abstract void calculate();

	private void drawPoint() {
		// TODO
	}

	private void drawLine() {
		// TODO
	}

	public int objectiveFunction() {
		int sum = 0;
		for(int i = 0; i < tspData.getSize() - 1; i++) {
			sum += tspData.getDistance(result[i], result[i+1]);
		}
		return sum;
	}

}
