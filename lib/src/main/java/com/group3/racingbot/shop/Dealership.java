package com.group3.racingbot.shop;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * Concrete class extending abstract class Shop. Sells OEM quality components and cars.
 * @author Maciej Bregisz
 */
@BsonDiscriminator(value="Dealership", key="_cls")
public class Dealership extends Shop  {

	
	@BsonCreator
	public Dealership() {
		setCarsForSale(new CarInventory());
		setComponentsForSale(new ComponentInventory());
		setId(2);
		setName("Dealership");
		setDescription("Factory cars and components, just like the owners manual suggested. Only genuine factory parts!");
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		getComponentsForSale().getItems().clear();
		System.out.println("Updating Dealership Store");
		
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
