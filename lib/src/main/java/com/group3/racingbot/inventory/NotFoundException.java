/**
 * 
 */
package com.group3.racingbot.inventory;

/**
 * Thrown when something cannot be found in a list or inventory
 * @author Nick Sabia
 *
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 4863129411524305253L;

	public NotFoundException(String message) {
		super(message);
	}

}
