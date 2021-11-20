/**
 * 
 */
package com.group3.racingbot.driverstate;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.CornerNode;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.StraightNode;
import com.group3.racingbot.racetrack.TrackNode;

/**
 * A state where the driver is racing defensively. This negatively impacts how far the Driver travels, but also decreases chances of crashing.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="Defensive", key="_cls")
public class Defensive extends Racing {
	/**
	 * Construct a defensive racing state.
	 * @param playerId used to grab the player object from the DB. Allows for saving the state to the DB once edits to the state have been made.
	 * @param driverId used to grab the driver object from the DB. Allows this state to change the driver's state and helps calculate how far the Driver will travel per time unit.
	 * @param carId used to grab the car object from the DB. This is the driver's vessel for traversing the track.
	 * @param raceEventId used to grab the race event object from the DB.
	 */
	@BsonCreator
	public Defensive(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("carId") String carId, @BsonProperty("raceEventId") String raceEventId) {
		super(playerId, driverId, carId, raceEventId);
		super.setMultiplier(0.75);
	}

	@Override
	public DriverState rollDriverState() {
		super.refreshFromDB();
		
		int roll = ThreadLocalRandom.current().nextInt(0, 100);
		if (roll < (6 * this.getMultiplier())) {
			// Driver has crashed
			crash(this.getCar());
			if (this.getCar().getDurability() > 0) {
				return new Crashed(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId());
			}
			else {
				return new DNF(this.getDriver(), this.getRaceEvent());
			}
		}
		else if (roll < 60) {
			// Driver remains in the Defensive state.
			return this;
		}
		else if (roll < 80) {
			// Driver is now driving normally.
			return new Normal(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId());
		}
		else {
			// Driver is now driving aggressively.
			return new Aggressive(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId());
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(super.getMultiplier()) + super.getPlayerId().hashCode() + super.getDriverId().hashCode() + super.getCarId().hashCode() + super.getCarId().hashCode() + super.getRaceEventId().hashCode();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Defensive) {
			Defensive otherObj = (Defensive) other;
			
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
		return "Defensive";
	}
}