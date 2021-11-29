package com.group3.racingbot.shop;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.Inventory;

/**
 * Concrete class extending abstract class Shop. Sells low quality components.
 * @author Maciej Bregisz
 */

public class Dealership extends Shop  {

	
	@BsonCreator
	public Dealership() {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(2);
		setName("Dealership");
		setDescription("Factory cars and components, just like the owners manual suggested. Only genuine factory parts!");
	}

	@Override
	public Component createComponent() {
		return null;
	}

	@Override
	public void update() {
		getComponentsForSale().getItems().clear();
		System.out.println("Updating Dealership Store");
		getComponentsForSale().add(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		
		CarBuilder cb = new CarBuilder();
		cb.addEngine(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addWheels(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addSuspension(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addChassis(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addTransmission(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getCarsForSale().add(cb.build());
		
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	/**
	 * Compare and determine whether or not the two objects are identical or the same object.
	 * @param obj the Object being compared to
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
