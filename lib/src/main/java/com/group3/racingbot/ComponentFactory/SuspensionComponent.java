package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.IClonable;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */
//@BsonDiscriminator(value="SuspensionComponent", key="_cls")
public class SuspensionComponent extends Component {
	//@BsonProperty("handling")
	private float handling;
	
	/**
	 * Constructor for suspension component
	 * @param quality
	 * @param value
	 * @param durability
	 * @param handling
	 */
	@BsonCreator
	public SuspensionComponent() {
		this.setComponentType(ComponentType.SUSPENSION);
	}
	
	/**
	 * Alternate constructor, takes an existing object of the same type, for use with prototype.
	 * @param sc SuspensionComponent object reference
	 */
	public SuspensionComponent(SuspensionComponent sc) {
		this.setComponentType(ComponentType.SUSPENSION);
		this.setId(sc.getId());
		this.setQuality(sc.getQuality());
		this.setWeight(sc.getWeight());
		this.setValue(sc.getValue());
		this.setDurability(sc.getDurability());
		this.setMaxDurability(sc.getMaxDurability());
		this.setThumbnailURL(sc.getThumbnailURL());
		
		this.setHandling(sc.getHandling());
		this.getRating();
	}
	
	
	/**
	 * Retrieve the handling which this suspension is capable of performing
	 * @return the handling
	 */
	public float getHandling() {
		return handling;
	}

	/**
	 * Set the handling which this suspension is capable of performing
	 * @param handling the handling to set
	 */
	public void setHandling(float handling) {
		this.handling = handling;
	}
	
	/**
	 * returns hashCode() for suspension component
	 */

	@Override
	public int hashCode() {
		return Objects.hash(handling);
	}
	
	/**
	 * returns equals() for suspension component
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuspensionComponent other = (SuspensionComponent) obj;
		return Float.floatToIntBits(handling) == Float.floatToIntBits(other.handling);
	}
	/**
	 * returns toString() for suspension component
	 */
	@Override
	public String toString() {
		return super.toString() + " | Handling: " + this.getHandling();
	}
	
	/**
	 * Creates a clone of this exact object
	 * @return a IClonable object, an exact copy.
	 */
	@Override
	public IClonable clone() {
		return new SuspensionComponent(this);
	}
}