package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.Iterator;

/**
 * Returns results which match the given price criteria.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class ValueFilter<T extends MaterialFilterable> extends IteratorDecorator<T> {
	private int price;
	private FilterOperation operation;
	
	/**
	 * Applies the price filter to whatever inventory iterator is passed into it.
	 * @param iterator the iterator which is to be decorated
	 * @param op the logical operator
	 * @param price the price of the material object to filter by
	 */
	public ValueFilter(Iterator<T> iterator, FilterOperation op, int price) {
		super(iterator);
		this.price = price;
		this.operation = op;
	}
	
	@Override
	public T next() {
		T item = super.getIterator().next();
		boolean itemMatchesContraints = false;
		switch (this.operation) {
			case IS_GREATER_THAN:
				itemMatchesContraints = item != null 
					&& item.getValue() > this.price;
				break;
			case IS_LESS_THAN:
				itemMatchesContraints = item != null 
					&& item.getValue() < this.price;
				break;
			case IS_EQUAL:
				itemMatchesContraints = item != null 
					&& item.getValue() == this.price;
				break;
			case IS_NOT_EQUAL:
				itemMatchesContraints = item != null 
					&& item.getValue() != this.price;
				break;
			default:
				System.out.println("ValueFilter: Invalid operator supplied.");
				break;
		}
		if (!itemMatchesContraints) {
			// If the item doesn't match the given criteria, 
			// we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	@Override
	public String getCriteria() {
		return this.operation.toString().toLowerCase() + " " + this.price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.price + this.operation.getIntOperation();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof ValueFilter) {
			ValueFilter<?> otherObj = (ValueFilter<?>) other;
			if (this.getCriteria().equals(otherObj.getCriteria())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "PriceFilter which filters for items that are " + this.getCriteria();
	}
}
