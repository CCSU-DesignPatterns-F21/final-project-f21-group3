package com.group3.racingbot.inventory;

/**
 * Applies the quality filter to whatever inventory iterator is passed into it. This will filter out items which don't match the criteria for the specified quality.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class QualityFilter<T extends Filterable> extends InventoryIteratorDecorator<T> {
	
	private String quality;
	/**
	 * Applies the quality filter to whatever inventory iterator is passed into it.
	 * @param iterator
	 * @param label
	 */
	public QualityFilter(InventoryIterator<T> iterator, String label) {
		super(iterator);
		this.quality = label;
	}
	
	/**
	 * Verifies that there is another item ahead of the current one.
	 */
	public boolean hasNext() {
		return this.inventoryIterator.hasNext();
	}
	
	/**
	 * Grab the next item in the list. This will filter out items which don't match the criteria for the specified durability.
	 */
	public T next() {
		T item = this.inventoryIterator.next();
		if (item != null && !this.quality.equals(item.getQuality())) {
			// If the quality doesn't match, we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	/**
	 * Print the entire inventory regardless of filter.
	 */
	public void printInventory() {
		
	}

}
