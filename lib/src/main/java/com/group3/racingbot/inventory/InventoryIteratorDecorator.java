/**
 * 
 */
package com.group3.racingbot.inventory;

/**
 * @author Nick Sabia
 *
 */
public abstract class InventoryIteratorDecorator<T> implements InventoryIterator<T> {
	protected InventoryIterator<T> inventoryIterator;
	
	InventoryIteratorDecorator(InventoryIterator<T> iterator) {
		this.inventoryIterator = iterator;
	}
	
	abstract public T next();
}
