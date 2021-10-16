package com.group3.racingbot;

/**
 * @author Nick Sabia
 * Ensures that an inventory can iterate and filter through its components.
 */
public interface Inventory<T> {
	public InventoryIterator<T> iterator();
	public void add(T item);
}
