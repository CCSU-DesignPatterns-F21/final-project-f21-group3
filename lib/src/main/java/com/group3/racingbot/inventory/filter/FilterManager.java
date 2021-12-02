/**
 * 
 */
package com.group3.racingbot.inventory.filter;

import java.util.ArrayList;

import com.group3.racingbot.inventory.Iterator;

/**
 * Used to store and chain iterator decorators which act as filters together to generate an output.
 * @author Nick Sabia
 */
public class FilterManager<T> {
	private ArrayList<IteratorDecorator<T>> filters;
	
	/**
	 * Constructs a filter manager. This creates an empty list of filters.
	 */
	public FilterManager() {
		this.filters = new ArrayList<IteratorDecorator<T>>();
	}

	/**
	 * Retrieve the list of filters (decorators) which the filter manager has.
	 * @return the list of filters
	 */
	public ArrayList<IteratorDecorator<T>> getFilters() {
		return filters;
	}

	/**
	 * Set the list of filters (decorators) which the filter manager has.
	 * @param filters the list of filters to set
	 */
	public void setFilters(ArrayList<IteratorDecorator<T>> filters) {
		this.filters = filters;
	}
	
	/**
	 * Add a filter to the list.
	 * @param filter filter to add
	 */
	public void add(IteratorDecorator<T> filter) {
		this.filters.add(filter);
	}
	
	/**
	 * Clear out all existing filters.
	 */
	public void clear() {
		this.filters = new ArrayList<IteratorDecorator<T>>();
	}
	
	/**
	 * Retrieve a new iterator that can traverse the filters.
	 * @return a filter iterator
	 */
	public Iterator<IteratorDecorator<T>> iterator() {
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
			IteratorDecorator<T> currentFilter = iterator.next();
			resultStr += currentFilter.toString() + "\n";
		}
		return resultStr;
	}
	
	/**
	 * Apply filters to a filterable iterator. In other words, this chains together all decorators/filters within the filter list and returns the final decorated iterator in that chain.
	 * @param originalIterator the unaltered iterator.
	 * @return string containing the results of the filter.
	 */
	public IteratorDecorator<T> applyFilters(Iterator<T> originalIterator) {
		Iterator<IteratorDecorator<T>> iterator = this.iterator();
		ArrayList<IteratorDecorator<T>> appliedFilters = new ArrayList<IteratorDecorator<T>>(); // Iterators which actually get used.
		
		// Connect each iterator with one another.
		int index = 0;
		while (iterator.hasNext()) {
			IteratorDecorator<T> currentFilter = iterator.next();
			if (appliedFilters.size() == 0) {
				currentFilter.setIterator(originalIterator);
			}
			else {
				currentFilter.setIterator(appliedFilters.get(index));
				index++;
			}
			appliedFilters.add(currentFilter);
		}
		// Handle when applied filters is empty
		if (appliedFilters.size() == 0) {
			return null;
		}
		return appliedFilters.get(index);
	}
	
	/**
	 * Provides a way to traverse the filters.
	 * @author Nick Sabia
	 */
	private class FilterIterator implements Iterator<IteratorDecorator<T>> {
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
		public IteratorDecorator<T> next() {
			IteratorDecorator<T> item = null;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof FilterManager) {
			@SuppressWarnings("unchecked")
			FilterManager<T> otherObj = (FilterManager<T>) other;
			
			ArrayList<IteratorDecorator<T>> otherFilters = otherObj.getFilters();
			
			if (otherFilters.size() != this.filters.size()) {
				return false;
			}
			
			try {
				for (int i = 0, len = this.filters.size(); i < len; i++) {
					if (otherFilters.get(i) == null || !otherFilters.get(i).equals(this.filters.get(i))) {
						return false;
					}
				}
			}
			catch (IndexOutOfBoundsException e) {
				return false;
			}

			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.printFilters();
	}
}
