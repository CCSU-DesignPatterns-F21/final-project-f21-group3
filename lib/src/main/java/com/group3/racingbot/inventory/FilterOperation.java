package com.group3.racingbot.inventory;

public enum FilterOperation {
	IS_GREATER_THAN(1),
	IS_EQUAL(0),
	IS_LESS_THAN(-1);
	
	private final int operation;

	private FilterOperation(int operation) {
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		return operation + "";
	}
}
