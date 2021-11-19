/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;

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
	@BsonIgnore
	private Player player;
	private String playerId;
	@BsonIgnore
	private Driver driver;
	private String driverId;
	
	/**
	 * Constructs a completed state.
	 * @param driver allows this state to set the state of the driver
	 * @param reward the event reward for first place that gets added to the players balance. 
	 */
	public Completed(Driver driver) {
		this.player = driver.getPlayer();
		this.playerId = driver.getPlayer().getId();
		this.driver = driver;
		this.driverId = driver.getId();
		//this.raceEventId = raceEventId;
	}
	
	/**
	 * Retrieve the Player of the Driver who has completed an event.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the Player of the Driver who has completed an event.
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Retrieve the Player id of the Player who owns the driver.
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the Player id of the Player who owns the driver.
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * Retrieve the Driver id of the driver who completed an event.
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}

	/**
	 * Set the Driver id of the driver who completed an event.
	 * @param driverId the driverId to set
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
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
	public void signUpForRace(Driver driver, Car car, RaceEvent raceEvent) {
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
	
	@Override
	abstract public boolean refreshFromDB();
}