package com.group3.racingbot.shop;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.Inventory;

/**
 * @author Maciej Bregisz
 *
 */
public class Junkyard extends Shop {

	
	@BsonCreator
	public Junkyard() {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(1);
		setName("Junkyard");
		setDescription("What you see is what you get, pal! Rusty parts and even rustier components, some hidden gems too!");
	}

	@Override
	public Component createComponent() {
		return null;
	}

	@Override
	public void update() {
		System.out.println("Updating Junkyard Store");
		getComponentsForSale().getItems().clear();
		getComponentsForSale().add(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		
		CarBuilder cb = new CarBuilder();
		cb.addEngine(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addWheels(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addSuspension(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addChassis(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addTransmission(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
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
