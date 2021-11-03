package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * Concrete class extending abstract class Shop. Sells lowest quality components.
 * @author Maciej Bregisz
 *
 */

public class ChopShop extends Shop  {
	
	@BsonCreator
	public ChopShop() {
		setId(0);
		setCarsForSale(new CarInventory());
		setComponentsForSale(new ComponentInventory());
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
		getComponentsForSale().getItems().clear();
		getComponentsForSale().add(getFactory().createComponent("engine", 100));
		getComponentsForSale().add(getFactory().createComponent("wheel", 100));
		getComponentsForSale().add(getFactory().createComponent("suspension", 100));
		getComponentsForSale().add(getFactory().createComponent("chassis", 100));
		getComponentsForSale().add(getFactory().createComponent("transmission", 100));
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
