package com.group3.racingbot.ComponentFactory;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class SuspensionComponent extends Component {
	
	private float handling;
	//TODO: tunedFor
	
	/**
	 * Constructor for suspension component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param handling
	 */
	
	public SuspensionComponent(String quality, int value, int durability, float handling) {
		this.setName("Suspension");
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
	
	/**
	 * returns toString() for suspension component
	 */
	
	@Override
	public String toString() {
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nHandling: " + this.getHandling() + "\n\n";
	}
	
}
