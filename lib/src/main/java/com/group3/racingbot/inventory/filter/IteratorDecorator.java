package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.Iterator;

/**
 * Ensures that a filter made for an Iterator will work.
 * @author Nick Sabia
 *
 * @param <T>
 */
public abstract class IteratorDecorator<T> implements Iterator<T> {
	/**
	 * The iterator which will be decorated/filtered.
	 */
	private Iterator<T> iterator;
	/**
	 * The current index which the iterator is currently on.
	 */
	private int current;
	
	/**
	 * Set the iterator which will be decorated/filtered.
	 * @param iterator the iterator which will be decorated
	 */
	public IteratorDecorator(Iterator<T> iterator) {
		this.iterator = iterator;
		this.current = 0;
	}
	
	/**
	 * Retrieve the iterator which is be decorated
	 * @return the inventoryIterator
	 */
	public Iterator<T> getIterator() {
		return iterator;
	}

	/**
	 * Set the iterator which is be decorated
	 * @param inventoryIterator the inventoryIterator to set
	 */
	public void setIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public int getCurrentIndex() {
		return this.current;
	}
	
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	/**
	 * Grab the next item in the list. This will filter out items which don't match the given criteria.
	 */
	abstract public T next();
	
	/**
	 * Returns what this filter is filtering for.
	 * @return String
	 */
	abstract public String getCriteria();
}
