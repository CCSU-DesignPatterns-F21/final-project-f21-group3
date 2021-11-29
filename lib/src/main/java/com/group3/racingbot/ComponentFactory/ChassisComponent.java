package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.IClonable;

/**
 * @author Jack Gola
 * Specialized class of Component abstract class
 * defines specialized variables for chassis component
 */
//@BsonDiscriminator(value="ChassisComponent", key="_cls")
public class ChassisComponent extends Component {
	//@BsonProperty("popularity")
	private int popularity = 0;
	//@BsonProperty("popularityModifier")
	private double popularityModifier = 1.0;
	//@BsonProperty("accelerationModifier")
	private double accelerationModifier = 1.0;
	//@BsonProperty("speedModifier")
	private double speedModifier = 1.0;
	//@BsonProperty("handlingModifier")
	private double handlingModifier = 1.0;
	//@BsonProperty("brakingModifier")
	private double brakingModifier = 1.0;
	
	/**
	 * Constructor for chassis component
	 */
	@BsonCreator
	public ChassisComponent() {
		this.setComponentType(ComponentType.CHASSIS);
	}
	
	/**
	 * Alternate constructor, takes an existing object of the same type, for use with prototype.
	 * @param cc ChassisComponent object reference
	 */
	public ChassisComponent(ChassisComponent cc)
	{
		this.setComponentType(ComponentType.CHASSIS);
		this.setId(cc.getId());
		this.setQuality(cc.getQuality());
		this.setWeight(cc.getWeight());
		this.setValue(cc.getValue());
		this.setDurability(cc.getDurability());
		this.setMaxDurability(cc.getMaxDurability());
		this.setThumbnailURL(cc.getThumbnailURL());
		
		this.setPopularity(cc.getPopularity());
		this.setPopularityModifier(cc.getPopularityModifier());
		this.setAccelerationModifier(cc.getAccelerationModifier());
		this.setSpeedModifier(cc.getSpeedModifier());
		this.setHandlingModifier(cc.getHandlingModifier());
		this.setBrakingModifier(cc.getBrakingModifier());
		this.getRating();
		
	}
	

	/**
	 * @return the brakingModifier
	 */
	public double getBrakingModifier() {
		return brakingModifier;
	}

	/**
	 * @param brakingModifier the brakingModifier to set
	 */
	public void setBrakingModifier(double brakingModifier) {
		this.brakingModifier = brakingModifier;
	}
	
	/**
	 * @return base popularity value
	 */
	public int getPopularity() {
		return popularity;
	}
	
	/**
	 * @param popularity the popularity value to set
	 */
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	/**
	 * @return the popularityModifier
	 */
	public double getPopularityModifier() {
		return popularityModifier;
	}

	/**
	 * @param popularityModifier the popularityModifier to set
	 */
	public void setPopularityModifier(double popularityModifier) {
		this.popularityModifier = popularityModifier;
	}

	/**
	 * @return the accelerationModifier
	 */
	public double getAccelerationModifier() {
		return accelerationModifier;
	}

	/**
	 * @param accelerationModifier the accelerationModifier to set
	 */
	public void setAccelerationModifier(double accelerationModifier) {
		this.accelerationModifier = accelerationModifier;
	}

	/**
	 * @return the speedModifier
	 */
	public double getSpeedModifier() {
		return speedModifier;
	}

	/**
	 * @param speedModifier the speedModifier to set
	 */
	public void setSpeedModifier(double speedModifier) {
		this.speedModifier = speedModifier;
	}

	/**
	 * @return the handlingModifier
	 */
	public double getHandlingModifier() {
		return handlingModifier;
	}

	/**
	 * @param handlingModifier the handlingModifier to set
	 */
	public void setHandlingModifier(double handlingModifier) {
		this.handlingModifier = handlingModifier;
	}

	/**
	 * returns hashCode() for chassis component
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(accelerationModifier, brakingModifier, handlingModifier, popularity, popularityModifier, speedModifier);
	}
	
	/**
	 * Checks to see if the modifiers and max durability between two chassis components are equal
	 */
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof ChassisComponent) {
			ChassisComponent otherObj = (ChassisComponent) other;
			
			if (this.getBrakingModifier() != otherObj.getBrakingModifier())
				return false;
			if (this.getPopularityModifier() != otherObj.getPopularityModifier())
				return false;
			if (this.getHandlingModifier() != otherObj.getHandlingModifier())
				return false;
			if (this.getAccelerationModifier() != otherObj.getAccelerationModifier())
				return false;
			if (this.getSpeedModifier() != otherObj.getSpeedModifier())
				return false;
			if (this.getMaxDurability() != otherObj.getMaxDurability())
				return false;
			return true;
		}
		return false;
	}

	/**
	 * returns toString() for chassis component
	 */
	@Override
	public String toString() {
		//return this.getComponentType().toString() + "\nQuality: " + this.getQuality() + "\nValue: " + this.getValue() + "\nDurability: " + this.getDurability() + "\nWeight: " + this.getWeight() + "\nPopularity: " + this.getPopularityModifier()
		//+ "\nAcceleration Modifier: " + getAccelerationModifier() + "\nSpeed Modifier: " + getSpeedModifier() + "\nHandling Modifier: " + getHandlingModifier() + "\nBraking Modifier: " + getBrakingModifier() + "\n\n";
		return super.toString() + " | Popularity Modifier: " + this.getPopularityModifier() + " | Acceleration Modifier: " + getAccelerationModifier() + " | Speed Modifier: " + getSpeedModifier() + " | Handling Modifier: " + getHandlingModifier() + " | Braking Modifier: " + getBrakingModifier();
	}
	
	/**
	 * Creates a clone of this exact object
	 * @return a IClonable object, an exact copy.
	 */
	@Override
	public IClonable clone() {
		// TODO Auto-generated method stub
		return new ChassisComponent(this);
	}
	

}