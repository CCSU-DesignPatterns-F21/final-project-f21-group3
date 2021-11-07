package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * Concrete class extending abstract class Shop. Sells Junkyard quality components and cars.
 * @author Maciej Bregisz
 *
 */
@BsonDiscriminator(value="Junkyard", key="_cls")
public class Junkyard extends Shop {

	@BsonCreator
	public Junkyard() {
		setCarsForSale(new CarInventory());
		setComponentsForSale(new ComponentInventory());
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
		getComponentsForSale().add(getFactory().createComponent("engine", 100));
	}
	

}
