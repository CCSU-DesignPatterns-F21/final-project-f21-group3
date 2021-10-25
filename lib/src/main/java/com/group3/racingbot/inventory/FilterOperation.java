package com.group3.racingbot.inventory;

/**
 * Represents logical operations which can be performed.
 * @author Nick Sabia
 *
 */
public enum FilterOperation {
	IS_GREATER_THAN(1),
	IS_EQUAL(0),
	IS_LESS_THAN(-1);
	
	private final int operation;

	private FilterOperation(int operation) {
		this.operation = operation;
	}
	
	@Override
	/**
	 * Display the numeric representation of the enumeration
	 */
	public String toString() {
		return operation + "";
	}
}
