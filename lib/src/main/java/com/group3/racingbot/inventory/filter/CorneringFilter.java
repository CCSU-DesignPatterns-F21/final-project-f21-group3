package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.Iterator;

/**
 * Returns results which match the given cornering attribute criteria.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class CorneringFilter<T extends SkillFilterable> extends IteratorDecorator<T> {
	private int cornering;
	private FilterOperation operation;
	
	/**
	 * Applies the cornering filter to whatever inventory iterator is passed into it.
	 * @param iterator the iterator which is to be decorated
	 * @param op the logical operator
	 * @param cornering the cornering skill to filter by
	 */
	public CorneringFilter(Iterator<T> iterator, FilterOperation op, int cornering) {
		super(iterator);
		this.cornering = cornering;
		this.operation = op;
	}
	
	@Override
	public T next() {
		T item = super.getIterator().next();
		boolean itemMatchesContraints = false;
		switch (this.operation) {
			case IS_GREATER_THAN:
				itemMatchesContraints = item != null 
					&& item.getCornering() > this.cornering;
				break;
			case IS_LESS_THAN:
				itemMatchesContraints = item != null 
					&& item.getCornering() < this.cornering;
				break;
			case IS_EQUAL:
				itemMatchesContraints = item != null 
					&& item.getCornering() == this.cornering;
				break;
			case IS_NOT_EQUAL:
				itemMatchesContraints = item != null 
					&& item.getCornering() != this.cornering;
				break;
			default:
				System.out.println("CorneringFilter: Invalid operator supplied.");
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
		return this.operation.toString().toLowerCase() + " " + this.cornering;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.cornering + this.operation.getIntOperation();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof CorneringFilter) {
			CorneringFilter<?> otherObj = (CorneringFilter<?>) other;
			if (this.getCriteria().equals(otherObj.getCriteria())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "CorneringFilter which filters for items that are " + this.getCriteria();
	}
}
