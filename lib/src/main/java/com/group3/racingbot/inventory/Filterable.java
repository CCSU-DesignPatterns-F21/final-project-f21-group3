package com.group3.racingbot.inventory;

/**
 * An class which implements Filterable is able to be filtered by an InventoryIterator
 * @author Nick Sabia
 *
 */
public interface Filterable {
	/**
	 * Retrieves the durability value.
	 * @return int
	 */
	public int getDurability();
	/**
	 * Retrieves the price value.
	 * @return int
	 */
	public int getPrice();
	/**
	 * Retrieves the quality label.
	 * @return String
	 */
	public String getQuality();
	/**
	 * Retrieves the weight value.
	 * @return int
	 */
	public int getWeight();
}
