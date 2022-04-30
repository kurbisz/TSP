package org.example.data;

import org.example.algorithm.taboo.LongTermList;
import org.example.algorithm.taboo.tabooList.TabooList;
import org.example.data.Result;

public class TabooSearchResult{

	public TabooList tabooList;
	public Result result;
	public LongTermList longTermList;

	public TabooSearchResult(TabooList tabooList, Result result, LongTermList longTermList) {
		this.tabooList = tabooList;
		this.result = result;
		this.longTermList = longTermList;
	}

}
