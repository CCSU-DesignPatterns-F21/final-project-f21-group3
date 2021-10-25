package com.group3.racingbot.inventory;

/**
 * @author Nick Sabia
 *
 */
public class QualityFilter<T extends Filterable> extends InventoryIteratorDecorator<T> {
	
	private String quality;
	
	public QualityFilter(InventoryIterator<T> iterator, String label) {
		super(iterator);
		this.quality = label;
	}
	
	public boolean hasNext() {
		return this.inventoryIterator.hasNext();
	}
	
	public T next() {
		T item = this.inventoryIterator.next();
		if (item != null && !this.quality.equals(item.getQuality())) {
			// If the quality doesn't match, we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	public void printInventory() {
		
	}

}
