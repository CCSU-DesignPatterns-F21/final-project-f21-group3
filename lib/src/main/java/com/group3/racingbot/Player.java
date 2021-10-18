package com.group3.racingbot;

import java.util.Date;
import java.util.Objects;

import com.group3.racingbot.components.Component;
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
	private int credits;
	private int famepoints;
	private int totalWins;
	private int totalLosses;
	private Date lastWorked;
	private ComponentInventory ownedComponents;
	private CarInventory ownedCars;
	
	/**
	 * Player class constructor.
	 */
	public Player() {
		credits = 2000;
		famepoints = 0;
		totalLosses = 0;
		totalWins = 0;
	}
	
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
	public Date getLastWorked() {
		return lastWorked;
	}

	/**
	 * @param lastWorked the lastWorked to set
	 */
	public void setLastWorked(Date lastWorked) {
		this.lastWorked = lastWorked;
	}

	public String toString() {
		return "User: " + id + " Credits: " + credits +" Famepoints: " + " Wins: " + totalWins + " Losses: " + totalLosses;
	}
	
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
