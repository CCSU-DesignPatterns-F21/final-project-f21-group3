/**
 * 
 */
package com.group3.racingbot.inventory;

/**
 * Thrown when something cannot be found in a list or inventory
 * @author Nick Sabia
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 4863129411524305253L;

	/**
	 * Indicates that something cannot be found.
	 * @param message the message to print when the exception is thrown
	 */
	public NotFoundException(String message) {
		super(message);
	}

}
