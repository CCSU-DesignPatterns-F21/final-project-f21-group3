package com.group3.racingbot;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables for chassis component
 */

public class ChassisComponent extends Component {
	
	private float popularityModifier, accelerationModifier, speedModifier, handlingModifier, brakingModifier;
	
	public ChassisComponent(String quality, int value, int durability, float popularityModifier, float accelerationModifier, float speedModifier, float handlingModifier, float brakingModifier) {
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
	
	
}
