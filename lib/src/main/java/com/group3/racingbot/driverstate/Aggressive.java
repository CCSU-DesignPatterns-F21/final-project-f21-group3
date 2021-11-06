package com.group3.racingbot.driverstate;

import java.util.concurrent.ThreadLocalRandom;

import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.CornerNode;
import com.group3.racingbot.racetrack.StraightNode;

/**
 * A state where the driver is racing aggressively. This grants a bonus to how far the Driver travels, but increases chances of crashing.
 * @author Nick Sabia
 *
 */
public class Aggressive extends Racing {
	private double multiplier;
	
	/**
	 * Construct an aggressive racing state.
	 * @param driver allows this state to change the driver's state and helps calculate how far the Driver will travel per time unit.
	 * @param car calculate how far the Driver will travel per time unit
	 * @param raceEvent carries event reward info into the completed stages
	 */
	public Aggressive(Driver driver, Car car, RaceEvent raceEvent) {
		super(driver, car, raceEvent);
		this.multiplier = 2.0;
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
	public DriverState rollDriverState() {
		// TODO Auto-generated method stub
		int roll = ThreadLocalRandom.current().nextInt(0, 100);
		if (roll < (6 * this.getMultiplier())) {
			crash(this.getCar());
			if (this.getCar().getDurability() > 0)
				return new Crashed(this.getDriver(), this.getCar(), this.getRaceEvent());
			else 
				return new DNF(this.getDriver(), this.getRaceEvent().getGrandPrize());
		}
		else if (roll < 60) {
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
			this.getRaceTrack().progressForward(distanceToCover);
		}
		return this;
	}

	@Override
	public void raceStep(Driver driver) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void crash(Car car) {
		final int CHASSIS = 0;
		final int ENGINE = 1;
		final int SUSPENSION = 2;
		final int TRANSMISSION = 3;
		final int WHEEL = 4;
		
		int amountOfComponentsToDamage = ThreadLocalRandom.current().nextInt(1, 3);
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
	public String toString() {
		return "Aggressive Racing State";
	}
}
