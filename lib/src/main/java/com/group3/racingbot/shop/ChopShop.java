package com.group3.racingbot.shop;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentType;
import com.group3.racingbot.gameservice.GameplayHandler;
import com.group3.racingbot.inventory.Inventory;

/**
 * Concrete class extending abstract class Shop. Sells lowest quality components.
 * @author Maciej Bregisz
 *
 */

public class ChopShop extends Shop  {
	
	
	/**
	 * ChopShop shop Constructor
	 */
	@BsonCreator
	public ChopShop() {
		setId(0);
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setName("Chop Shop");
		setDescription("You never know what you will find at the Chop Shop, stolen catalytic converters, wheels, rims and more! No questions asked, no refunds!");
	}
	
	/**
	 * ChopShop store constructor with GameplayHandler parameter
	 * @param gh Reference to the GameplayHandler
	 */
	public ChopShop(GameplayHandler gph) {
		setId(0);
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setName("Chop Shop");
		setDescription("You never know what you will find at the Chop Shop, stolen catalytic converters, wheels, rims and more! No questions asked, no refunds!");
		gph.subscribe(this);
	}

	
	
	/**
	 * Generates new components and cars for sale using the abstract factory and builder.
	 */
	public void update() {
		System.out.println("Updating ChopShop Store");
		getComponentsForSale().getItems().clear();
		getComponentsForSale().add(getFactory().createComponent(ComponentType.ENGINE, ThreadLocalRandom.current().nextInt(50, 150 + 1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.WHEELS, ThreadLocalRandom.current().nextInt(50, 150 + 1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.SUSPENSION, ThreadLocalRandom.current().nextInt(50, 150 + 1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.CHASSIS, ThreadLocalRandom.current().nextInt(50, 150 + 1)));
		getComponentsForSale().add(getFactory().createComponent(ComponentType.TRANSMISSION, ThreadLocalRandom.current().nextInt(50, 150 + 1)));
		
		
		getCarsForSale().getItems().clear();
		CarBuilder cb = new CarBuilder();
		cb.addChassis(getFactory().createComponent(ComponentType.CHASSIS, ThreadLocalRandom.current().nextInt(50, 150 + 1)))
		.addEngine(getFactory().createComponent(ComponentType.ENGINE, ThreadLocalRandom.current().nextInt(50, 150 + 1)))
		.addSuspension(getFactory().createComponent(ComponentType.SUSPENSION, ThreadLocalRandom.current().nextInt(50, 150 + 1)))
		.addWheels(getFactory().createComponent(ComponentType.WHEELS, ThreadLocalRandom.current().nextInt(50, 150 + 1)))
		.addTransmission(getFactory().createComponent(ComponentType.TRANSMISSION, ThreadLocalRandom.current().nextInt(50, 150 + 1)));
		getCarsForSale().add(cb.build());
	}

	/**
	 * Calculates the Objects hash code
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
