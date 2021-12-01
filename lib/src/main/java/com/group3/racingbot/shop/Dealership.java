package com.group3.racingbot.shop;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.gameservice.GameplayHandler;
import com.group3.racingbot.inventory.Inventory;

/**
 * Concrete class extending abstract class Shop. Sells low quality components.
 * @author Maciej Bregisz
 */

public class Dealership extends Shop  {

	
	/**
	 * ChopShop shop Constructor
	 */
	@BsonCreator
	public Dealership() {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(2);
		setName("Dealership");
		setDescription("Factory cars and components, just like the owners manual suggested. Only genuine factory parts!");
	}
	/**
	 * ChopShop store constructor with GameplayHandler parameter
	 * @param gh Reference to the GameplayHandler
	 */
	public Dealership(GameplayHandler gph) {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(2);
		setName("Dealership");
		setDescription("Factory cars and components, just like the owners manual suggested. Only genuine factory parts!");
		gph.subscribe(this);
	}

	/**
	 * Generates new components and cars for sale using the abstract factory and builder.
	 */
	public void update() {
		getComponentsForSale().getItems().clear();
		System.out.println("Updating Dealership Store");
		getComponentsForSale().add(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getComponentsForSale().add(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		
		getCarsForSale().getItems().clear();
		CarBuilder cb = new CarBuilder();
		cb.addEngine(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addWheels(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addSuspension(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addChassis(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(301, 750 + 1)))
		.addTransmission(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(301, 750 + 1)));
		getCarsForSale().add(cb.build());
		
		
	}

	/**
	 *  Calculates the Objects hash code
	 * @return int object hash code
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	/**
	 * Compare and determine whether or not the two objects are identical or the same object.
	 * @param obj the Object being compared to
	 * @return boolean whether or not objects are equal
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
