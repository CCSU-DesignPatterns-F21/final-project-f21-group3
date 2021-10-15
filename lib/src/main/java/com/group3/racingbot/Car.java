/**
 * 
 */
package com.group3.racingbot;

/**
 * @author Nick Sabia
 *
 */
public class Car implements Quantifiable{
	private int durability;
	private int price;
	private String quality;
	private int weight;
	
	public Car(int durability, int price, String quality, int weight) {
		this.durability = durability;
		this.price = price;
		this.quality = quality;
		this.weight = weight;
	}
	
	public int getDurability() {
		return this.durability;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public String getQuality() {
		return this.quality;
	}
	
	public int getWeight() {
		return this.weight;
	}
}
