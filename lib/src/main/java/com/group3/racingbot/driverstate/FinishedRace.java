/**
 * 
 */
package com.group3.racingbot.driverstate;

import com.group3.racingbot.Driver;

/**
 * A state which the Driver moves into after reaching the end of a race.
 * @author Nick Sabia
 *
 */
public class FinishedRace extends Completed {
	private final int position;
	
	/**
	 * Constructs a finished race state
	 * @param driver allows this state to set the state of the driver and get the Player to add credits.
	 * @param reward the event reward for first place that gets added to the players balance. 
	 * @param position the final pole position of the Driver in the race.
	 */
	public FinishedRace(Driver driver, int reward, int position) {
		super(driver, reward);
		this.position = position;
	}
	
	/**
	 * @return the final pole position of the Driver in the race
	 */
	public int getPosition() {
		return this.position;
	}

	@Override
	public void collectReward() {
		// Reward the player with credits
		int currentCredits = this.getDriver().getPlayer().getCredits();
		this.getDriver().getPlayer().setCredits(currentCredits + (this.getReward()/this.getPosition()));
		
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
