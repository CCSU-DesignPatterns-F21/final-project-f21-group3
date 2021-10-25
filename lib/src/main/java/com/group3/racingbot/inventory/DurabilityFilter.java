package com.group3.racingbot.inventory;

public class DurabilityFilter<T extends Filterable> extends InventoryIteratorDecorator<T> {
	private float durability;
	private FilterOperation operation;
	
	public DurabilityFilter(InventoryIterator<T> iterator, FilterOperation op, float durability) {
		super(iterator);
		this.durability = durability;
		this.operation = op;
	}
	
	public boolean hasNext() {
		return this.inventoryIterator.hasNext();
	}
	
	public T next() {
		T item = this.inventoryIterator.next();
		boolean itemMatchesContraints = false;
		switch (this.operation) {
			case IS_GREATER_THAN:
				itemMatchesContraints = item != null 
					&& item.getDurability() > this.durability;
				break;
			case IS_LESS_THAN:
				itemMatchesContraints = item != null 
					&& item.getDurability() < this.durability;
				break;
			case IS_EQUAL: default:
				itemMatchesContraints = item != null 
					&& item.getDurability() == this.durability;
				break;
		}
		if (!itemMatchesContraints) {
			// If the item doesn't match the given criteria, 
			// we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	public void printInventory() {
		
	}
}
