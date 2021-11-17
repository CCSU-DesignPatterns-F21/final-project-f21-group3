/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;

/**
 * A state where the driver completes something, whether that's an event or a training session.
 * @author Nick Sabia
 *
 */
//@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = DNF.class),
//        @JsonSubTypes.Type(value = FinishedRace.class),
//		@JsonSubTypes.Type(value = FinishedTraining.class)})
//@BsonDiscriminator(value="Completed", key="_cls")
public abstract class Completed implements DriverState{
	private Driver driver;
	
	/**
	 * Constructs a completed state.
	 * @param driver allows this state to set the state of the driver
	 * @param reward the event reward for first place that gets added to the players balance. 
	 */
	public Completed(Driver driver) {
		this.driver = driver;
		//this.raceEventId = raceEventId;
	}

	/**
	 * Retrieve the Driver which has completed something.
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the Driver which has completed something.
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@Override
	public void rest() {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void signUpForRace(Driver driver, Car car, String raceEventId) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void beginRace() {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String raceStep(Driver driver) {
		// TODO Auto-generated method stub
		// Do nothing
		return "Finished";
	}

	@Override
	public void completedRace(Driver driver) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void completedTraining(Driver driver) {
		// TODO Auto-generated method stub
		// Do nothing
	}
}