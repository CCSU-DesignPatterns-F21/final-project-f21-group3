package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.InventoryIterator;

/**
 * Ensures that a filter made for an InventoryIterator will work.
 * @author Nick Sabia
 *
 * @param <T>
 */
public abstract class InventoryIteratorDecorator<T> implements InventoryIterator<T> {
	/**
	 * The iterator which will be decorated/filtered.
	 */
	protected InventoryIterator<T> inventoryIterator;
	
	/**
	 * Set the iterator which will be decorated/filtered.
	 * @param iterator
	 */
	public InventoryIteratorDecorator(InventoryIterator<T> iterator) {
		this.inventoryIterator = iterator;
	}
	
	/**
	 * Retrieve the iterator which is be decorated
	 * @return the inventoryIterator
	 */
	public InventoryIterator<T> getInventoryIterator() {
		return inventoryIterator;
	}

	/**
	 * Set the iterator which is be decorated
	 * @param inventoryIterator the inventoryIterator to set
	 */
	public void setInventoryIterator(InventoryIterator<T> inventoryIterator) {
		this.inventoryIterator = inventoryIterator;
	}

	/**
	 * Grab the next item in the list. This will filter out items which don't match the given criteria.
	 */
	abstract public T next();
	
	/**
	 * Returns what this filter is filtering for.
	 * @return String
	 */
	abstract public String getCriteria();
}
