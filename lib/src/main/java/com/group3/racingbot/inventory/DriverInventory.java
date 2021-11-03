package com.group3.racingbot.inventory;

import java.util.ArrayList;
import java.util.List;

import com.group3.racingbot.Driver;

/**
 * Store and access drivers
 * @author Nick Sabia
 *
 */
public class DriverInventory implements Inventory<Driver>{
	private List<Driver> items;
	
	/**
	 * Creates a list to store the drivers into.
	 */
	public DriverInventory() {
		// TODO: Get this list of components from the database upon class creation.
		// For now, creates a list that the user can add to.
		this.items = new ArrayList<Driver>();
	}
	
	/**
	 * Creates an instance of an iterator which can be used to traverse the inventory of drivers.
	 */
	public InventoryIterator<Driver> iterator() {
		return new DriverIterator();
	}
	
	/**
	 * Add a driver to the inventory.
	 */
	public void add(Driver driver) {
		this.items.add(driver);
	}
	
	/**
	 * Grab the entire list of items. This is necessary for MongoDB to work properly.
	 * @return List<Driver>
	 */
	public List<Driver> getItems() {
		return items;
	}
	
	/**
	 * Set the list of items. This is necessary for MongoDB to work properly.
	 * @param newList
	 */
	public void setItems(List<Driver> newList) {
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
		else if (other instanceof DriverInventory) {
			DriverInventory otherObj = (DriverInventory) other;
			InventoryIterator<Driver> thisIterator = this.iterator();
			InventoryIterator<Driver> otherObjIterator = otherObj.iterator();
			
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
	private class DriverIterator implements InventoryIterator<Driver> {
		private int current;
		
		private DriverIterator() {
			this.current = 0;
		}
		
		/**
		 * Verifies that there is another driver ahead of the current one.
		 */
		public boolean hasNext() {
			if (this.current < DriverInventory.this.items.size() + 1) {
				return true;
			}
			return false;
		}
		
		/**
		 * Grab the next driver.
		 */
		public Driver next() {
			Driver item = null;
			try {
				item = DriverInventory.this.items.get(this.current);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("End of the list has been reached.");
			}
			this.current++;
			return item;
		}
		
		/**
		 * Print the entire inventory regardless of filter.
		 */
		public void printInventory() {
			int tempCurrent = this.current;
			this.current = 0;
			// Iterate through the inventory and print each item.
			while (this.hasNext()) {
				Driver item = this.next();
				System.out.println(item);
			}
			this.current = tempCurrent;
		}
	}
}
