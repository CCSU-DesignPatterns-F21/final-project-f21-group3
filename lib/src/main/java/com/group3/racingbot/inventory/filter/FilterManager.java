/**
 * 
 */
package com.group3.racingbot.inventory.filter;

import java.util.ArrayList;

import com.group3.racingbot.inventory.InventoryIterator;
import com.group3.racingbot.inventory.Iterator;

/**
 * Used to store and use filters on inventories.
 * @author Nick Sabia
 */
public class FilterManager<T> {
	//private ArrayList<InventoryIteratorDecorator<T>> materialFilters;
	//private ArrayList<InventoryIteratorDecorator<T>> skillFilters;
	private ArrayList<InventoryIteratorDecorator<T>> filters;
	
	public FilterManager() {
		//this.materialFilters = new ArrayList<InventoryIteratorDecorator<T>>();
		//this.skillFilters = new ArrayList<InventoryIteratorDecorator<T>>();
		this.filters = new ArrayList<InventoryIteratorDecorator<T>>();
	}

	/**
	 * @return the filters
	 */
	public ArrayList<InventoryIteratorDecorator<T>> getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(ArrayList<InventoryIteratorDecorator<T>> filters) {
		this.filters = filters;
	}
	
	/**
	 * Add a filter to the list.
	 * @param filter filter to add
	 */
	//@SuppressWarnings("unchecked")
	public void add(InventoryIteratorDecorator<T> filter) {
		//if (filter.getInventoryIterator().getClass().isAssignableFrom(MaterialFilterable.class)) {
			//this.materialFilters.add((InventoryIteratorDecorator<MaterialFilterable>) filter);
		//}
		//else if (filter.getInventoryIterator().getClass().isAssignableFrom(MaterialFilterable.class))
		this.filters.add(filter);
	}
	
	/**
	 * Clear out all existing filters.
	 */
	public void clear() {
		this.filters = new ArrayList<InventoryIteratorDecorator<T>>();
	}
	
	/**
	 * Retrieve a new iterator that can traverse the filters.
	 * @return a filter iterator
	 */
	public Iterator<InventoryIteratorDecorator<T>> iterator() {
		return new FilterIterator();
	}
	
	/**
	 * Prints out all stored filters to a string.
	 * @return a string containing info about each filter in the list
	 */
	public String printFilters() {
		String resultStr = "";
		FilterIterator iterator = new FilterIterator();
		while (iterator.hasNext()) {
			InventoryIteratorDecorator<T> currentFilter = iterator.next();
			resultStr += currentFilter.toString() + "\n";
		}
		return resultStr;
	}
	
	/**
	 * Apply material filters to a material filterable iterator.
	 * @param originalIterator the unaltered iterator.
	 * @return string containing the results of the filter.
	 */
	public InventoryIteratorDecorator<T> performMaterialFilter(InventoryIterator<T> originalIterator) {
		//String resultStr = "";
		FilterIterator iterator = new FilterIterator();
		//Iterator<? extends MaterialFilterable> prevIterator = originalIterator; // This is the iterator which will go into the parameters of the next iterator we find.
		ArrayList<InventoryIteratorDecorator<T>> appliedFilters = new ArrayList<InventoryIteratorDecorator<T>>(); // Iterators which actually get used.
		// Connect each iterator with one another.
		int index = 0;
		while (iterator.hasNext()) {
			InventoryIteratorDecorator<T> currentFilter = iterator.next();
			// Ensure that only the material filterable filters apply.
			if (currentFilter.getClass().isAssignableFrom(MaterialFilterable.class)) {
				/*QualityFilter<Car> filterA = new QualityFilter<Car>(carIterator, "Lemon");
	    		DurabilityFilter<Car> filterB = new DurabilityFilter<Car>(filterA, FilterOperation.IS_GREATER_THAN, 40);*/
				if (appliedFilters.size() == 0) {
					currentFilter.setInventoryIterator(originalIterator);
				}
				else {
					currentFilter.setInventoryIterator(appliedFilters.get(index));
					index++;
				}
				appliedFilters.add(currentFilter);
			}
		}
		return appliedFilters.get(index);
	}
	
	/**
	 * Provides a way to traverse the filters.
	 * @author Nick Sabia
	 */
	private class FilterIterator implements Iterator<InventoryIteratorDecorator<T>> {
		private int current;
		
		private FilterIterator() {
			this.current = 0;
		}
		
		@Override
		public int getCurrentIndex() {
			return current;
		}
		
		@Override
		public boolean hasNext() {
			if (this.current < FilterManager.this.filters.size()) {
				return true;
			}
			return false;
		}
		
		@Override
		public InventoryIteratorDecorator<T> next() {
			InventoryIteratorDecorator<T> item = null;
			try {
				item = FilterManager.this.filters.get(this.current);
				this.current++;
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("End of the list has been reached.");
			}
			return item;
		}
	}
}
