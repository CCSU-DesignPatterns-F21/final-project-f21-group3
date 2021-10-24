package com.group3.racingbot;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class TransmissionComponent extends Component {
	
	private float acceleration;
	//TODO: tunedFor
	
	public TransmissionComponent(String quality, int value, int durability, float acceleration) {
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


}
