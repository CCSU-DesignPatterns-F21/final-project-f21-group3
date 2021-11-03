package com.group3.racingbot;

import com.group3.racingbot.ComponentFactory.ChassisComponent;
import com.group3.racingbot.ComponentFactory.EngineComponent;
import com.group3.racingbot.ComponentFactory.SuspensionComponent;
import com.group3.racingbot.ComponentFactory.TransmissionComponent;
import com.group3.racingbot.ComponentFactory.WheelComponent;
import com.group3.racingbot.inventory.filter.MaterialFilterable;

/**
 * A car which contains five car components: A chassis, engine, suspension, transmission, and wheels.
 * 
 * Using the five car components, this class can tell you the stats of the car.
 * @author Nick Sabia
 *
 */
public class Car implements MaterialFilterable {
	private ChassisComponent chassis;
	private EngineComponent engine;
	private SuspensionComponent suspension;
	private TransmissionComponent transmission;
	private WheelComponent wheels;
	
	/**
	 * Creates a car which can be equipped with different components.
	 */
	public Car() {
		this.chassis = null;
		this.engine = null;
		this.suspension = null;
		this.transmission = null;
		this.wheels = null;
	}
	
	/**
	 * @return the chassis
	 */
	public ChassisComponent getChassis() {
		return chassis;
	}

	/**
	 * @return the engine
	 */
	public EngineComponent getEngine() {
		return engine;
	}

	/**
	 * @return the suspension
	 */
	public SuspensionComponent getSuspension() {
		return suspension;
	}

	/**
	 * @return the transmission
	 */
	public TransmissionComponent getTransmission() {
		return transmission;
	}

	/**
	 * @return the wheels
	 */
	public WheelComponent getWheels() {
		return wheels;
	}

	/**
	 * @param chassis the chassis to set
	 */
	public void setChassis(ChassisComponent chassis) {
		this.chassis = chassis;
	}

	/**
	 * @param engine the engine to set
	 */
	public void setEngine(EngineComponent engine) {
		this.engine = engine;
	}

	/**
	 * @param suspension the suspension to set
	 */
	public void setSuspension(SuspensionComponent suspension) {
		this.suspension = suspension;
	}

	/**
	 * @param transmission the transmission to set
	 */
	public void setTransmission(TransmissionComponent transmission) {
		this.transmission = transmission;
	}

	/**
	 * @param wheels the wheels to set
	 */
	public void setWheels(WheelComponent wheels) {
		this.wheels = wheels;
	}

	/**
	 * 
	 * @return boolean based on if the car contains all parts.
	 */
	public boolean hasAllComponents() {
		if (this.chassis != null && this.engine != null && this.suspension != null && this.transmission != null && this.wheels != null)
			return true;
		return false;
	}
	
	/**
	 * Gets the minimum durability value amongst the five car components.
	 */
	public int getDurability() {
		if (this.hasAllComponents()) {
			int minDurability = Math.min(this.chassis.getDurability(), this.engine.getDurability());
			minDurability = Math.min(minDurability, this.suspension.getDurability());
			minDurability = Math.min(minDurability, this.transmission.getDurability());
			minDurability = Math.min(minDurability, this.wheels.getDurability());
			return minDurability;
		}
		return 0;
	}
	
	/**
	 * Get the price of the car.
	 * 
	 * Values the car based on the value of all car components combined.
	 */
	public int getPrice() {
		if (this.hasAllComponents()) {
			return this.chassis.getValue()
					 + this.engine.getValue()
					 + this.suspension.getValue()
					 + this.transmission.getValue()
					 + this.wheels.getValue();
		}
		return 0;
	}
	
	/**
	 * Get the overall rating of the car.
	 * 
	 * Rates the car based on the rating of all car components combined.
	 */
	public int getRating() {
		if (this.hasAllComponents()) {
			return this.chassis.getRating()
				 + this.engine.getRating()
				 + this.suspension.getRating()
				 + this.transmission.getRating()
				 + this.wheels.getRating();
		}
		return 0;
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
		if (this.hasAllComponents()) {
			return this.chassis.getWeight()
				 + this.engine.getWeight()
				 + this.suspension.getWeight()
				 + this.transmission.getWeight()
				 + this.wheels.getWeight();
		}
		return 0;
	}
	
	/**
	 * Get the popularity rating of the car.
	 * 
	 * @return the popularity rating of the car.
	 */
	public float getPopularityRating() {
		if (this.chassis != null)
			return this.chassis.getPopularity() * this.chassis.getPopularityModifier();
		return 0;
	}
	
	/**
	 * Get the speed rating of the car.
	 * 
	 * @return the speed rating of the car.
	 */
	public float getSpeedRating() {
		if (this.chassis != null && this.engine != null)
			return this.engine.getSpeed() * this.chassis.getSpeedModifier();
		return 0;
	}
	
	/**
	 * Get the handling rating of the car.
	 * 
	 * @return the handling rating of the car.
	 */
	public float getHandlingRating() {
		if (this.chassis != null && this.suspension != null)
			return this.suspension.getHandling() * this.chassis.getHandlingModifier();
		return 0;
	}
	
	/**
	 * Get the acceleration rating of the car.
	 * 
	 * @return the acceleration rating of the car.
	 */
	public float getAccelerationRating() {
		if (this.chassis != null && this.transmission != null)
			return this.transmission.getAcceleration() * this.chassis.getAccelerationModifier();
		return 0;
	}
	
	/**
	 * Get the braking rating of the car.
	 * 
	 * @return the braking rating of the car.
	 */
	public float getBrakingRating() {
		if (this.chassis != null && this.wheels != null)
			return this.wheels.getBraking() * this.chassis.getBrakingModifier();
		return 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getDurability() + this.getPrice() + this.getRating() + this.getWeight() + ((int) this.getPopularityRating()) + ((int) this.getSpeedRating()) + ((int) this.getHandlingRating()) + ((int) this.getBrakingRating()) + ((int) this.getAccelerationRating());
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Car) {
			Car otherObj = (Car) other;
			
			if (this.getDurability() != otherObj.getDurability())
				return false;
			if (this.getPrice() != otherObj.getPrice())
				return false;
			if (this.getRating() != otherObj.getRating())
				return false;
			if (this.getWeight() != otherObj.getWeight())
				return false;
			if (((int) this.getPopularityRating()) != ((int) otherObj.getPopularityRating()))
				return false;
			if (((int) this.getSpeedRating()) != ((int) otherObj.getSpeedRating()))
				return false;
			if (((int) this.getHandlingRating()) != ((int) otherObj.getHandlingRating()))
				return false;
			if (((int) this.getBrakingRating()) != ((int) otherObj.getBrakingRating()))
				return false;
			if (((int) this.getAccelerationRating()) != ((int) otherObj.getAccelerationRating()))
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Durability: " + this.getDurability() + " | Price: " + this.getPrice() + " | Quality: " + this.getQuality() + " | Weight: " + this.getWeight();
	}
}