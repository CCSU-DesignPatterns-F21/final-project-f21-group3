package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class EngineComponent extends Component{
	
	private float speed;
	
	//TODO: tunedFor
	
	/**
	 * Constructor for engine component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param speed
	 */
	
	public EngineComponent(String quality, int value, int durability, float speed) {
		this.setName("Engine");
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
	
	/**
	 * returns hashCode() for engine component
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(speed);
	}
	
	/**
	 * returns equals() for engine component
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EngineComponent other = (EngineComponent) obj;
		return Float.floatToIntBits(speed) == Float.floatToIntBits(other.speed);
	}
	
	/**
	 * returns toString() for engine component
	 */

	@Override
	public String toString() {
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nSpeed: " + this.getSpeed() + "\n\n";
	}
	
	
}

