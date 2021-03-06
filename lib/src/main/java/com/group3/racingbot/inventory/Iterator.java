package com.group3.racingbot.inventory;

/**
 * Ensures that any class which implements this can iterate through some data structure. Otherwise known as the iterator pattern.
 * @author Nick Sabia
 *
 * @param <T>
 */
public interface Iterator<T> {
	/**
	 * Verifies that there is another item ahead of the current one.
	 * @return whether or not there is another item ahead of the current one.
	 */
	public boolean hasNext();
	
	/**
	 * Grab the next item.
	 * @return T
	 */
	public T next();
	
	/**
	 * Retrieve the current index which the iterator is on.
	 * @return current index of the iterator
	 */
	public int getCurrentIndex();
}
