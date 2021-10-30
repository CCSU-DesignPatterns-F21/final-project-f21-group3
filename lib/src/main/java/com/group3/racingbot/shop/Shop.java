package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

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
	private CarInventory carsForSale;
	private ComponentInventory componentsForSale;

	public abstract Component createComponent();

	
	@Override
	public String toString() {
		return getName() + " | " + getDescription();
	}
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the id
	 */
	 @BsonId
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the carsForSale
	 */
	public CarInventory getCarsForSale() {
		return carsForSale;
	}
	/**
	 * @param carsForSale the carsForSale to set
	 */
	public void setCarsForSale(CarInventory carsForSale) {
		this.carsForSale = carsForSale;
	}
	/**
	 * @return the componentsForSale
	 */
	public ComponentInventory getComponentsForSale() {
		return componentsForSale;
	}
	/**
	 * @param componentsForSale the componentsForSale to set
	 */
	public void setComponentsForSale(ComponentInventory componentsForSale) {
		this.componentsForSale = componentsForSale;
	}
}
