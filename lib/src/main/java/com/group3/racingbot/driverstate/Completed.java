/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.standings.DriverStanding;

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
	@BsonCreator
	public Completed(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId) {
		this.player = null;
		this.playerId = playerId;
		this.driver = null;
		this.driverId = driverId;
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
	public String rest(Driver driver) {
		return driver.getName() + "(" + driver.getId() + ") still has rewards to claim. May not rest until the reward has been claimed.\n**Claim a Reward**\n!r debug claim";
	}

	@Override
	public String beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		return this.driverStatus(driver);
	}

	@Override
	public String signUpForRace(Driver driver, Car car, RaceEvent raceEvent) {
		return this.driverStatus(driver);
	}

	@Override
	public String beginRace(Driver driver) {
		return this.driverStatus(driver);
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DriverStanding raceStep(Driver driver, DriverStanding driverStanding) {
		// TODO Auto-generated method stub
		// Do nothing
		return null;
	}

	@Override
	public String completedRace(Driver driver) {
		// TODO Auto-generated method stub
		return driverStatus(driver);
	}

	@Override
	public String completedTraining(Driver driver) {
		// If in the Training state, move to FinishedTraining state.
		return driverStatus(driver);
	}
	
	@Override
	abstract public String collectReward();
	
	@Override
	abstract public boolean refreshFromDB();
	
	@Override
	abstract public String driverStatus(Driver driver);
}