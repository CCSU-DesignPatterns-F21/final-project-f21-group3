package com.group3.racingbot.driverstate;

import com.group3.racingbot.Driver;

/**
 * Classes which implement this are considered states. A state for a Driver can offer bonuses or hindrances while racing, permanent skill improvements off the track, or simply resting/idling.
 * @author Nick Sabia
 *
 */
public interface DriverState {
	/**
	 * Retrieve the Driver which this state is attached to.
	 * @return Driver
	 */
	public Driver getDriver();
}
