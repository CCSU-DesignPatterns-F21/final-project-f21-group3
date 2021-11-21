/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;

/**
 * The state a driver moves into when their car gets totalled during a race.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="DNF", key="_cls")
public class DNF extends Completed {
	@BsonIgnore
	private RaceEvent raceEvent;
	private String raceEventId;
	
	/**
	 * Constructs a DNF state.
	 * @param driver allows this state to set the state of the driver
	 * @param raceEvent the event which the driver participated in.
	 */
	public DNF(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId) {
		super(playerId, driverId);
	}
	
	@Override
	public void collectReward() {
		this.refreshFromDB(); // Ensure that we have all objects that we'd need.
		
		DBHandler dbh = DBHandler.getInstance();
		Player updatedPlayer = super.getPlayer();
		Driver updatedDriver = super.getDriver();
		
		updatedDriver.setState(new Resting()); // Set driver to resting state
		updatedPlayer.setCredits(updatedPlayer.getCredits() + 50); // Pity money
		updatedPlayer.getOwnedDrivers().update(updatedDriver);
		dbh.updateUser(updatedPlayer);
	}
	
	/**
	 * @return the raceEventId
	 */
	public String getRaceEventId() {
		return raceEventId;
	}

	/**
	 * @param raceEventId the raceEventId to set
	 */
	public void setRaceEventId(String raceEventId) {
		this.raceEventId = raceEventId;
	}
	
	@Override
	public boolean refreshFromDB() {
		DBHandler dbh = DBHandler.getInstance();
		
		// Verify that the driver still exists.
		if (super.getDriver() == null) {
			// The server could have restarted and the instance of this class may only hold the id's
			// In this case, retrieve all necessary info from the database.
			super.setPlayer(dbh.getPlayer(super.getPlayerId()));
			try {
				if (super.getPlayer() != null) {
					super.setDriver(super.getPlayer().getOwnedDrivers().getById(super.getDriverId()));
				}
				else {
					System.out.println("Player " + super.getPlayerId() + " is missing from Database. Attempting to put Driver " + super.getPlayerId() + " into a resting state...");
				}
			}
			catch(NotFoundException e) {
				// Driver is missing from DB
				System.out.println("Driver " + super.getPlayerId() + " is missing from Database. Attempting to put Driver " + super.getPlayerId() + " into a resting state...");
			}
			
			if (super.getPlayer() == null || super.getDriver() == null) {
				if (dbh.removeDriverFromRaceEventInDB(super.getDriverId(), this.raceEventId)) {
					System.out.println("Driver " + super.getDriverId() + " has been removed from race event " + this.raceEventId + ". Setting driver state to resting...");
					if (dbh.updateDriverStateInDB(super.getPlayerId(), super.getDriverId(), new Resting())) {
						System.out.println("Driver " + super.getDriverId() + " is now in a resting state.");
					}
					else {
						System.out.println("Unable to put Driver " + super.getDriverId() + " into a resting state.");
					}
				}
				else {
					System.out.println("Race event " + this.raceEventId + " does not exist. No further actions taken.");
				}
				return false;
			}
		}
		
		if (this.raceEvent == null) {
			// Verify that the race event still exists.
			if (!dbh.raceEventExists(this.raceEventId)) {
				System.out.println("Race event " + this.raceEventId + " does not exist. Attempting to change the state of Driver " + super.getDriverId() + " to a resting state.");
				
				// Update driver state in DB
				if (dbh.updateDriverStateInDB(super.getPlayerId(), super.getDriverId(), new Resting())) {
					System.out.println("Driver " + super.getDriverId() + " is now in a resting state.");
				}
				else {
					System.out.println("Unable to change Driver " + super.getDriverId() + " to a resting state in the Database.");
				}
				
				// Database updated, now update locally.
				super.getDriver().setState(new Resting());
				return false;
			}
			else {
				// Get the race event
				this.raceEvent = dbh.getRaceEvent(this.raceEventId);
			}
		}
		return true;
	}
	
	@Override
	public String driverStatus(Driver driver) {
		return driver.getName() + " (" + driver.getId() + ") was unable to complete the race event " + this.raceEventId + ". You can now claim your participation reward.\n**Claim a reward**\n!r debug claim";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.getPlayerId().hashCode() + super.getDriverId().hashCode();
		result = prime * result + ((raceEvent == null) ? 0 : raceEvent.hashCode());
		result = prime * result + ((raceEventId == null) ? 0 : raceEventId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof DNF) {
			DNF otherObj = (DNF) other;
			
			if (!(this.getPlayerId().equals(otherObj.getPlayerId())))
				return false;
			if (!(this.getDriverId().equals(otherObj.getDriverId())))
				return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "DNF";
	}
}