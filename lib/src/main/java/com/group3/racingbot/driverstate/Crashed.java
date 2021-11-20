package com.group3.racingbot.driverstate;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.CornerNode;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.StraightNode;
import com.group3.racingbot.racetrack.TrackNode;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * A state which the driver stays in one place on the track until they recover.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="Crashed", key="_cls")
public class Crashed extends Racing {
	/**
	 * Construct a crashed racing state.
	 * @param playerId used to grab the player object from the DB. Allows for saving the state to the DB once edits to the state have been made.
	 * @param driverId used to grab the driver object from the DB. Allows this state to change the driver's state and helps calculate how far the Driver will travel per time unit.
	 * @param carId used to grab the car object from the DB. This is the driver's vessel for traversing the track.
	 * @param raceEventId used to grab the race event object from the DB.
	 * @param raceTrack what the driver will be racing on.
	 */
	@BsonCreator
	public Crashed(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("carId") String carId, @BsonProperty("raceEventId") String raceEventId, @BsonProperty("raceTrack") RaceTrack raceTrack) {
		super(playerId, driverId, carId, raceEventId, raceTrack);
		super.setMultiplier(0);
	}

	@Override
	public DriverState rollDriverState() {
		// TODO Auto-generated method stub
		int lowerBound = (int) Math.floor(this.getDriver().getRecovery()/5);
		int recoveryRoll = ThreadLocalRandom.current().nextInt(lowerBound, 100);
		if (recoveryRoll > 75) {
			return new Normal(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId(), super.getRaceTrack());
		}
		else {
			return this;
		}
	}

	@Override
	public void crash(Car car) {
		// Do nothing
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = super.getPlayerId().hashCode() + super.getDriverId().hashCode() + super.getCarId().hashCode() + super.getCarId().hashCode() + super.getRaceEventId().hashCode();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Crashed) {
			Crashed otherObj = (Crashed) other;
			
			if (!(this.getPlayerId().equals(otherObj.getPlayerId())))
				return false;
			if (!(this.getDriverId().equals(otherObj.getDriverId())))
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Crashed";
	}
}