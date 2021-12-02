package com.group3.racingbot;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.inventory.Inventory;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.inventory.Unique;

/**
 * Defines the Player class. Player class is the main record in the DB, the records get parsed into this class.
 * @author Maciej Bregisz
 *
 */
public class Player implements Unique {
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
	private Inventory<Component> ownedComponents;
	@BsonProperty("ownedCars")
	private Inventory<Car> ownedCars;
	@BsonProperty("ownedDrivers")
	private Inventory<Driver> ownedDrivers;
	@BsonProperty("activeDriverId")
	private String activeDriverId;
	@BsonProperty("activeCarId")
	private String activeCarId;
	
	/**
	 * Player class constructor. Creates all inventories and a default driver for the player to use.
	 * Note: Player does not start with an id, or a username. This must be set after it's created in order for it to persist in the database.
	 */
	@BsonCreator
	public Player() {
		this.id = "";
		this.username = "";
		setOwnedComponents(new Inventory<Component>());
		setOwnedCars(new Inventory<Car>());
		setOwnedDrivers(new Inventory<Driver>());
		// Create a default driver.
		Driver defaultDriver = new Driver("Stig");
		defaultDriver.setId(DBHandler.getInstance().generateId(6));
		getOwnedDrivers().add(defaultDriver);
		setActiveDriverId(getOwnedDrivers().getItems().get(0).getId());
		//this.filters = new ArrayList<InventoryIterator<? extends InventoryIteratorDecorator<?>>>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieve the player's discord username
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the player's discord username
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieve the player's total credits
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Set the player's total credits
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * Retrieve the player's fame points.
	 * @return the famepoints
	 */
	public int getFamepoints() {
		return famepoints;
	}

	/**
	 * Set the player's fame points.
	 * @param famepoints the famepoints to set
	 */
	public void setFamepoints(int famepoints) {
		this.famepoints = famepoints;
	}

	/**
	 * Retrieve the player's total wins
	 * @return the totalWins
	 */
	public int getTotalWins() {
		return totalWins;
	}

	/**
	 * Set the player's total wins
	 * @param totalWins the totalWins to set
	 */
	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	/**
	 * Retrieve the player's total losses
	 * @return the totalLosses
	 */
	public int getTotalLosses() {
		return totalLosses;
	}

	/**
	 * Set the player's total losses
	 * @param totalLosses the totalLosses to set
	 */
	public void setTotalLosses(int totalLosses) {
		this.totalLosses = totalLosses;
	}
	

	/**
	 * Retrieve the timestamp of when the player last called the work command.
	 * @return the lastWorked
	 */
	public long getLastWorked() {
		return lastWorked;
	}

	/**
	 * Set the timestamp of when the player last called the work command.
	 * @param lastWorked the lastWorked to set
	 */
	public void setLastWorked(long lastWorked) {
		this.lastWorked = lastWorked;
	}
	
	/**
	 * Retrieve the inventory of cars which the player owns.
	 * @return the ownedCars
	 */
	public Inventory<Car> getOwnedCars() {
		return ownedCars;
	}

	/**
	 * Set the inventory of cars which the player owns.
	 * @param ownedCars the ownedCars to set
	 */
	public void setOwnedCars(Inventory<Car> ownedCars) {
		this.ownedCars = ownedCars;
	}

	/**
	 * Retrieve the inventory of components which the player owns.
	 * @return the ownedComponents
	 */
	public Inventory<Component> getOwnedComponents() {
		return ownedComponents;
	}

	/**
	 * Set the inventory of components which the player owns.
	 * @param ownedComponents the ownedComponents to set
	 */
	public void setOwnedComponents(Inventory<Component> ownedComponents) {
		this.ownedComponents = ownedComponents;
	}
	
	/**
	 * Retrieve the inventory of drivers which the player owns.
	 * @return the ownedDrivers
	 */
	public Inventory<Driver> getOwnedDrivers() {
		return ownedDrivers;
	}

	/**
	 * Set the inventory of components which the player owns.
	 * @param ownedDrivers the ownedDrivers to set
	 */
	public void setOwnedDrivers(Inventory<Driver> ownedDrivers) {
		this.ownedDrivers = ownedDrivers;
	}

	/**
	 * Retrieve the driver which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @return the activeDriverId
	 */
	public String getActiveDriverId() {
		return activeDriverId;
	}

	/**
	 * Set the driver which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @param activeDriverId the activeDriverId to set
	 */
	public void setActiveDriverId(String activeDriverId) {
		this.activeDriverId = activeDriverId;
	}
	
	/**
	 * Retrieve the active driver from the driver inventory. If the driver cannot be found, return null.
	 * @return active driver if successful, null otherwise.
	 */
	public Driver obtainActiveDriver() {
		try {
			Driver activeDriver = this.getOwnedDrivers().getById(this.getActiveDriverId());
			System.out.println("Active driver " + activeDriver.getId() +  " found in inventory.");
			return activeDriver;
		}
		catch(NotFoundException e) {
			System.out.println("Active driver not found in inventory. Was the driver deleted while they were still active?");
			return null;
		}
	}
	
	/**
	 * Retrieve the active car from the car inventory. If the car cannot be found, return null.
	 * @return active car if successful, null otherwise.
	 */
	public Car obtainActiveCar() {
		try {
			Car activeCar = this.getOwnedCars().getById(this.getActiveCarId());
			return activeCar;
		}
		catch(NotFoundException e) {
			System.out.println("Active car not found in inventory. Was the car deleted while it was still active?");
			return null;
		}
	}

	/**
	 * Retrieve the car which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @return the activeCarId
	 */
	public String getActiveCarId() {
		return activeCarId;
	}

	/**
	 * Set the car which the player is currently using. 
	 * 
	 * This is what will be used in a race if the player decides to race.
	 * @param activeCarId the activeCar to set
	 */
	public void setActiveCarId(String activeCarId) {
		this.activeCarId = activeCarId;
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