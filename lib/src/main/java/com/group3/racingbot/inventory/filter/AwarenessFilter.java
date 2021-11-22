package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.InventoryIterator;

/**
 * Returns results which match the given awareness attribute criteria.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class AwarenessFilter<T extends SkillFilterable> extends InventoryIteratorDecorator<T> {
	private int awareness;
	private FilterOperation operation;
	private int current;
	
	/**
	 * Applies the awareness filter to whatever inventory iterator is passed into it.
	 * @param iterator
	 * @param op
	 * @param awareness
	 */
	public AwarenessFilter(InventoryIterator<T> iterator, FilterOperation op, int awareness) {
		super(iterator);
		this.awareness = awareness;
		this.operation = op;
		this.current = 0;
	}
	
	@Override
	public int getCurrentIndex() {
		return this.current;
	}
	
	/**
	 * Verifies that there is another item ahead of the current one.
	 */
	public boolean hasNext() {
		return this.inventoryIterator.hasNext();
	}
	
	/**
	 * Grab the next item in the list. This will filter out items which don't match the criteria for the specified awareness skill.
	 */
	public T next() {
		T item = this.inventoryIterator.next();
		boolean itemMatchesContraints = false;
		switch (this.operation) {
			case IS_GREATER_THAN:
				itemMatchesContraints = item != null 
					&& item.getAwareness() > this.awareness;
				break;
			case IS_LESS_THAN:
				itemMatchesContraints = item != null 
					&& item.getAwareness() < this.awareness;
				break;
			case IS_EQUAL: default:
				itemMatchesContraints = item != null 
					&& item.getAwareness() == this.awareness;
				break;
		}
		if (!itemMatchesContraints) {
			// If the item doesn't match the given criteria, 
			// we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	/**
	 * Returns what this filter is filtering for.
	 * @return String
	 */
	public String getCriteria() {
		return this.operation.toString().toLowerCase() + " " + this.awareness;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.awareness + this.operation.getIntOperation();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof AwarenessFilter) {
			AwarenessFilter<?> otherObj = (AwarenessFilter<?>) other;
			if (this.getCriteria().equals(otherObj.getCriteria())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "AwarenessFilter which filters for items that are " + this.getCriteria();
	}
	
	/**
	 * Print the entire inventory regardless of filter.
	 */
	public void printInventory() {
		
	}
}
