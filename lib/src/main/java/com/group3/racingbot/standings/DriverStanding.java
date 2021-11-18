/**
 * 
 */
package com.group3.racingbot.standings;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.inventory.NotFoundException;

/**
 * Keep track of a driver's position within a race event.
 * @author Nick Sabia
 */
public class DriverStanding {
	@BsonIgnore
	private final Driver driver;
	private final String playerId;
	private final String driverId;
	private int position;
	private int distanceTraveled;
	private int timeCompleted;
	
	@BsonCreator
	public DriverStanding(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId) {
		// Obtain driver from DB
		DBHandler dbh = DBHandler.getInstance();
		Player p = dbh.getPlayer(playerId);
		Driver driver = null;
		try {
			driver = p.getOwnedDrivers().getById(driverId);
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}
		this.driver = driver;
		this.driverId = driverId;
		this.playerId = playerId;
		this.position = 1;
		this.distanceTraveled = 0;
		this.timeCompleted = 0;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the distanceTravelled
	 */
	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * @param distanceTravelled the distanceTravelled to set
	 */
	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	/**
	 * @return the timeCompleted
	 */
	public int getTimeCompleted() {
		return timeCompleted;
	}

	/**
	 * @param timeCompleted the timeCompleted to set
	 */
	public void setTimeCompleted(int timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}
	
	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}
	
}
