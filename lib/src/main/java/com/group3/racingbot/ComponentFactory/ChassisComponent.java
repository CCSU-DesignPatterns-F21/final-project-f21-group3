package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.IClonable;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables for chassis component
 */
public class ChassisComponent extends Component {
	private int popularity = 0;
	private double popularityModifier = 1.0;
	private double accelerationModifier = 1.0;
	private double speedModifier = 1.0;
	private double handlingModifier = 1.0;
	private double brakingModifier = 1.0;
	
	/**
	 * Constructor for chassis component
	 */
	@BsonCreator
	public ChassisComponent() {
		this.setComponentType(ComponentType.CHASSIS);
	}
	
	/**
	 * Alternate constructor, takes an existing object of the same type, for use with prototype.
	 * @param cc ChassisComponent object reference
	 */
	public ChassisComponent(ChassisComponent cc)
	{
		this.setComponentType(ComponentType.CHASSIS);
		this.setId(cc.getId());
		this.setQuality(cc.getQuality());
		this.setWeight(cc.getWeight());
		this.setValue(cc.getValue());
		this.setDurability(cc.getDurability());
		this.setMaxDurability(cc.getMaxDurability());
		this.setThumbnailURL(cc.getThumbnailURL());
		
		this.setPopularity(cc.getPopularity());
		this.setPopularityModifier(cc.getPopularityModifier());
		this.setAccelerationModifier(cc.getAccelerationModifier());
		this.setSpeedModifier(cc.getSpeedModifier());
		this.setHandlingModifier(cc.getHandlingModifier());
		this.setBrakingModifier(cc.getBrakingModifier());
		this.getRating();
		
	}
	

	/**
	 * Retrieve the braking modifier. This modifier/multiplier affects the braking capability of the car.
	 * @return the brakingModifier
	 */
	public double getBrakingModifier() {
		return brakingModifier;
	}

	/**
	 * Set the braking modifier. This modifier/multiplier affects the braking capability of the car.
	 * @param brakingModifier the brakingModifier to set
	 */
	public void setBrakingModifier(double brakingModifier) {
		this.brakingModifier = brakingModifier;
	}
	
	/**
	 * Retrieve the popularity value for this chassis.
	 * @return base popularity value
	 */
	public int getPopularity() {
		return popularity;
	}
	
	/**
	 * Set the popularity value for this chassis.
	 * @param popularity the popularity value to set
	 */
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	/**
	 * Retrieve the popularity modifier. This modifier/multiplier affects the popularity of the car.
	 * @return the popularityModifier
	 */
	public double getPopularityModifier() {
		return popularityModifier;
	}

	/**
	 * Set the popularity modifier. This modifier/multiplier affects the popularity of the car.
	 * @param popularityModifier the popularityModifier to set
	 */
	public void setPopularityModifier(double popularityModifier) {
		this.popularityModifier = popularityModifier;
	}

	/**
	 * Retrieve the acceleration modifier. This modifier/multiplier affects the acceleration capability of the car.
	 * @return the accelerationModifier
	 */
	public double getAccelerationModifier() {
		return accelerationModifier;
	}

	/**
	 * Set the acceleration modifier. This modifier/multiplier affects the acceleration capability of the car.
	 * @param accelerationModifier the accelerationModifier to set
	 */
	public void setAccelerationModifier(double accelerationModifier) {
		this.accelerationModifier = accelerationModifier;
	}

	/**
	 * Retrieve the speed modifier. This modifier/multiplier affects the speed capability of the car.
	 * @return the speedModifier
	 */
	public double getSpeedModifier() {
		return speedModifier;
	}

	/**
	 * Set the speed modifier. This modifier/multiplier affects the speed capability of the car.
	 * @param speedModifier the speedModifier to set
	 */
	public void setSpeedModifier(double speedModifier) {
		this.speedModifier = speedModifier;
	}

	/**
	 * Retrieve the handling modifier. This modifier/multiplier affects the handling capability of the car.
	 * @return the handlingModifier
	 */
	public double getHandlingModifier() {
		return handlingModifier;
	}

	/**
	 * Set the handling modifier. This modifier/multiplier affects the handling capability of the car.
	 * @param handlingModifier the handlingModifier to set
	 */
	public void setHandlingModifier(double handlingModifier) {
		this.handlingModifier = handlingModifier;
	}

	/**
	 * returns hashCode() for chassis component
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(accelerationModifier, brakingModifier, handlingModifier, popularity, popularityModifier, speedModifier);
	}
	
	/**
	 * Checks to see if the modifiers and max durability between two chassis components are equal
	 */
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof ChassisComponent) {
			ChassisComponent otherObj = (ChassisComponent) other;
			
			if (this.getBrakingModifier() != otherObj.getBrakingModifier())
				return false;
			if (this.getPopularityModifier() != otherObj.getPopularityModifier())
				return false;
			if (this.getHandlingModifier() != otherObj.getHandlingModifier())
				return false;
			if (this.getAccelerationModifier() != otherObj.getAccelerationModifier())
				return false;
			if (this.getSpeedModifier() != otherObj.getSpeedModifier())
				return false;
			if (this.getMaxDurability() != otherObj.getMaxDurability())
				return false;
			return true;
		}
		return false;
	}

	/**
	 * returns toString() for chassis component
	 */
	@Override
	public String toString() {
		//return this.getComponentType().toString() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nWeight: " + this.getWeight() + "\nPopularity: " + this.getPopularityModifier()
		//+ "\nAcceleration Modifier: " + getAccelerationModifier() + "\nSpeed Modifier: " + getSpeedModifier() + "\nHandling Modifier: " + getHandlingModifier() + "\nBraking Modifier: " + getBrakingModifier() + "\n\n";
		return super.toString() + " | Popularity Modifier: " + this.getPopularityModifier() + " | Acceleration Modifier: " + getAccelerationModifier() + " | Speed Modifier: " + getSpeedModifier() + " | Handling Modifier: " + getHandlingModifier() + " | Braking Modifier: " + getBrakingModifier();
	}
	
	/**
	 * Creates a clone of this exact object
	 * @return a IClonable object, an exact copy.
	 */
	@Override
	public IClonable clone() {
		return new ChassisComponent(this);
	}
	

}