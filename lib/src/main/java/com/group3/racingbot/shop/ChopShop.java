package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * 
 * @author Maciej Bregisz
 *
 */

public class ChopShop extends Shop  {
	@BsonCreator
	public ChopShop() {
		setCarsForSale(new CarInventory());
		setComponentsForSale(new ComponentInventory());
		setId(0);
		setName("Chop Shop");
		setDescription("You never know what you will find at the Chop Shop, stolen catalytic converters, wheels, rims and more! No questions asked, no refunds!");
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		System.out.println("Updating ChopShop Store");
		
	}

}
