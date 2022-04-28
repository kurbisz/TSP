package org.example.algorithm.taboo;

import org.example.algorithm.taboo.tabooList.TabooList;
import org.example.data.Result;

public class TabooSearchResult {

	TabooList tabooList;
	Result result;
	LongTermList longTermList;

	public TabooSearchResult(TabooList tabooList, Result result, LongTermList longTermList) {
		this.tabooList = tabooList;
		this.result = result;
		this.longTermList = longTermList;
	}

}
