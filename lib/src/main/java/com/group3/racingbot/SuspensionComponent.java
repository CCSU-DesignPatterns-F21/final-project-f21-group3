package com.group3.racingbot;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class SuspensionComponent extends Component {
	
	private float handling;
	//TODO: tunedFor
	
	public SuspensionComponent(String quality, int value, int durability, float handling) {
		this.setQuality(quality);
		this.setValue(value);
		this.setDurability(durability);
		this.setHandling(handling);
	}

	/**
	 * @return the handling
	 */
	public float getHandling() {
		return handling;
	}

	/**
	 * @param handling the handling to set
	 */
	public void setHandling(float handling) {
		this.handling = handling;
	}
	
}
