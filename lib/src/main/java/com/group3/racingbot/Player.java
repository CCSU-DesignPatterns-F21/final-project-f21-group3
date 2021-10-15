package com.group3.racingbot;

import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

public class Player {
	
	private String id;
	private String username;
	private int credits;
	private int famepoints;
	private int totalWins;
	private int totalLosses;
	private ComponentInventory componentInventory;
	private CarInventory carInventory;
	
	
	public Player(String i, String u) {
		id = i;
		username = u;
	}
	
	
	//Getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getFamepoints() {
		return famepoints;
	}

	public void setFamepoints(int famepoints) {
		this.famepoints = famepoints;
	}

	public int getTotalWins() {
		return totalWins;
	}

	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	public int getTotalLosses() {
		return totalLosses;
	}

	public void setTotalLosses(int totalLosses) {
		this.totalLosses = totalLosses;
	}

	public ComponentInventory getComponentInventory() {
		return componentInventory;
	}

	public void setComponentInventory(ComponentInventory componentInventory) {
		this.componentInventory = componentInventory;
	}

	public CarInventory getCarInventory() {
		return carInventory;
	}

	public void setCarInventory(CarInventory carInventory) {
		this.carInventory = carInventory;
	}

	

	

}
