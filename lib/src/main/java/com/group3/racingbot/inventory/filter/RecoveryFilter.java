package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.InventoryIterator;

/**
 * Returns results which match the given recovery attribute criteria.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class RecoveryFilter<T extends SkillFilterable> extends InventoryIteratorDecorator<T> {
	private int recovery;
	private FilterOperation operation;
	
	/**
	 * Applies the recovery filter to whatever inventory iterator is passed into it.
	 * @param iterator
	 * @param op
	 * @param recovery
	 */
	public RecoveryFilter(InventoryIterator<T> iterator, FilterOperation op, int recovery) {
		super(iterator);
		this.recovery = recovery;
		this.operation = op;
	}
	
	/**
	 * Verifies that there is another item ahead of the current one.
	 */
	public boolean hasNext() {
		return this.inventoryIterator.hasNext();
	}
	
	/**
	 * Grab the next item in the list. This will filter out items which don't match the criteria for the specified recovery skill.
	 */
	public T next() {
		T item = this.inventoryIterator.next();
		boolean itemMatchesContraints = false;
		switch (this.operation) {
			case IS_GREATER_THAN:
				itemMatchesContraints = item != null 
					&& item.getRecovery() > this.recovery;
				break;
			case IS_LESS_THAN:
				itemMatchesContraints = item != null 
					&& item.getRecovery() < this.recovery;
				break;
			case IS_EQUAL: default:
				itemMatchesContraints = item != null 
					&& item.getRecovery() == this.recovery;
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
		return this.operation.toString() + " " + this.recovery;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.recovery + this.operation.getIntOperation();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof RecoveryFilter) {
			RecoveryFilter<?> otherObj = (RecoveryFilter<?>) other;
			if (this.getCriteria().equals(otherObj.getCriteria())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "RecoveryFilter which filters for items that are " + this.getCriteria();
	}
	
	/**
	 * Print the entire inventory regardless of filter.
	 */
	public void printInventory() {
		
	}
}
