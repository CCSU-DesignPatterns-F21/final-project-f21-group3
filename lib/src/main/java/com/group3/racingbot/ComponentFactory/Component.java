package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.IClonable;
import com.group3.racingbot.inventory.Unique;
import com.group3.racingbot.inventory.filter.ComponentFilterable;
import com.group3.racingbot.inventory.filter.MaterialFilterable;
import com.group3.racingbot.inventory.filter.Quality;

/**
 * @author Jack Gola
 * Defines the abstract class of component, defines getters and setters for common variables
 * alongside common functionality
 */

@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EngineComponent.class),
        @JsonSubTypes.Type(value = SuspensionComponent.class),
        @JsonSubTypes.Type(value = TransmissionComponent.class),
        @JsonSubTypes.Type(value = ChassisComponent.class),
        @JsonSubTypes.Type(value = WheelComponent.class)})
@BsonDiscriminator
public abstract class Component implements Unique, IClonable, MaterialFilterable, ComponentFilterable {
	private String id = "";
	private ComponentType componentType = null;
	private Quality quality = Quality.LEMON;

	private int weight = 0, value = 0, durability = 0, rating = 0;
	private int maxDurability = 100;
	private String thumbnailURL = "";
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieve the rating of the component. The rating governs what to classify the component as. Classifications include LEMON, JUNKYARD, OEM, SPORTS, and RACING.
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	@Override
	public ComponentType getComponentType() {
		return componentType;
	}
	
	@Override
	public void setComponentType(ComponentType componentType) {
		this.componentType = componentType;
	}

	/**
	 * Set the quality classification of the component. Classifications include LEMON, JUNKYARD, OEM, SPORTS, and RACING.
	 * @param quality the quality to set
	 */
	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	/**
	 * Set the weight of the component.
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Set how much this component is worth.
	 * @param d the value to set
	 */
	public void setValue(int d) {
		this.value = d;
	}

	/**
	 * Set how durable this component is.
	 * @param durability the durability to set
	 */
	public void setDurability(int durability) {
		this.durability = durability;
	}

	/**
	 * Reset the durability of this component to the max durability.
	 */
	public void repair() {
		durability = maxDurability;
	}
	
	/**
	 * Set how durable this component can possibly be.
	 * @param sets maxDurability
	 */
	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}
	
	/**
	 * Retrieve the quality classification of the component. Classifications include LEMON, JUNKYARD, OEM, SPORTS, and RACING.
	 * @param returns Quality
	 */
	public Quality getQuality() {
		return quality;
	}
	
	/**
	 * Retrieve the weight of the component.
	 * @param returns weight
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Retrieve how much this component is worth.
	 * @param returns value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Retrieve how durable this component is.
	 * @param returns durability
	 */
	public int getDurability() {
		return durability;
	}
	
	/**
	 * Retrieve how durable this component can possibly be.
	 * @param returns max durability
	 */
	public int getMaxDurability() {
		return maxDurability;
	}
	
	/**
	 * Returns a percentage representing how worn down this component is.
	 * @param returns durability ratio
	 */
	public double calculateDurabilityRatio() {
		return  durability / maxDurability;
	}
	
	/**
	 * Retrieve the url to the image which displays when viewing this component
	 * @return the thumbnailURL
	 */
	public String getThumbnailURL() {
		return thumbnailURL;
	}

	/**
	 * Set the url to the image which displays when viewing this component
	 * @param thumbnailURL the thumbnailURL to set
	 */
	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	@Override
	abstract public IClonable clone();
	
	/**
	 * @param returns hashCode for component
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(durability, maxDurability, componentType.toString(), quality, value, weight);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Component) {
			Component otherObj = (Component) other;
			
			if (this.getDurability() != otherObj.getDurability())
				return false;
			if (this.getValue() != otherObj.getValue())
				return false;
			if (this.getRating() != otherObj.getRating())
				return false;
			if (this.getWeight() != otherObj.getWeight())
				return false;
			if (this.getComponentType().getComponentType() != otherObj.getComponentType().getComponentType()) {
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	/**
	 * @param returns toString for component
	 */

	@Override
	public String toString() {

		//return "Component [id=" + id + "quality=" + quality + ", type=" + componentType + ", weight=" + weight + ", value=" + value
		//		+ ", durability=" + durability + ", rating=" + rating + ", maxDurability=" + maxDurability + "]";
		return "id: " + this.id + " | " + this.componentType.toString() + " | Quality: " + this.quality + " | Value: " + this.value + " | Durability: " + this.durability + "/" + this.maxDurability + " | Weight: " + this.weight;
	}
}
