package com.group3.racingbot.ComponentFactory;

/**
 * @author Jack Gola
 * Defines the abstract class of component, defines getters and setters for common variables
 * alongside common functionality
 */
public abstract class Component {
	private String quality, name;
	private int weight, value, durability, rating;
	private int maxDurability = 100;				
	
	
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @param durability the durability to set
	 */
	public void setDurability(int durability) {
		this.durability = durability;
	}

	/**
	 * @param maxDurability the maxDurability to set
	 */
	public void repair() {
		durability = 100;
	}
	
	/**
	 * @param sets maxDurability
	 */
	
	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}
	
	/**
	 * @param returns Quality
	 */
	
	public String getQuality() {
		return quality;
	}
	
	/**
	 * @param returns weight
	 */
	
	public int getWeight() {
		return weight;
	}
	
	/**
	 * @param returns value
	 */
	
	public int getValue() {
		return value;
	}
	
	/**
	 * @param returns durability
	 */
	
	public int getDurability() {
		return durability;
	}
	
	/**
	 * @param returns max durability
	 */
	
	public int getMaxDurability() {
		return durability;
	}
	/**
	 * @param returns rating
	 */
	
	public int computeRating() {
		return rating;
	}
	
	/**
	 * @param returns durability ratio
	 */
	public int getDurabilityRatio() {
		return  durability / maxDurability;
	}
}
