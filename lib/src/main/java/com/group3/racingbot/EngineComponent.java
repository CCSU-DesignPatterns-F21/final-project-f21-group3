package com.group3.racingbot;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class EngineComponent extends Component{
	
	private float speed;
	//TODO: tunedFor
	
	public EngineComponent(String quality, int value, int durability, float speed) {
		this.setQuality(quality);
		this.setValue(value);
		this.setDurability(durability);
		this.setSpeed(speed);
	}
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}

