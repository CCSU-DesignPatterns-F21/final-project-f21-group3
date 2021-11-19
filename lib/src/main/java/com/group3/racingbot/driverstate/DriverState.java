package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.shop.ChopShop;
import com.group3.racingbot.shop.Dealership;
import com.group3.racingbot.shop.Importer;
import com.group3.racingbot.shop.Junkyard;

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
	String raceStep(Driver driver);
	
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
	
	/**
	 * In the event that this driver is in this state when the server shuts down, this will grab all necessary data from the database in order to get back up and running.
	 * @return whether or not all missing objects were successfully obtained from the database.
	 */
	boolean refreshFromDB();
}