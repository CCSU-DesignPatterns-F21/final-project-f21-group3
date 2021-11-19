/**
 * 
 */
package com.group3.racingbot.driverstate;

import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.CornerNode;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.StraightNode;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

/**
 * A state where the Driver is racing as they normally would. A Driver may leave this state once they finish the race.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="Normal", key="_cls")
public class Normal extends Racing {
	private double multiplier;
	/**
	 * Construct a normal racing state.
	 * @param driver allows this state to change the driver's state and helps calculate how far the Driver will travel per time unit.
	 * @param car calculate how far the Driver will travel per time unit
	 * @param raceEvent carries event reward info into the completed stages
	 */
	public Normal (String playerId, String driverId, String carId, String raceEventId, RaceTrack raceTrack) {
		super(playerId, driverId, carId, raceEventId, raceTrack);
		this.multiplier = 1.0;
	}
	
	/**
	 * Retrieve a general purpose multiplier which governs how likely it is that the Driver crashes on each roll and how far the Driver travels.
	 * @return the multiplier
	 */
	public double getMultiplier() {
		return multiplier;
	}

	/**
	 * Set a general purpose multiplier which governs how likely it is that the Driver crashes on each roll and how far the Driver travels.
	 * @param multiplier the multiplier to set
	 */
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	@Override
	public void rollDriverState() {
		super.refreshFromDB();
		
		// TODO Auto-generated method stub
		int roll = ThreadLocalRandom.current().nextInt(0, 100);
		if (roll < (6 * this.getMultiplier())) {
			// Driver has crashed
			crash(this.getCar());
			if (this.getCar().getDurability() > 0) {
				this.getDriver().setState(new Crashed(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId(), super.getRaceTrack()));
			}
			else {
				this.getDriver().setState(new DNF(this.getDriver(), this.getRaceEvent()));
			}
		}
		else if (roll < 60) {
			// Driver remains in the Normal state.
			this.raceStep(this.getDriver());
		}
		else if (roll < 80) {
			// Driver is now driving defensively.
			this.getDriver().setState(new Defensive(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId(), super.getRaceTrack()));
			this.getDriver().getState().raceStep(this.getDriver());
		}
		else {
			// Driver is now driving aggressively.
			this.getDriver().setState(new Aggressive(super.getPlayerId(), super.getDriverId(), super.getCarId(), super.getRaceEventId(), super.getRaceTrack()));
			this.getDriver().getState().raceStep(this.getDriver());
		}
	}

	@Override
	public String raceStep(Driver driver) {
		int corneringDist = this.rollCornerDistance(this.getMultiplier());
		int straightDist = this.rollStraightDistance(this.getMultiplier());
		this.setCornerDistance(corneringDist);
		this.setStraightDistance(straightDist);
		int distanceToCover = 0;
		if (this.getCurrentNode() instanceof StraightNode) {
			distanceToCover = straightDist + (int) Math.floor(corneringDist/3);
		}
		else if (this.getCurrentNode() instanceof CornerNode) {
			distanceToCover = corneringDist + (int) Math.floor(straightDist/3);
		}
		this.getRaceTrack().progressForward(super.getDriver(), distanceToCover);
		super.setTotalDistanceTraveled(super.getTotalDistanceTraveled() + distanceToCover);
		return "Driver: " + driver.getName() + " | " + this.getRaceTrack().currentProgressToString() + " | Distance covered this turn: " + distanceToCover;
	}
	
	@Override
	public void crash(Car car) {
		final int CHASSIS = 0;
		final int ENGINE = 1;
		final int SUSPENSION = 2;
		final int TRANSMISSION = 3;
		final int WHEEL = 4;
		
		int amountOfComponentsToDamage = ThreadLocalRandom.current().nextInt(1, 4);
		for (int i = 0; i < amountOfComponentsToDamage; i++) {
			int componentToDamage = ThreadLocalRandom.current().nextInt(0, 4);
			int damage = ThreadLocalRandom.current().nextInt(0, 50);
			int currentDurability = 0;
			int newDurability = 0;
			switch (componentToDamage) {
				case CHASSIS:
					currentDurability = this.getCar().getChassis().getDurability();
					newDurability = currentDurability - damage;
					if (newDurability < 0)
						newDurability = 0;
					this.getCar().getChassis().setDurability(newDurability);
					break;
				case ENGINE:
					currentDurability = this.getCar().getEngine().getDurability();
					newDurability = currentDurability - damage;
					if (newDurability < 0)
						newDurability = 0;
					this.getCar().getEngine().setDurability(newDurability);
					break;
				case SUSPENSION:
					currentDurability = this.getCar().getSuspension().getDurability();
					newDurability = currentDurability - damage;
					if (newDurability < 0)
						newDurability = 0;
					this.getCar().getSuspension().setDurability(newDurability);
					break;
				case TRANSMISSION:
					currentDurability = this.getCar().getTransmission().getDurability();
					newDurability = currentDurability - damage;
					if (newDurability < 0)
						newDurability = 0;
					this.getCar().getTransmission().setDurability(newDurability);
					break;
				case WHEEL:
					currentDurability = this.getCar().getWheels().getDurability();
					newDurability = currentDurability - damage;
					if (newDurability < 0)
						newDurability = 0;
					this.getCar().getWheels().setDurability(newDurability);
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(multiplier);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Normal) {
			Normal otherObj = (Normal) other;
			
			if (this.getMultiplier() != otherObj.getMultiplier())
				return false;
			if (!(this.getDriver().equals(otherObj.getDriver())))
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Normal Racing State";
	}
}