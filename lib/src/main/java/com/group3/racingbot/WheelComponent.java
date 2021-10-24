package com.group3.racingbot;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class WheelComponent extends Component {
	
	private String braking;
	//TODO: tunedFor
	
	public WheelComponent(String quality, int value, int durability, float braking) {
		this.setQuality(quality);
		this.setValue(value);
		this.setDurability(durability);
		this.setBraking(quality);
	}
	/**
	 * @return the braking
	 */
	public String getBraking() {
		return braking;
	}

	/**
	 * @param braking the braking to set
	 */
	public void setBraking(String braking) {
		this.braking = braking;
	}

}
