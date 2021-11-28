package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.IClonable;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */
//@BsonDiscriminator(value="WheelComponent", key="_cls")
public class WheelComponent extends Component {
	//@BsonProperty("braking")
	private float braking;
	
	/**
	 * Constructor for wheel component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param braking
	 */
	@BsonCreator
	public WheelComponent() {
		this.setComponentType(ComponentType.WHEELS);
	}
	
	/**
	 * Alternate constructor, takes an existing object of the same type, for use with prototype.
	 * @param wc TransmissionComponent object reference
	 */
	public WheelComponent(WheelComponent wc)
	{
		this.setComponentType(ComponentType.WHEELS);
		this.setId(wc.getId());
		this.setQuality(wc.getQuality());
		this.setWeight(wc.getWeight());
		this.setValue(wc.getValue());
		this.setDurability(wc.getDurability());
		this.setMaxDurability(wc.getMaxDurability());
		this.setThumbnailURL(wc.getThumbnailURL());
		
		this.setBraking(wc.getBraking());
		this.getRating();
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
	 * returns hashCode() for wheel component
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(braking);
	}
	
	/**
	 * returns equals() for wheel component
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WheelComponent other = (WheelComponent) obj;
		return Float.floatToIntBits(braking) == Float.floatToIntBits(other.braking);
	}
	/**
	 * returns toString() for wheel component
	 */
	@Override
	public String toString() {
		return this.getComponentType().toString() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nWeight: " + this.getWeight() + "\nBraking: " + this.getBraking() + "\n\n";
	}
	
	/**
	 * Creates a clone of this exact object
	 * @return a IClonable object, an exact copy.
	 */
	@Override
	public IClonable clone() {
		// TODO Auto-generated method stub
		return new WheelComponent(this);
	}

}