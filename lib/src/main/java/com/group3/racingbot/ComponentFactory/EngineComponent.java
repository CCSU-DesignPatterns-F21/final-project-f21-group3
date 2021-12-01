package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.IClonable;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables
 */
//@BsonDiscriminator(value="EngineComponent", key="_cls")
public class EngineComponent extends Component {
	//@BsonProperty("speed")
	private float speed = 25;
	
	/**
	 * Constructor for engine component
	 */
	@BsonCreator
	public EngineComponent() {
		this.setComponentType(ComponentType.ENGINE);
	}
	/**
	 * Alternate constructor, takes an existing object of the same type, for use with prototype.
	 * @param ec EngineComponent object reference
	 */
	public EngineComponent(EngineComponent ec) {
		this.setComponentType(ComponentType.ENGINE);
		this.setId(ec.getId());
		this.setQuality(ec.getQuality());
		this.setWeight(ec.getWeight());
		this.setValue(ec.getValue());
		this.setDurability(ec.getDurability());
		this.setMaxDurability(ec.getMaxDurability());
		this.setThumbnailURL(ec.getThumbnailURL());
		
		this.setSpeed(ec.getSpeed());
		this.getRating();
	}
	
	/**
	 * Retrieve the speed which this engine is capable of performing.
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Set the speed which this engine is capable of performing.
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
		return super.toString() + " | Speed: " + this.getSpeed();
	}

	/**
	 * Creates a clone of this exact object
	 * @return a IClonable object, an exact copy.
	 */
	@Override
	public IClonable clone() {
		return new EngineComponent(this);
	}
	
	
}
