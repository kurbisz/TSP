package org.example.parser;

import org.example.TspData;

public abstract class Parser {

	protected String data;

	public Parser(String data) {
		this.data = data;
	}

	public abstract TspData getTspData();

}
