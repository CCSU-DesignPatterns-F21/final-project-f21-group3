/**
 * 
 */
package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.Driver;

/**
 * The state a driver moves into when their car gets totalled during a race.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="DNF", key="_cls")
public class DNF extends Completed {
	
	private String raceEventId;
	
	/**
	 * Constructs a DNF state.
	 * @param driver allows this state to set the state of the driver
	 * @param reward the event reward for first place.
	 */
	public DNF(Driver driver, String raceEventId) {
		super(driver);
		this.raceEventId = raceEventId;
	}
	
	@Override
	public void collectReward() {
		this.getDriver().setState(new Resting());
	}
	
	/**
	 * @return the raceEventId
	 */
	public String getRaceEventId() {
		return raceEventId;
	}

	/**
	 * @param raceEventId the raceEventId to set
	 */
	public void setRaceEventId(String raceEventId) {
		this.raceEventId = raceEventId;
	}
}