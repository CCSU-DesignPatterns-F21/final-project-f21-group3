package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.Inventory;

/**
 * 
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

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		getComponentsForSale().getItems().clear();
		System.out.println("Updating Importer Store");
		
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
	//TODO: add a toString with just a quick description

}
