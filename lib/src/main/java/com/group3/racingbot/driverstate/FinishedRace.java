/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;

/**
 * A state which the Driver moves into after reaching the end of a race.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="FinishedRace", key="_cls")
public class FinishedRace extends Completed {
	private RaceEvent raceEvent;
	private String raceEventId;
	private int position;
	// When race is completed, use the race event ID to pull from the database to determine reward.
	
	/**
	 * Constructs a finished race state
	 * @param driver allows this state to set the state of the driver and get the Player to add credits.
	 * @param reward the event reward for first place that gets added to the players balance. 
	 * @param position the final pole position of the Driver in the race.
	 */
	@BsonCreator
	public FinishedRace(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("raceEventId") String raceEventId) {
		super(playerId, driverId);
		this.position = 0;
		this.raceEvent = null;
		this.raceEventId = raceEventId;
	}
	
	/**
	 * @return the final pole position of the Driver in the race
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * @return the id of the race event which the driver completed.
	 */
	public String getRaceEventId() {
		return this.raceEventId;
	}

	@Override
	public void collectReward() {
		this.refreshFromDB();

		DBHandler dbh = DBHandler.getInstance();
		Player updatedPlayer = super.getPlayer();
		Driver updatedDriver = super.getDriver();
		int reward = this.raceEvent.getGrandPrize();
		
		try {
			this.position = this.raceEvent.getStandings().getDriverStandingById(super.getDriverId()).getPosition();
		}
		catch (NotFoundException e) {
			System.out.println("FinishedRace; collectReward method: Unable to find Driver " + super.getDriverId() + " within the standings of Race Event " + this.raceEventId + ". Cannot determine the position which the driver placed.");
			e.printStackTrace();
			return;
		}
		
		// Return the driver to a resting state
		updatedDriver.setState(new Resting());
		updatedPlayer.getOwnedDrivers().update(updatedDriver);
		
		// Reward the player with credits
		int currentCredits = updatedPlayer.getCredits();
		updatedPlayer.setCredits(currentCredits + (reward/this.position));
		dbh.updateUser(updatedPlayer);
	}
	
	@Override
	public boolean refreshFromDB() {
		DBHandler dbh = DBHandler.getInstance();
		
		// Verify that the driver still exists.
		// Sets both the driver and player objects if needed.
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
		
		// Sets the raceEvent object if needed.
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
		String positionPostfix = "th";
		if ((this.position % 10) == 1) {
			positionPostfix = "st";
		}
		else if ((this.position % 10) == 2) {
			positionPostfix = "nd";
		}
		else if ((this.position % 10) == 3) {
			positionPostfix = "rd";
		}
		return driver.getName() + "(" + driver.getId() + ") has completed the race event " + this.raceEventId + ", finishing in " + this.position + positionPostfix + " place. You can now claim your winnings. \nClaim a reward: !r debug driver reward";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.getPlayerId().hashCode() + super.getDriverId().hashCode();
		result = prime * result + ((raceEvent == null) ? 0 : raceEvent.hashCode());
		result = prime * result + ((raceEventId == null) ? 0 : raceEventId.hashCode());
		result *= position;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof FinishedRace) {
			FinishedRace otherObj = (FinishedRace) other;
			
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
		return "FinishedRace";
	}
	
}