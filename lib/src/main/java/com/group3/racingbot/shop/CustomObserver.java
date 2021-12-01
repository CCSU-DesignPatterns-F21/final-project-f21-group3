package com.group3.racingbot.shop;


/**
 * Custom Observer Interface for objects which can be updated from a subscribed subject.
 * @author Maciej Bregisz
 *
 */
public interface CustomObserver {
	
	/**
	 * Update all listeners of the observer
	 */
	public void update();

}
