package com.group3.racingbot.inventory;

/**
 * Classes which implement this can be filtered by a concrete InventoryIteratorDecorator which filters by some skill related property.
 * @author Nick Sabia
 *
 */
public interface SkillFilterable {
	/**
	 * Retrieves the composure skill value.
	 * @return int
	 */
	public int getComposure();
	/**
	 * Retrieves the awareness skill value.
	 * @return int
	 */
	public int getAwareness();
	/**
	 * Retrieves the drafting skill label.
	 * @return int
	 */
	public int getDrafting();
	/**
	 * Retrieves the straights skill value.
	 * @return int
	 */
	public int getStraights();
	/**
	 * Retrieves the cornering skill value.
	 * @return int
	 */
	public int getCornering();
	/**
	 * Retrieves the recovery skill value.
	 * @return int
	 */
	public int getRecovery();
}
