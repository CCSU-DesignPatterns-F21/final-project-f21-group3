/**
 * 
 */
package com.group3.racingbot;

import com.group3.racingbot.inventory.Quantifiable;

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
	
	public String toString() {
		return "Durability: " + this.durability + " | Price: " + this.price + " | Quality: " + this.quality + " | Weight: " + this.weight;
	}
}
