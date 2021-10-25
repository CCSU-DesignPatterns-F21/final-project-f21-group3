package com.group3.racingbot.ComponentFactory;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables for chassis component
 */

public class ChassisComponent extends Component {
	
	private int popularity;
	private float popularityModifier, accelerationModifier, speedModifier, handlingModifier, brakingModifier;
	
	/**
	 * Constructor for chassis component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param popularityModifier
	 * @param accelerationModifier
	 * @param speedModifier
	 * @param handlingModifier
	 * @param brakingModifier
	 */
	
	public ChassisComponent(String quality, int value, int durability, int popularity, float popularityModifier, float accelerationModifier, float speedModifier, float handlingModifier, float brakingModifier) {
		this.setName("Chassis");
		this.setQuality(quality);
		this.setValue(value);
		this.setDurability(durability);
		this.setPopularityModifier(popularityModifier);
		this.setAccelerationModifier(accelerationModifier);
		this.setSpeedModifier(speedModifier);
		this.setHandlingModifier(handlingModifier);
		this.setBrakingModifier(brakingModifier);
	}

	/**
	 * @return the brakingModifier
	 */
	public float getBrakingModifier() {
		return brakingModifier;
	}

	/**
	 * @param brakingModifier the brakingModifier to set
	 */
	public void setBrakingModifier(float brakingModifier) {
		this.brakingModifier = brakingModifier;
	}
	
	/**
	 * @return base popularity value
	 */
	public int getPopularity() {
		return popularity;
	}
	
	/**
	 * @param popularity the popularity value to set
	 */
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	/**
	 * @return the popularityModifier
	 */
	public float getPopularityModifier() {
		return popularityModifier;
	}

	/**
	 * @param popularityModifier the popularityModifier to set
	 */
	public void setPopularityModifier(float popularityModifier) {
		this.popularityModifier = popularityModifier;
	}

	/**
	 * @return the accelerationModifier
	 */
	public float getAccelerationModifier() {
		return accelerationModifier;
	}

	/**
	 * @param accelerationModifier the accelerationModifier to set
	 */
	public void setAccelerationModifier(float accelerationModifier) {
		this.accelerationModifier = accelerationModifier;
	}

	/**
	 * @return the speedModifier
	 */
	public float getSpeedModifier() {
		return speedModifier;
	}

	/**
	 * @param speedModifier the speedModifier to set
	 */
	public void setSpeedModifier(float speedModifier) {
		this.speedModifier = speedModifier;
	}

	/**
	 * @return the handlingModifier
	 */
	public float getHandlingModifier() {
		return handlingModifier;
	}

	/**
	 * @param handlingModifier the handlingModifier to set
	 */
	public void setHandlingModifier(float handlingModifier) {
		this.handlingModifier = handlingModifier;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime * this.popularity;
		return result;
	}
	
	/**
	 * returns toString() for chassis component
	 */
	@Override
	public String toString() {
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nPopularity: " + this.getPopularityModifier()
		+ "\nAcceleration Modifier: " + getAccelerationModifier() + "\nSpeed Modifier: " + getSpeedModifier() + "\nHandling Modifier: " + getHandlingModifier() + "\nBraking Modifier: " + getBrakingModifier() + "\n\n";
	}
	
	
}
