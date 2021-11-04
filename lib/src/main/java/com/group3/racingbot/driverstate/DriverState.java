package com.group3.racingbot.driverstate;

import com.group3.racingbot.Car;
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
	 * The driver begins the race they've signed up for. 
	 */
	void beginRace();
	
	/**
	 * Puts the Driver into a training state to improve a skill.
	 * @param driver
	 * @param skillToTrain
	 * @param intensity
	 */
	void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity);
	
	/**
	 * Registers this driver and their car for a racing event.
	 * @param driver
	 * @param car
	 * @param raceEvent
	 */
	void signUpForRace(Driver driver, Car car, RaceEvent raceEvent);
	
	/**
	 * Upon completion of training or a race, collect your reward (whether it's stat points or credits).
	 */
	void collectReward();
	
	/**
	 * Lets the Driver withdraw from a race if they are in a race pending state.
	 * @param driver
	 * @return success or failure of race withdrawl.
	 */
	boolean withdrawFromRace(Driver driver);
	
	/**
	 * Allow the Driver to perform their turn to move on the track during a race.
	 * @param driver
	 */
	void raceRoll(Driver driver);
	
	/**
	 * Move to the finished race state upon race completion.
	 * @param driver
	 */
	void completedRace(Driver driver, RaceEvent raceEvent);
	
	/**
	 * Move to the finished training state upon training completion.
	 * @param driver
	 */
	void completedTraining(Driver driver);
}