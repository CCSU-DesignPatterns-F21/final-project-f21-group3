package com.group3.racingbot.driverstate;

import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.standings.DriverStanding;

/**
 * Classes which implement this are considered states. A state for a Driver can offer bonuses or hindrances while racing, permanent skill improvements off the track, or simply resting/idling.
 * @author Nick Sabia
 *
 */
public interface DriverState {
	/**
	 * Puts the Driver into a Resting state.
	 * @param driver the driver to switch into a resting state.
	 * @return context for what state the user may have left.
	 */
	String rest(Driver driver);
	
	/**
	 * The driver begins the race they've signed up for.
	 * @param driver the driver to participate in the race.
	 */
	String beginRace(Driver driver);
	
	/**
	 * Puts the Driver into a training state to improve a skill.
	 * @param driver the driver to train
	 * @param skillToTrain the skill of the driver to improve
	 * @param intensity governs how long the training session will last and how large the training reward will be
	 * @return String containing contextual info about beginning training.
	 */
	String beginTraining(Driver driver, Skill skillToTrain, Intensity intensity);
	
	/**
	 * Registers this driver and their car for a racing event.
	 * @param driver the driver to sign up for the race
	 * @param car the car which the driver will use in the race
	 * @param raceEvent the event which the driver is signed up for
	 * @return String containing contextual info about signing up for a race.
	 */
	String signUpForRace(Driver driver, Car car, RaceEvent raceEvent);
	
	/**
	 * Upon completion of training or a race, collect your reward (whether it's stat points or credits).
	 */
	String collectReward();
	
	/**
	 * Allow the Driver to perform their turn to move on the track during a race.
	 * @param driver the driver to advance forward in the race.
	 * @param driverStanding the driver standing, containing info about how they stack up compared to other racers in the event.
	 * @return the updated driver standing.
	 */
	DriverStanding raceStep(Driver driver, DriverStanding driverStanding);
	
	/**
	 * Move to the finished race state upon race completion.
	 * @param driver the driver which reached the end of the race track
	 * @return String containing contextual info about completing a race.
	 */
	String completedRace(Driver driver);
	
	/**
	 * Move to the finished training state upon training completion.
	 * @param driver the driver to switch into a completed state.
	 * @return String indicating that the user can now claim a reward.
	 */
	String completedTraining(Driver driver);
	
	/**
	 * Gives helpful information about the current state of the driver.
	 * @return a contextual string which offers helpful information about a particular driver.
	 */
	public String driverStatus(Driver driver);
}