package com.group3.racingbot;

import java.util.Objects;

import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.ComponentInventory;

/**
 * Defines the Player class. Player class is the main record in the DB, the records get parsed into this class.
 * @author Maciej Bregisz
 *
 */

public class Player {
	
	private String id;
	private String username;
	private int credits = 2000;
	private int famepoints = 0;
	private int totalWins = 0;
	private int totalLosses = 0;
	private long lastWorked = 0;
	private ComponentInventory ownedComponents;
	private CarInventory ownedCars;
	
	/**
	 * Player class constructor.
	 */
	public Player() {
	
		setOwnedComponents(new ComponentInventory());
		setOwnedCars(new CarInventory());
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
	
	public String toString() {
		return "User: " + id + " Credits: " + credits +" Famepoints: " + " Wins: " + totalWins + " Losses: " + totalLosses;
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
	 * @return details about Player Object.
	 */
	@Override
	public String toString() {
		return "User: " + id + " Credits: " + credits +" Famepoints: " + " Wins: " + totalWins + " Losses: " + totalLosses;
	}
	
	/**
	 * @return the hashCode value of the Player Object
	 */
	@Override
    public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + credits;
		result = prime * result + famepoints;
		result = prime * result + totalWins;
		result = prime * result + totalLosses;
		return result;
    }

	/**
	 * @return returns whether or not a two objects of type Player are actually the same object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return credits == other.credits && famepoints == other.famepoints && Objects.equals(id, other.id)
				&& totalLosses == other.totalLosses && totalWins == other.totalWins
				&& Objects.equals(username, other.username);
	}

	

	
	
	
}
