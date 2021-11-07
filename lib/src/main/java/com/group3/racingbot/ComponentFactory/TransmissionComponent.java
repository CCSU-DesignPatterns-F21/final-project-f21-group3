package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */

public class TransmissionComponent extends Component {
	@BsonProperty("acceleration")
	private float acceleration;
	
	/**
	 * Constructor for transmission component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param acceleration
	 */
	@BsonCreator
	public TransmissionComponent() {
		this.setName("Transmission");
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
	 * returns hashCode() for transmission component
	 */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(acceleration);
		return result;
	}
	
	/**
	 * returns equals() for transmission component
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransmissionComponent other = (TransmissionComponent) obj;
		return Float.floatToIntBits(acceleration) == Float.floatToIntBits(other.acceleration);
	}

	/**
	 * returns toString() for transmission component
	 */
	
	@Override
	public String toString() {
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nAcceleration: " + this.getAcceleration() + "\n\n";
	}


}
