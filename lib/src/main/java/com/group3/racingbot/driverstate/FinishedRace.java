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
	 * 
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
}
