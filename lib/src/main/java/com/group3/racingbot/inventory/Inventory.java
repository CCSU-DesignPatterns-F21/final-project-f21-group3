package com.group3.racingbot.inventory;

/**
 * @author Nick Sabia
 * Ensures that an inventory can traverse through its items and can add new items.
 */
public interface Inventory<T> {
	/**
	 * Creates an iterator which can traverse through the inventory of items.
	 * @return InventoryIterator<T>
	 */
	public InventoryIterator<T> iterator();
	/**
	 * Add an item to the inventory of items.
	 * @param item
	 */
	public void add(T item);
	/**
	 * Remove an item from the inventory of items.
	 * @param item
	 * @return success or failure of item removal
	 */
	public boolean remove (T item);
}
