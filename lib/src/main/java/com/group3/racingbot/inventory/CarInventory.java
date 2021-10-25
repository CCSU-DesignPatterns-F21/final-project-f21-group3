package com.group3.racingbot.inventory;

import java.util.ArrayList;
import java.util.List;

import com.group3.racingbot.Car;

/**
 * @author Nick Sabia
 *
 */
public class CarInventory implements Inventory<Car>{
	private List<Car> items;
	
	public CarInventory() {
		// TODO: Get this list of components from the database upon class creation.
		// For now, stores the list that the user passes in.
		this.items = new ArrayList<Car>();
	}
	public InventoryIterator<Car> iterator() {
		return new CarIterator();
	}
	
	public void add(Car car) {
		this.items.add(car);
	}
	
	public List<Car> getItems() {
		return items;
	}
	
	public void setItems(List<Car> newList) {
		this.items = newList;
	}
	
	private class CarIterator implements InventoryIterator<Car> {
		private int current;
		
		private CarIterator() {
			this.current = 0;
		}
		
		public boolean hasNext() {
			if (this.current < CarInventory.this.items.size() + 1) {
				return true;
			}
			return false;
		}
		
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
