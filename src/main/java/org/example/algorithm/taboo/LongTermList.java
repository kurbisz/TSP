package org.example.algorithm.taboo;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.tabooList.TabooList;
import org.example.data.Result;

public class LongTermList {



	int counter = 0;
	boolean resetted = true, kick;
	int newElements, checkRatio;

	Result result;
	int best;
	TabooList list;

	public LongTermList(Result result, TabooList list) {
		this(result, list, 5, 100, false);
	}

	public LongTermList(Result result, TabooList list, int newElements, int checkRatio, boolean kick) {
		this.result = result;
		this.best = result.calcObjectiveFunction();
		this.list = list;
		this.newElements = newElements;
		this.checkRatio = checkRatio;
		this.kick = kick;
	}

	public boolean addCount(Move move) {
		if(counter < newElements && resetted) {
			list.add(move);
		}
		counter++;
		return counter >= checkRatio;
	}

	public boolean isBetter(int newResult) {
		return best != -1 && newResult < best;
	}

	public void reset(Result result, TabooList tabooList, boolean newPoint) {
		if(newPoint) {
			this.result = result.clone();
			this.best = result.calcObjectiveFunction();
			this.list = tabooList.cloneTabooList();
		}
		counter = 0;
		resetted = newPoint;
	}

	public LongTermList clone() {
		LongTermList longTermList = new LongTermList(result.clone(), list.cloneTabooList(), newElements, checkRatio, kick);
		longTermList.counter = counter;
		longTermList.best = best;
		return longTermList;
	}

}
