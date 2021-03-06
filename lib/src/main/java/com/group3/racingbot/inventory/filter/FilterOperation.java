package com.group3.racingbot.inventory.filter;

/**
 * Represents logical operations which can be performed.
 * @author Nick Sabia
 *
 */
public enum FilterOperation {
	IS_GREATER_THAN(1),
	IS_EQUAL(0),
	IS_LESS_THAN(-1),
	IS_NOT_EQUAL(2);
	
	private final int operation;

	private FilterOperation(int operation) {
		this.operation = operation;
	}
	
	/**
	 * Returns the integer representation of the operation
	 * @return int
	 */
	public int getIntOperation() {
		return this.operation;
	}
	
	/**
	 * Display the textual representation of the enumeration
	 */
	@Override
	public String toString() {
		switch (this.operation) {
			case 2:
				return "NOT EQUAL TO";
			case 1:
				return "GREATER THAN";
			case 0:
				return "EQUAL TO";
			case -1:
				return "LESS THAN";
			default:
				return "INVALID OPERATOR";
		}
	}
}
