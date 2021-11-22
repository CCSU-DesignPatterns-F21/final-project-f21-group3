package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.inventory.Inventory;
/**
 * Abstract class for shops.
 * @author Maciej Bregisz
 *
 */
@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Junkyard.class),
        @JsonSubTypes.Type(value = ChopShop.class),
        @JsonSubTypes.Type(value = Dealership.class),
        @JsonSubTypes.Type(value = Importer.class)})
@BsonDiscriminator
public abstract class Shop implements CustomObserver{
	
	private String name;
	private String description;
	private int id;
	private Inventory<Car> carsForSale;
	private Inventory<Component> componentsForSale;
	private ComponentFactory factory;

	public abstract Component createComponent();

	
	@Override
	public String toString() {
		return getName() + " | " + getDescription();
	} 
	/**
	 * @return the name of the shop
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the shop name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description of the shop
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the shop description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the shop id
	 */
	 @BsonId
	public int getId() {
		return id;
	}
	/**
	 * @param id the shop id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the list of cars for sale
	 */
	public Inventory<Car> getCarsForSale() {
		return carsForSale;
	}
	/**
	 * @param carsForSale set the list of cars for sale
	 */
	public void setCarsForSale(Inventory<Car> carsForSale) {
		this.carsForSale = carsForSale;
	}
	/**
	 * @return the list of components for sale
	 */
	public Inventory<Component> getComponentsForSale() {
		return componentsForSale;
	}
	/**
	 * @param componentsForSale the list of components for sale to set
	 */
	public void setComponentsForSale(Inventory<Component> componentsForSale) {
		this.componentsForSale = componentsForSale;
	}
	
	/**
	 * @return the component factory
	 */
	public ComponentFactory getFactory() {
		return factory;
	}

	/**
	 * @param factory the component factory to set
	 */
	public void setFactory(ComponentFactory factory) {
		this.factory = factory;
	}

	/**
	 * Generate a hashCode for the object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carsForSale == null) ? 0 : carsForSale.hashCode());
		result = prime * result + ((componentsForSale == null) ? 0 : componentsForSale.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Compare and determine whether or not the two objects are identical or the same object.
	 * @param obj the Object being compared to
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (carsForSale == null) {
			if (other.carsForSale != null)
				return false;
		} else if (!carsForSale.equals(other.carsForSale))
			return false;
		if (componentsForSale == null) {
			if (other.componentsForSale != null)
				return false;
		} else if (!componentsForSale.equals(other.componentsForSale))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
