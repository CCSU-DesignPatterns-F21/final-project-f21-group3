package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
		this.setName("Engine");
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
