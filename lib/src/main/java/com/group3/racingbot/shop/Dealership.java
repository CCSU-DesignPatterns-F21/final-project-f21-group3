package com.group3.racingbot.shop;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * 
 * @author Maciej Bregisz
 *
 */

public class Dealership extends Shop  {

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
		System.out.println("Updating Dealership Store");
		
	}

}