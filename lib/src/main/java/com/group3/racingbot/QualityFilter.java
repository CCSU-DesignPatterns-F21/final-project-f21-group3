package com.group3.racingbot;

/**
 * @author Nick Sabia
 *
 */
public class QualityFilter<T extends Quantifiable> extends InventoryIteratorDecorator<T> {
	
	private String qualityLabel;
	
	QualityFilter(InventoryIterator<T> iterator, String label) {
		super(iterator);
		this.qualityLabel = label;
	}
	
	public boolean hasNext() {
		return this.inventoryIterator.hasNext();
	}
	
	public T next() {
		T item = this.inventoryIterator.next();
		if (item != null && !this.qualityLabel.equals(item.getQuality())) {
			// If the quality doesn't match, we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	public void printInventory() {
		
	}

}
