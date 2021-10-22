package com.group3.racingbot.inventory;

import java.util.List;

import com.group3.racingbot.components.Component;

/**
 * @author Nick Sabia
 *
 */
public class ComponentInventory implements Inventory<Component>{
	private List<Component> items;
	
	public ComponentInventory() {
		// TODO: Get this list of components from the database upon class creation.
		// For now, stores the list that the user passes in.
		//this.items = componentList;
	}
	
	public List<Component> getItems() {
		return items;
		
	}
	public InventoryIterator<Component> iterator() {
		return new ComponentIterator();
	}
	
	public void add(Component component) {
		this.items.add(component);
	}
	
	private class ComponentIterator implements InventoryIterator<Component> {
		private int current;
		
		private ComponentIterator() {
			this.current = 0;
		}
		
		public boolean hasNext() {
			if (this.current < ComponentInventory.this.items.size() + 1) {
				return true;
			}
			return false;
		}
		
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
