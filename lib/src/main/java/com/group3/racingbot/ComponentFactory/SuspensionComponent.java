package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
	@BsonCreator
	public SuspensionComponent(@BsonProperty("quality") String quality, 
			@BsonProperty("value") int value,
			@BsonProperty("durability") int durability,
			@BsonProperty("handling") float handling) {
		
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
		return this.getName() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nHandling: " + this.getHandling() + "\n\n";
	}
}
