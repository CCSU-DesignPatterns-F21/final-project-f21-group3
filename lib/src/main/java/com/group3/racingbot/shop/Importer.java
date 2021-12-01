package com.group3.racingbot.shop;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentType;
import com.group3.racingbot.gameservice.GameplayHandler;
import com.group3.racingbot.inventory.Inventory;

/**
 * Concrete class extending abstract class Shop. Sells high quality components.
 * @author Maciej Bregisz
 *
 */

public class Importer extends Shop  {
	
	@BsonCreator
	public Importer() {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(3);
		setName("Importer");
		setDescription("Foreign cars and parts. Exotic, Overengineered, Expensive");
	}
	
	public Importer(GameplayHandler gph) {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(3);
		setName("Importer");
		setDescription("Foreign cars and parts. Exotic, Overengineered, Expensive");
	}


	/**
	 * Generates new components and cars for sale using the abstract factory and builder.
	 */
	public void update() {
		getComponentsForSale().getItems().clear();
		System.out.println("Updating Importer Store");
		getComponentsForSale().add(getFactory().createComponent(ComponentType.ENGINE, ThreadLocalRandom.current().nextInt(751, 3500+1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.WHEELS, ThreadLocalRandom.current().nextInt(751, 3500+1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.SUSPENSION, ThreadLocalRandom.current().nextInt(751, 3500+1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.CHASSIS, ThreadLocalRandom.current().nextInt(751, 3500+1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.TRANSMISSION, ThreadLocalRandom.current().nextInt(751, 3500+1)));
		
		getCarsForSale().getItems().clear();
		CarBuilder cb = new CarBuilder();
		cb.addEngine(getFactory().createComponent(ComponentType.ENGINE, ThreadLocalRandom.current().nextInt(751, 3500+1)))
		.addWheels(getFactory().createComponent(ComponentType.WHEELS, ThreadLocalRandom.current().nextInt(751, 3500+1)))
		.addSuspension(getFactory().createComponent(ComponentType.SUSPENSION, ThreadLocalRandom.current().nextInt(751, 3500+1)))
		.addChassis(getFactory().createComponent(ComponentType.CHASSIS, ThreadLocalRandom.current().nextInt(751, 3500+1)))
		.addTransmission(getFactory().createComponent(ComponentType.TRANSMISSION, ThreadLocalRandom.current().nextInt(751, 3500+1)));
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
