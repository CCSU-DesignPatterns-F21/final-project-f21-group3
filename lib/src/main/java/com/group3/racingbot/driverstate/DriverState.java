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
//@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Resting.class),
//        @JsonSubTypes.Type(value = Training.class),
//        @JsonSubTypes.Type(value = Racing.class),
//        @JsonSubTypes.Type(value = RacePending.class),
//		@JsonSubTypes.Type(value = Completed.class)})
//@BsonDiscriminator(value="DriverState", key="_cls")
public interface DriverState {
	/**
	 * Puts the Driver into a Resting state.
	 * @param driver the driver to switch into a resting state.
	 * @return context for what state the user may have left.
	 */
	String rest(Driver driver);
	
	/**
	 * The driver begins the race they've signed up for.
	 * @param driver
	 */
	String beginRace(Driver driver);
	
	/**
	 * Puts the Driver into a training state to improve a skill.
	 * @param driver
	 * @param skillToTrain
	 * @param intensity
	 * @return String containing contextual info about beginning training.
	 */
	String beginTraining(Driver driver, Skill skillToTrain, Intensity intensity);
	
	/**
	 * Registers this driver and their car for a racing event.
	 * @param driver
	 * @param car
	 * @param raceEvent
	 * @return String containing contextual info about signing up for a race.
	 */
	String signUpForRace(Driver driver, Car car, RaceEvent raceEvent);
	
	/**
	 * Upon completion of training or a race, collect your reward (whether it's stat points or credits).
	 */
	String collectReward();
	
	/**
	 * Allow the Driver to perform their turn to move on the track during a race.
	 * @param driver
	 * @param driverStanding
	 * @return the updated driver standing.
	 */
	DriverStanding raceStep(Driver driver, DriverStanding driverStanding);
	
	/**
	 * Move to the finished race state upon race completion.
	 * @param driver
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