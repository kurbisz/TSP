package org.example.algorithm;

import org.example.data.Result;
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

	public abstract Result calculate();

}
