package com.group3.racingbot.ComponentFactory;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class TransmissionComponent extends Component {
	
	private float acceleration;
	//TODO: tunedFor
	
	/**
	 * Constructor for transmission component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param acceleration
	 */
	
	public TransmissionComponent(String quality, int value, int durability, float acceleration) {
		this.setName("Transmission");
		this.setQuality(quality);
		this.setValue(value);
		this.setDurability(durability);
		this.setAcceleration(acceleration);
	}

	/**
	 * @return the acceleration
	 */
	public float getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	
	/**
	 * returns toString() for transmission component
	 */
	
	@Override
	public String toString() {
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nAcceleration: " + this.getAcceleration() + "\n\n";
	}


}
