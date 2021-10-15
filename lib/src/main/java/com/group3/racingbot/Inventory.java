package com.group3.racingbot;

/**
 * @author Nick Sabia
 * Ensures that an inventory can iterate and filter through its components.
 */
public interface Inventory<T> {
	public Iterator<T> iterator();
}
