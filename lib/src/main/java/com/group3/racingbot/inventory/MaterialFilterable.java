package com.group3.racingbot.inventory;

/**
 * Classes which implement this can be filtered by a concrete InventoryIteratorDecorator which filters by some physical property.
 * @author Nick Sabia
 *
 */
public interface MaterialFilterable {
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
