package com.group3.racingbot.inventory;

import java.util.ArrayList;
import java.util.List;

import com.group3.racingbot.Car;

/**
 * Store and access cars
 * @author Nick Sabia
 *
 */
public class CarInventory implements Inventory<Car>{
	private List<Car> items;
	
	/**
	 * Creates a list to store the cars into.
	 */
	public CarInventory() {
		// TODO: Get this list of components from the database upon class creation.
		// For now, creates a list that the user can add to.
		this.items = new ArrayList<Car>();
	}
	
	/**
	 * Creates an instance of an iterator which can be used to traverse the inventory of cars.
	 */
	public InventoryIterator<Car> iterator() {
		return new CarIterator();
	}
	
	/**
	 * Add a car to the inventory.
	 */
	public void add(Car car) {
		this.items.add(car);
	}
	
	/**
	 * Remove a car from the inventory.
	 */
	public boolean remove(Car car) {
		if (this.items.remove(car)) 
			return true;
		return false;
	}
	
	/**
	 * Grab the entire list of items. This is necessary for MongoDB to work properly.
	 * @return List<Car>
	 */
	public List<Car> getItems() {
		return items;
	}
	
	/**
	 * Set the list of items. This is necessary for MongoDB to work properly.
	 * @param newList
	 */
	public void setItems(List<Car> newList) {
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
		else if (other instanceof CarInventory) {
			CarInventory otherObj = (CarInventory) other;
			InventoryIterator<Car> thisIterator = this.iterator();
			InventoryIterator<Car> otherObjIterator = otherObj.iterator();
			
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
	 * Display the total amount of cars in the inventory.
	 */
	public String toString() {
		return "Total Cars: " + getItems().size();
	}
	
	/**
	 * Provides a way to traverse the inventory.
	 * @author Nick Sabia
	 *
	 */
	private class CarIterator implements InventoryIterator<Car> {
		private int current;
		
		private CarIterator() {
			this.current = 0;
		}
		
		/**
		 * Verifies that there is another car ahead of the current one.
		 */
		public boolean hasNext() {
			if (this.current < CarInventory.this.items.size() + 1) {
				return true;
			}
			return false;
		}
		
		/**
		 * Grab the next car.
		 */
		public Car next() {
			Car item = null;
			try {
				item = CarInventory.this.items.get(this.current);
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
				Car item = this.next();
				System.out.println(item);
			}
			this.current = tempCurrent;
		}
	}
}