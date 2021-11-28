package com.group3.racingbot.inventory.filter;

/**
 * Classes which implement this can be filtered by a concrete IteratorDecorator which filters by some physical property.
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
	public int getValue();
	/**
	 * Retrieves the quality label.
	 * @return String
	 */
	public Quality getQuality();
	/**
	 * Retrieves the weight value.
	 * @return int
	 */
	public int getWeight();
}
