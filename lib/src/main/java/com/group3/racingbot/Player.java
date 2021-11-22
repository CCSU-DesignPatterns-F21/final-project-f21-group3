package com.group3.racingbot;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;
import com.group3.racingbot.inventory.DriverInventory;

/**
 * Defines the Player class. Player class is the main record in the DB, the records get parsed into this class.
 * @author Maciej Bregisz
 *
 */

public class Player {
    @BsonProperty("_id")
	private String id;
	@BsonProperty("username")
	private String username;
	@BsonProperty("credits")
	private int credits = 2000;
	@BsonProperty("famepoints")
	private int famepoints = 0;
	@BsonProperty("totalWins")
	private int totalWins = 0;
	@BsonProperty("totalLosses")
	private int totalLosses = 0;
	@BsonProperty("lastWorked")
	private long lastWorked = 0;
	@BsonProperty("ownedComponents")
	private ComponentInventory ownedComponents;
	@BsonProperty("ownedCars")
	private CarInventory ownedCars;
	@BsonProperty("ownedDrivers")
	private DriverInventory ownedDrivers;
	@BsonProperty("activeDriver")
	private Driver activeDriver;
	@BsonProperty("activeCar")
	private Car activeCar;
	
	/**
	 * Player class constructor.
	 */
	@BsonCreator
	public Player() {
		this.id = "";
		this.username = "";
		setOwnedComponents(new ComponentInventory());
		setOwnedCars(new CarInventory());
		setOwnedDrivers(new DriverInventory());
		// Create a default driver.
		getOwnedDrivers().add(new Driver("Stig"));
		setActiveDriver(getOwnedDrivers().getItems().get(0));
	}
	
	/**
	 * 
	 * @return Player id
	 */
	
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * @return the famepoints
	 */
	public int getFamepoints() {
		return famepoints;
	}

	/**
	 * @param famepoints the famepoints to set
	 */
	public void setFamepoints(int famepoints) {
		this.famepoints = famepoints;
	}

	/**
	 * @return the totalWins
	 */
	public int getTotalWins() {
		return totalWins;
	}

	/**
	 * @param totalWins the totalWins to set
	 */
	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	/**
	 * @return the totalLosses
	 */
	public int getTotalLosses() {
		return totalLosses;
	}

	/**
	 * @param totalLosses the totalLosses to set
	 */
	public void setTotalLosses(int totalLosses) {
		this.totalLosses = totalLosses;
	}
	

	/**
	 * @return the lastWorked
	 */
	public long getLastWorked() {
		return lastWorked;
	}

	/**
	 * @param lastWorked the lastWorked to set
	 */
	public void setLastWorked(long lastWorked) {
		this.lastWorked = lastWorked;
	}
	
	/**
	 * @return the ownedCars
	 */
	public CarInventory getOwnedCars() {
		return ownedCars;
	}

	/**
	 * @param ownedCars the ownedCars to set
	 */
	public void setOwnedCars(CarInventory ownedCars) {
		this.ownedCars = ownedCars;
	}

	/**
	 * @return the ownedComponents
	 */
	public ComponentInventory getOwnedComponents() {
		return ownedComponents;
	}

	/**
	 * @param ownedComponents the ownedComponents to set
	 */
	public void setOwnedComponents(ComponentInventory ownedComponents) {
		this.ownedComponents = ownedComponents;
	}
	
	/**
	 * @return the ownedDrivers
	 */
	public DriverInventory getOwnedDrivers() {
		return ownedDrivers;
	}

	/**
	 * @param ownedDrivers the ownedDrivers to set
	 */
	public void setOwnedDrivers(DriverInventory ownedDrivers) {
		this.ownedDrivers = ownedDrivers;
	}

	/**
	 * Retrieve the driver which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @return the activeDriver
	 */
	public Driver getActiveDriver() {
		return activeDriver;
	}

	/**
	 * Set the driver which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @param activeDriver the activeDriver to set
	 */
	public void setActiveDriver(Driver activeDriver) {
		this.activeDriver = activeDriver;
	}

	/**
	 * Retrieve the car which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @return the activeCar
	 */
	public Car getActiveCar() {
		return activeCar;
	}

	/**
	 * Set the car which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @param activeCar the activeCar to set
	 */
	public void setActiveCar(Car activeCar) {
		this.activeCar = activeCar;
	}

	/**
	 * @return details about Player Object.
	 */
	@Override
	public String toString() {
		return "User: " + id + " Credits: " + credits +" Famepoints: " + " Wins: " + totalWins + " Losses: " + totalLosses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + credits;
		result = prime * result + famepoints;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + totalLosses;
		result = prime * result + totalWins;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Player) {
			Player otherObj = (Player) other;
			// Since the Player's ID is unique, we can use this to determine Player equality.
			if (!this.getId().equals(otherObj.getId()))
				return false;
			return true;
		}
		return false;
	}
	

	
}