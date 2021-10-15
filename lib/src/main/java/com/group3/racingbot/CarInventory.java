package com.group3.racingbot;

import java.util.List;

/**
 * @author Nick Sabia
 *
 */
public class CarInventory {
	private List<Car> items;
	
	public CarInventory(List<Car> carList) {
		// TODO: Get this list of components from the database upon class creation.
		// For now, stores the list that the user passes in.
		this.items = carList;
	}
	public InventoryIterator<Car> iterator() {
		return new CarIterator();
	}
	
	private class CarIterator implements InventoryIterator<Car> {
		private int current = 0;
		
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
				this.current++;
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("End of the list has been reached.");
			}
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
