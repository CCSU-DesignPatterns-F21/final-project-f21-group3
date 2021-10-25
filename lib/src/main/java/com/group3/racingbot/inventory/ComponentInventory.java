package com.group3.racingbot.inventory;

import java.util.ArrayList;
import java.util.List;

import com.group3.racingbot.components.Component;

/**
 * Store and access components which are used for cars.
 * @author Nick Sabia
 *
 */
public class ComponentInventory implements Inventory<Component>{
	private List<Component> items;
	
	/**
	 * Creates a list to store the components into.
	 */
	public ComponentInventory() {
		// TODO: Get this list of components from the database upon class creation.
		// For now, stores the list that the user passes in.
		//this.items = componentList;
		this.items = new ArrayList<Component>();
	}
	
	/**
	 * Creates an instance of an iterator which can be used to traverse the inventory of components.
	 */
	public InventoryIterator<Component> iterator() {
		return new ComponentIterator();
	}
	
	/**
	 * Add a component to the inventory.
	 */
	public void add(Component component) {
		this.items.add(component);
	}
	
	/**
	 * Grab the entire list of items. This is necessary for MongoDB to work properly.
	 * @return List<Component>
	 */
	public List<Component> getItems() {
		return items;
	}
	
	/**
	 * Set the list of items. This is necessary for MongoDB to work properly.
	 * @param newList
	 */
	public void setItems(List<Component> newList) {
		this.items = newList;
	}
	
	/**
	 * Display the total amount of components in the inventory.
	 */
	public String toString() {
		return "Total Components: " + getItems().size();
	}
	
	/**
	 * Provides a way to traverse the inventory.
	 * @author Nick Sabia
	 *
	 */
	private class ComponentIterator implements InventoryIterator<Component> {
		private int current;
		
		private ComponentIterator() {
			this.current = 0;
		}
		
		/**
		 * Verifies that there is another component ahead of the current one.
		 */
		public boolean hasNext() {
			if (this.current < ComponentInventory.this.items.size() + 1) {
				return true;
			}
			return false;
		}
		
		/**
		 * Grab the next component.
		 */
		public Component next() {
			Component item = null;
			try {
				item = ComponentInventory.this.items.get(this.current);
				this.current++;
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("End of the list has been reached.");
			}
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
				Component item = this.next();
				System.out.println(item);
			}
			this.current = tempCurrent;
		}
	}
}
