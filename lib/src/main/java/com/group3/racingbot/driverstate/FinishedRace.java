/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
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
	public FinishedRace(Driver driver, RaceEvent raceEvent) {
		super(driver);
		// TODO: call to database for position
		this.position = 0;
		this.raceEvent = raceEvent;
		this.raceEventId = raceEvent.getId();
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
		// TODO: Obtain reward from race event in database.
		int reward = 10000;
		
		
		// Reward the player with credits
		int currentCredits = this.getDriver().getPlayer().getCredits();
		this.getDriver().getPlayer().setCredits(currentCredits + (reward/this.getPosition()));
		
		// Return the driver to a resting state
		this.getDriver().setState(new Resting());
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof FinishedRace) {
			FinishedRace otherObj = (FinishedRace) other;
			
			if (this.getPosition() != otherObj.getPosition())
				return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "FinishedRace [position=" + position + "]";
	}
	
}