package com.group3.racingbot;

/**
 * @author Jack Gola
 * Defines the abstract class of component, defines getters and setters for common variables
 * alongside common functionality
 */
public abstract class Component {
	private String quality;
	private int weight, value, durability, rating;
	private int maxDurability = 100;				
	
	
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
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

	//price commented out for now	
	
	/**
	 * @param price the price to set
	 */
	//public void setPrice(int price) {
		//this.price = price;
	//}

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
	
	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}
	
	public String getQuality() {
		return quality;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getDurability() {
		return durability;
	}
	
	public int getMaxDurability() {
		return durability;
	}
	
	public int computeRating() {
		return rating;
	}
	
	public int getDurabilityRatio() {
		return  durability / maxDurability;
	}
	
	
	
}
