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
@BsonDiscriminator(value="DNF", key="_cls")
public class DNF extends Completed {
	/**
	 * Constructs a DNF state.
	 * @param driver allows this state to set the state of the driver
	 * @param reward the event reward for first place.
	 */
	public DNF(Driver driver, int reward) {
		super(driver, reward);
	}
	
	@Override
	public void collectReward() {
		this.getDriver().setState(new Resting());
	}
}
