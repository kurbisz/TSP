package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

public abstract class Algorithm {

	protected TspData tspData;

	public Algorithm(TspData tspData) {
		this.tspData = tspData;
		int size = tspData.getSize();
	}

	public abstract Result calculate();

}
