package com.group3.racingbot.driverstate;

import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.RaceTrack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A state which the driver stays in one place on the track until they recover.
 * @author Nick Sabia
 *
 */
public class Crashed extends Racing {
	
	public Crashed(Driver driver, Car car, RaceEvent raceEvent) {
		super(driver, car, raceEvent);
	}

	@Override
	public DriverState rollDriverState() {
		// TODO Auto-generated method stub
		int lowerBound = (int) Math.floor(this.getDriver().getRecovery()/5);
		int recoveryRoll = ThreadLocalRandom.current().nextInt(lowerBound, 100);
		if (recoveryRoll > 75) {
			this.getDriver().setState(new Normal(this.getDriver(), this.getCar(), this.getRaceEvent()));
		}
		return null;
	}

	@Override
	public void raceRoll(Driver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crash(Car car) {
		// TODO Auto-generated method stub
		// Do nothing
	}
}
