/**
 * 
 */
package com.group3.racingbot.driverstate;

import java.util.Date;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.standings.DriverStanding;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

/**
 * A state where the Driver is idle. A Driver may leave this state once their cooldown has expired.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="Resting", key="_cls")
public class Resting implements DriverState {

	@Override
	public String rest(Driver driver) {
		return driver.getName() + "(" + driver.getId() + ") was already resting.";
	}

	@Override
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// Check cooldown, if ok then train. Otherwise, remain resting. 
		//Date d = new Date();
		//long now = d.getTime();
		//final long halfHour = 1800;
		
		//if (now > driver.getCooldown()) {
			driver.setState(new Training(driver.getPlayer().getId(), driver.getId(), skillToTrain, intensity));
			/*switch (intensity) {
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
			}*/
		//}
	}

	@Override
	public void signUpForRace(Driver driver, Car car, RaceEvent raceEvent) {
		DriverState racePendingState = new RacePending(driver.getPlayer().getId(), driver.getId(), car.getId(), raceEvent.getId());
		driver.setState(racePendingState);
	}
	
	@Override
	public void beginRace(Driver driver) {
		// If in RacePending state and all fields are not null, then race!
		// Do nothing
	}

	@Override
	public String collectReward() {
		// If in completed state, execute this and go to resting state. Otherwise, do nothing.
		// Do nothing
		return "";
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// If in RacePending state, execute this, go to resting state, and return true. Otherwise do nothing and return false.
		return false;
	}

	@Override
	public DriverStanding raceStep(Driver driver, DriverStanding driverStanding) {
		// If in Racing state, calculate the distance which the driver can travel on straights and corners. 
		// Next, randomize if a crash will occur this roll. If so, add to the idleTime and damage the Car. Otherwise, continue.
		// Finally, if the Driver's on a straight node then travel the straight distance + Math.floor(cornerDistance/3). Otherwise, vice versa.
		
		// Do nothing
		return null;
	}

	@Override
	public void completedRace(Driver driver) {
		// If in the Racing state, move to FinishedRace state.
		// Do nothing
	}

	@Override
	public void completedTraining(Driver driver) {
		// If in the Training state, move to FinishedTraining state.
		// Do nothing
	}
	
	@Override
	public String toString() {
		return "Resting";
	}

	@Override
	public boolean refreshFromDB() {
		// If the server shuts down and boots back up, this function will grab all the missing objects from the database and insert them into the state as needed.
		// Do nothing
		return true;
	}
	
	@Override
	public String driverStatus(Driver driver) {
		String trainingSyntax = "!r debug driver train (awareness | cornering | composure | drafting | straights | recovery) (light | medium | intense)\n!r debug driver train (a | cor | com | d | s | r) (l | m | i)";
		String registerSyntax = "!r debug event register";
		return driver.getName() + " (" + driver.getId() + ") is currently resting. You can train " + driver.getName() + " or enter them into a race.\n**Train**\n" + trainingSyntax + "\n**Register for a race**\n" + registerSyntax;
	}
}