/**
 * 
 */
package com.group3.racingbot;

import com.group3.racingbot.ComponentFactory.ChassisComponent;
import com.group3.racingbot.ComponentFactory.EngineComponent;
import com.group3.racingbot.ComponentFactory.SuspensionComponent;
import com.group3.racingbot.ComponentFactory.TransmissionComponent;
import com.group3.racingbot.ComponentFactory.WheelComponent;
import com.group3.racingbot.inventory.Filterable;

/**
 * A car which contains five car components: A chassis, engine, suspension, transmission, and wheels.
 * 
 * Using the five car components, this class can tell you the stats of the car.
 * @author Nick Sabia
 *
 */
public class Car implements Filterable {
	/*
	- chassis : ChassisComponent
	- engine : EngineComponent
	- suspension : SuspensionComponent
	- transmission : TransmissionComponent
	- wheels : WheelComponent
	- rating: int
	- durability : int
	- price : int
	- quality : String
	- weight : int
	- popularity : float
	- acceleration : float
	- speed : float
	- handling : float
	- braking : float 
	 */
	private ChassisComponent chassis;
	private EngineComponent engine;
	private SuspensionComponent suspension;
	private TransmissionComponent transmission;
	private WheelComponent wheels;
	private int durability; // Calculated.
	private int price; // Calculated.
	private String quality; // Calculated based on overall rating.
	private int weight; // Calculated.
	private float popularityRating;
	private float accelerationRating;
	private float speedRating;
	private float handlingRating;
	private float brakingRating;
	
	public Car(int durability, int price, String quality, int weight) {
		this.durability = durability;
		this.price = price;
		this.quality = quality;
		this.weight = weight;
	}
	
	/**
	 * Gets the minimum durability value amongst the five car components.
	 */
	public int getDurability() {
		int minDurability = Math.min(this.chassis.getDurability(), this.engine.getDurability());
		minDurability = Math.min(minDurability, this.suspension.getDurability());
		minDurability = Math.min(minDurability, this.transmission.getDurability());
		minDurability = Math.min(minDurability, this.wheels.getDurability());
		return minDurability;
	}
	
	/**
	 * Get the price of the car.
	 * 
	 * Values the car based on the value of all car components combined.
	 */
	public int getPrice() {
		return this.chassis.getValue()
			 + this.engine.getValue()
			 + this.suspension.getValue()
			 + this.transmission.getValue()
			 + this.wheels.getValue();
	}
	
	/**
	 * Get the overall rating of the car.
	 * 
	 * Rates the car based on the rating of all car components combined.
	 */
	public int getRating() {
		return this.chassis.getRating()
			 + this.engine.getRating()
			 + this.suspension.getRating()
			 + this.transmission.getRating()
			 + this.wheels.getRating();
	}
	
	/**
	 * Get the quality of the car.
	 * 
	 * Classifies the quality of the car based on the car's rating.
	 */
	public String getQuality() {
		int overallRating = this.getRating();
		if (overallRating < 100)
			return "Lemon";
		else if (overallRating < 200) 
			return "Junkyard";
		else if (overallRating < 300) 
			return "OEM";
		else if (overallRating < 400) 
			return "Sports";
		else {
			return "Racing";
		}
	}
	
	/**
	 * Get the weight of the car.
	 * 
	 * Weighs the car based on the weight of all car components combined.
	 */
	public int getWeight() {
		return this.chassis.getWeight()
			 + this.engine.getWeight()
			 + this.suspension.getWeight()
			 + this.transmission.getWeight()
			 + this.wheels.getWeight();
	}
	
	/**
	 * Get the popularity rating of the car.
	 * 
	 * @return the popularity rating of the car.
	 */
	public float getPopularityRating() {
		return this.chassis.getPopularity() * this.chassis.getPopularityModifier();
	}
	
	/**
	 * Get the speed rating of the car.
	 * 
	 * @return the speed rating of the car.
	 */
	public float getSpeedRating() {
		return this.engine.getSpeed() * this.chassis.getSpeedModifier();
	}
	
	/**
	 * Get the handling rating of the car.
	 * 
	 * @return the handling rating of the car.
	 */
	public float getHandlingRating() {
		return this.suspension.getHandling() * this.chassis.getHandlingModifier();
	}
	
	/**
	 * Get the acceleration rating of the car.
	 * 
	 * @return the acceleration rating of the car.
	 */
	public float getAccelerationRating() {
		return this.transmission.getAcceleration() * this.chassis.getAccelerationModifier();
	}
	
	/**
	 * Get the braking rating of the car.
	 * 
	 * @return the braking rating of the car.
	 */
	public float getBrakingRating() {
		return this.wheels.getBraking() * this.chassis.getBrakingModifier();
	}
	
	@Override
	public String toString() {
		return "Durability: " + this.durability + " | Price: " + this.price + " | Quality: " + this.quality + " | Weight: " + this.weight;
	}
}
