/**
 * 
 */
package com.group3.racingbot.inventory;

/**
 * @author Nick Sabia
 *
 */
public interface Unique {
	/**
	 * Retrieves an identifier which is unique in some way.
	 * @return a string which represents a unique id
	 */
	public String getId();
	
	/**
	 * Sets an identifier which is unique in some way.
	 * @param id the id to set
	 */
	public void setId(String id);
}
