package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * 
 * @author Maciej Bregisz
 *
 */

public class Importer extends Shop  {
	
	@BsonCreator
	public Importer() {
		setCarsForSale(new CarInventory());
		setComponentsForSale(new ComponentInventory());
		setId(3);
		setName("Importer");
		setDescription("Foreign cars and parts. Exotic, Overengineered, Expensive");
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		System.out.println("Updating Importer Store");
		
	}

}
