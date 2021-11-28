package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.IClonable;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */
//@BsonDiscriminator(value="TransmissionComponent", key="_cls")
public class TransmissionComponent extends Component {
	//@BsonProperty("acceleration")
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
		this.setComponentType(ComponentType.TRANSMISSION);
	}
	
	/**
	 * Alternate constructor, takes an existing object of the same type, for use with prototype.
	 * @param tc TransmissionComponent object reference
	 */
	public TransmissionComponent(TransmissionComponent tc)
	{
		this.setComponentType(ComponentType.TRANSMISSION);
		this.setId(tc.getId());
		this.setQuality(tc.getQuality());
		this.setWeight(tc.getWeight());
		this.setValue(tc.getValue());
		this.setDurability(tc.getDurability());
		this.setMaxDurability(tc.getMaxDurability());
		this.setThumbnailURL(tc.getThumbnailURL());
		
		this.setAcceleration(tc.getAcceleration());
		this.getRating();
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
		return this.getComponentType().toString() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nWeight: " + this.getWeight() + "\nAcceleration: " + this.getAcceleration() + "\n\n";
	}
	
	/**
	 * Creates a clone of this exact object
	 * @return a IClonable object, an exact copy.
	 */
	@Override
	public IClonable clone() {
		// TODO Auto-generated method stub
		return new TransmissionComponent(this);
	}


}