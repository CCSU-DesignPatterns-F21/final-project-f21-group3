package com.group3.racingbot.ComponentFactory;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public abstract class Component {
	private String quality = "", name = "";
	private int weight = 0, value = 0, durability = 0;
	private int maxDurability = 100;				
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @param durability the durability to set
	 */
	public void setDurability(int durability) {
		this.durability = durability;
	}

	/**
	 * @param maxDurability the maxDurability to set
	 */
	public void repair() {
		durability = 100;
	}
	
	/**
	 * @param sets maxDurability
	 */
	
	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}
	
	/**
	 * @param returns Quality
	 */
	
	public String getQuality() {
		return quality;
	}
	
	/**
	 * @param returns weight
	 */
	
	public int getWeight() {
		return weight;
	}
	
	/**
	 * @param returns value
	 */
	
	public int getValue() {
		return value;
	}
	
	/**
	 * @param returns durability
	 */
	
	public int getDurability() {
		return durability;
	}
	
	/**
	 * @param returns max durability
	 */
	
	public int getMaxDurability() {
		return durability;
	}
	
	/**
	 * Returns a percentage representing how worn down this component is.
	 * @param returns durability ratio
	 */
	public double calculateDurabilityRatio() {
		return  durability / maxDurability;
	}
	
	/**
	 * @param returns hashCode for component
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(durability, maxDurability, name, quality, value, weight);
	}
	
	/**
	 * @param returns boolean for component
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		return durability == other.durability && maxDurability == other.maxDurability
				&& Objects.equals(name, other.name) && Objects.equals(quality, other.quality)
				&& value == other.value && weight == other.weight;
	}
	
	/**
	 * @param returns toString for component
	 */

	@Override
	public String toString() {
		return "Component [quality=" + quality + ", name=" + name + ", weight=" + weight + ", value=" + value
				+ ", durability=" + durability + ", maxDurability=" + maxDurability + "]";
	}
}
