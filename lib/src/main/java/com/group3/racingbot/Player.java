package com.group3.racingbot;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;
import com.group3.racingbot.inventory.DriverInventory;
import com.group3.racingbot.inventory.NotFoundException;

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
	@BsonProperty("activeDriverId")
	private String activeDriverId;
	@BsonProperty("activeCarId")
	private String activeCarId;
	
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
		Driver defaultDriver = new Driver("Stig");
		defaultDriver.setId(DBHandler.getInstance().generateId(6));
		getOwnedDrivers().add(defaultDriver);
		setActiveDriverId(getOwnedDrivers().getItems().get(0).getId());
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