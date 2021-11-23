/**
 * @author Nick Sabia
 * Ensures that an inventory can traverse through its items and can add new items.
 */
package com.group3.racingbot.inventory;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.group3.racingbot.inventory.filter.FilterManager;

/**
 * Store and access items
 * @author Nick Sabia
 *
 */
public class Inventory<T extends Unique> {
	@JsonBackReference
	private List<T> items;
	private FilterManager<T> filterManager;
	
	/**
	 * Creates a list to store the items into.
	 */
	public Inventory() {
		this.items = new ArrayList<T>();
		this.filterManager = new FilterManager<T>();
	}

	/**
	 * @return the filterManager
	 */
	public FilterManager<T> getFilterManager() {
		return filterManager;
	}

	/**
	 * @param filterManager the filterManager to set
	 */
	public void setFilterManager(FilterManager<T> filterManager) {
		this.filterManager = filterManager;
	}

	/**
	 * Creates an instance of an iterator which can be used to traverse the inventory of drivers.
	 */
	public InventoryIterator<T> iterator() {
		return new ConcreteIterator();
	}
	
	/**
	 * Add an item to the inventory.
	 */
	public void add(T item) {
		this.items.add(item);
	}
	
	/**
	 * Remove an item from the inventory.
	 */
	public boolean remove(T item) {
		if (this.items.remove(item)) 
			return true;
		return false;
	}
	
	/**
	 * Updates an item in the inventory
	 * @param item item to replace an existing item with
	 * @return whether or not the update was successful
	 */
	public boolean update(T item) {
		InventoryIterator<T> iterator = this.iterator();
		while (iterator.hasNext()) {
			int currentIndex = iterator.getCurrentIndex();
			T currentItem = iterator.next();
			if (currentItem.getId().equals(item.getId())) {
				this.items.set(currentIndex, item);
				//System.out.println("Inventory; update method: Driver " + item.getId() + " has been updated in the driver inventory of Player " + driver.getPlayerId() + ".");
				return true;
			}
		}
		System.out.println("DriverInventory; update method: Unable to an item with the id: " + item.getId());
		return false;
		//throw new NotFoundException("Unable to find the driver with the id: " + driver.getId());
	}
	
	/**
	 * Updates an item in the inventory at a specified index.
	 * @param item item to replace an existing item with
	 * @param index the index which to replace using the specified item.
	 */
	public boolean update(T item, int index) {
		try {
			this.items.set(index, item);
			//System.out.println("DriverInventory; update method: Driver " + item.getId() + " has been updated in the driver inventory of Player " + driver.getPlayer().getId() + ".");
			return true;
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("DriverInventory; update method: Given index out of bounds. Unable update item at index " + index + ".");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Get an item from the inventory based on its id.
	 * @param id the id of the item
	 * @return an item
	 * @throws NotFoundException 
	 */
	public T getById(String id) throws NotFoundException {
		InventoryIterator<T> iterator = this.iterator();
		while (iterator.hasNext()) {
			T currentItem = iterator.next();
			if (currentItem.getId().equals(id)) {
				return currentItem;
			}
		}
		throw new NotFoundException("Unable to find item with the id: " + id);
	}
	
	/**
	 * Grab the entire list of items. This is necessary for MongoDB to work properly.
	 * @return List<T> list of items in the inventory.
	 */
	public List<T> getItems() {
		return this.items;
	}
	
	/**
	 * Set the list of items. This is necessary for MongoDB to work properly.
	 * @param newList
	 */
	public void setItems(List<T> newList) {
		this.items = newList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}
	
	@Override
	/**
	 * Check if two lists of items are the same.
	 */
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (this.getClass().isInstance(other)) {
			@SuppressWarnings("unchecked")
			Inventory<T> otherObj = (Inventory<T>) other;
			InventoryIterator<T> thisIterator = this.iterator();
			InventoryIterator<T> otherObjIterator = otherObj.iterator();
			
			// Compare each item for equality.
			while (thisIterator.hasNext() && otherObjIterator.hasNext()) {
				if (!thisIterator.next().equals(otherObjIterator.next())) {
					return false;
				}
			}
			
			// Check if one iterator has any more items than the other
			if (thisIterator.hasNext() || otherObjIterator.hasNext()) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	@Override
	/**
	 * Display the total amount of drivers in the inventory.
	 */
	public String toString() {
		return "Total Cars: " + getItems().size();
	}
	
	/**
	 * Provides a way to traverse the inventory.
	 * @author Nick Sabia
	 *
	 */
	private class ConcreteIterator implements InventoryIterator<T> {
		private int current;
		
		private ConcreteIterator() {
			this.current = 0;
		}
		
		@Override
		public int getCurrentIndex() {
			return current;
		}
		
		@Override
		public boolean hasNext() {
			if (this.current < Inventory.this.items.size()) {
				return true;
			}
			return false;
		}
		
		@Override
		public T next() {
			T item = null;
			try {
				item = Inventory.this.items.get(this.current);
				this.current++;
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("End of the list has been reached.");
			}
			return item;
		}
		
		@Override
		public void printInventory() {
			int tempCurrent = this.current;
			this.current = 0;
			// Iterate through the inventory and print each item.
			while (this.hasNext()) {
				T item = this.next();
				System.out.println(item);
			}
			this.current = tempCurrent;
		}
	}
}