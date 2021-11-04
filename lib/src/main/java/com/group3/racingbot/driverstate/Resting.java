/**
 * 
 */
package com.group3.racingbot.driverstate;

import java.util.Date;

import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;

/**
 * An idle state for a Driver. A Driver may leave this state once their cooldown has expired.
 * @author Nick Sabia
 *
 */
public class Resting implements DriverState {
	/**
	 * Set the Driver's state to a resting state.
	 * @param driver
	 */
	public Resting() {
		
	}

	@Override
	public void rest() {
		// do nothing
	}

	@Override
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// TODO Check cooldown, if ok then train. Otherwise, remain resting. 
		Date d = new Date();
		long now = d.getTime();
		final long halfHour = 1800;
		
		if (now > driver.getCooldown()) {
			driver.setState(new Training(driver, skillToTrain, intensity));
			switch (intensity) {
				case LIGHT:
					driver.setCooldown(now + halfHour);
					break;
				case MEDIUM:
					driver.setCooldown(now + (halfHour*2));
					break;
				case INTENSE:
					driver.setCooldown(now + (halfHour*3));
					break;
				default:
					// Do nothing
					break;
			}
		}
	}

	@Override
	public void signUpForRace(Driver driver, RaceEvent raceEvent) {
		// TODO Check cooldown, if ok then sign up for race. Otherwise, remain resting. 
		Date d = new Date();
		long now = d.getTime();
		
		if (now > driver.getCooldown()) {
			driver.setState(new RacePending(driver, raceEvent));
		}
	}

	@Override
	public void collectReward(Driver driver) {
		// TODO If in completed state, execute this and go to resting state. Otherwise, do nothing.
		// Do nothing
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// TODO If in RacePending state, execute this, go to resting state, and return true. Otherwise do nothing and return false.
		return false;
	}

	@Override
	public void raceRoll(Driver driver) {
		// TODO If in Racing state, calculate the distance which the driver can travel on straights and corners. 
		//      Next, randomize if a crash will occur this roll. If so, add to the idleTime and damage the Car. Otherwise, continue.
		//      Finally, if the Driver's on a straight node then travel the straight distance + Math.floor(cornerDistance/3). Otherwise, vice versa.
		
		// Do nothing
	}

	@Override
	public void completedRace(Driver driver) {
		// TODO If in the Racing state, move to FinishedRace state.
		// Do nothing
	}

	@Override
	public void completedTraining(Driver driver) {
		// TODO If in the Training state, move to FinishedTraining state.
		// Do nothing
	}
	
	@Override
	public String toString() {
		return "Resting";
	}
}
