package com.group3.racingbot.inventory;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.group3.racingbot.Driver;
import com.group3.racingbot.ComponentFactory.Component;

/**
 * Store and access drivers
 * @author Nick Sabia
 *
 */
public class DriverInventory implements Inventory<Driver>{
	@JsonBackReference
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
	 * Remove a driver from the inventory.
	 */
	public boolean remove(Driver driver) {
		if (this.items.remove(driver)) 
			return true;
		return false;
	}
	
	/**
	 * Updates a driver in the inventory
	 * @param driver
	 * @return whether or not the update was successful
	 */
	public boolean update(Driver driver) {
		InventoryIterator<Driver> iterator = this.iterator();
		while (iterator.hasNext()) {
			int currentIndex = iterator.getCurrentIndex();
			Driver currentDriver = iterator.next();
			if (currentDriver.getId().equals(driver.getId())) {
				this.items.set(currentIndex, driver);
				System.out.println("DriverInventory; update method: Driver " + driver.getId() + " has been updated in the driver inventory of Player " + driver.getPlayerId() + ".");
				return true;
			}
		}
		System.out.println("DriverInventory; update method: Unable to find the driver with the id: " + driver.getId());
		return false;
		//throw new NotFoundException("Unable to find the driver with the id: " + driver.getId());
	}
	
	/**
	 * Updates a driver in the inventory at a specified index.
	 * @param driver
	 * @param index
	 */
	public boolean update(Driver driver, int index) {
		try {
			this.items.set(index, driver);
			System.out.println("DriverInventory; update method: Driver " + driver.getId() + " has been updated in the driver inventory of Player " + driver.getPlayer().getId() + ".");
			return true;
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("DriverInventory; update method: Given index out of bounds. Unable update Driver at index " + index + " in the driver inventory of Player " + driver.getPlayer().getId() + ".");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Get a driver from the inventory based on their ID.
	 * @param driverId the id of the driver
	 * @return a driver
	 * @throws NotFoundException 
	 */
	public Driver getById(String driverId) throws NotFoundException {
		InventoryIterator<Driver> iterator = this.iterator();
		while (iterator.hasNext()) {
			Driver currentDriver = iterator.next();
			if (currentDriver.getId().equals(driverId)) {
				return currentDriver;
			}
		}
		throw new NotFoundException("Unable to find the driver with the id: " + driverId);
	}
	
	/**
	 * Get a driver from the inventory based on their ID.
	 * @param driverName the name of the driver
	 * @return a driver
	 * @throws NotFoundException
	 */
	public Driver getByName(String driverName) throws NotFoundException {
		InventoryIterator<Driver> iterator = this.iterator();
		while (iterator.hasNext()) {
			Driver currentDriver = iterator.next();
			if (currentDriver.getName().equals(driverName)) {
				return currentDriver;
			}
		}
		throw new NotFoundException("Unable to find the driver with the name: " + driverName);
	}
	
	/**
	 * Grab the entire list of items. This is necessary for MongoDB to work properly.
	 * @return List<Driver>
	 */
	public List<Driver> getItems() {
		return this.items;
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
		
		@Override
		public int getCurrentIndex() {
			return current;
		}
		
		@Override
		public boolean hasNext() {
			if (this.current < DriverInventory.this.items.size()) {
				return true;
			}
			return false;
		}
		
		@Override
		public Driver next() {
			Driver item = null;
			try {
				item = DriverInventory.this.items.get(this.current);
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
				Driver item = this.next();
				System.out.println(item);
			}
			this.current = tempCurrent;
		}
	}
}