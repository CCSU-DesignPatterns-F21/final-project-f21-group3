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
		// For now, stores the list that the user passes in.
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
