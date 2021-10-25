package com.group3.racingbot.ComponentFactory;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class WheelComponent extends Component {
	
	private float braking;
	//TODO: tunedFor
	
	/**
	 * Constructor for wheel component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param braking
	 */
	
	public WheelComponent(String quality, int value, int durability, float braking) {
		this.setName("Wheel");
		this.setQuality(quality);
		this.setValue(value);
		this.setDurability(durability);
		this.setBraking(braking);
	}
	/**
	 * @return the braking
	 */
	public float getBraking() {
		return braking;
	}

	/**
	 * @param braking the braking to set
	 */
	public void setBraking(float braking) {
		this.braking = braking;
	}
	
	/**
	 * returns toString() for wheel component
	 */
	
	@Override
	public String toString() {
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nBraking: " + this.getBraking() + "\n\n";
	}

}
