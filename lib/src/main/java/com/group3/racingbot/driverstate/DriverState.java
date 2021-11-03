package com.group3.racingbot.driverstate;

import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;

/**
 * Classes which implement this are considered states. A state for a Driver can offer bonuses or hindrances while racing, permanent skill improvements off the track, or simply resting/idling.
 * @author Nick Sabia
 *
 */
public interface DriverState {
	/**
	 * Puts the Driver into a Resting state.
	 */
	void rest();
	
	/**
	 * Puts the Driver into a training state to improve a skill.
	 * @param driver
	 * @param skillToTrain
	 * @param intensity
	 */
	void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity);
	
	/**
	 * Registers this driver for a racing event.
	 * @param driver
	 * @param raceEvent
	 */
	void signUpForRace(Driver driver, RaceEvent raceEvent);
	
	/**
	 * Upon completion of training or a race, collect your reward (whether it's stat points or credits).
	 * @param driver
	 */
	void collectReward(Driver driver);
	
	/**
	 * Lets the Driver withdraw from a race if they are in a race pending state.
	 * @param driver
	 * @return success or failure of race withdrawl.
	 */
	boolean withdrawFromRace(Driver driver);
	
	/**
	 * Allow the Driver to perform a move on the track in a race.
	 * @param driver
	 */
	void raceRoll(Driver driver);
	
	/**
	 * Move to the finished race state upon race completion.
	 * @param driver
	 */
	void completedRace(Driver driver);
	
	/**
	 * Move to the finished training state upon training completion.
	 * @param driver
	 */
	void completedTraining(Driver driver);
}
