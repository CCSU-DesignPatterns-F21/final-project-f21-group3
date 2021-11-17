/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.Driver;

/**
 * A state which the Driver moves into after reaching the end of a race.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="FinishedRace", key="_cls")
public class FinishedRace extends Completed {
	private final int position;
	private final String raceEventId;
	// When race is completed, use the race event ID to pull from the database to determine reward.
	
	/**
	 * Constructs a finished race state
	 * @param driver allows this state to set the state of the driver and get the Player to add credits.
	 * @param reward the event reward for first place that gets added to the players balance. 
	 * @param position the final pole position of the Driver in the race.
	 */
	public FinishedRace(Driver driver, String raceEventId) {
		super(driver);
		// TODO: call to database for position
		this.position = 0;
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
		// TODO: Obtain reward from race event in database.
		int reward = 10000;
		
		
		// Reward the player with credits
		int currentCredits = this.getDriver().getPlayer().getCredits();
		this.getDriver().getPlayer().setCredits(currentCredits + (reward/this.getPosition()));
		
		// Return the driver to a resting state
		this.getDriver().setState(new Resting());
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