package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentType;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		System.out.println("Updating Junkyard Store");
		getComponentsForSale().getItems().clear();
		getComponentsForSale().add(getFactory().createComponent(ComponentType.ENGINE, 100));
	}
}
