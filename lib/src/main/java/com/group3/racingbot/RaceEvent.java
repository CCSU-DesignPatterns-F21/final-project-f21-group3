package com.group3.racingbot;

import com.group3.racingbot.driverstate.Racing;
import com.group3.racingbot.inventory.DriverInventory;
import com.group3.racingbot.inventory.InventoryIterator;
import com.group3.racingbot.racetrack.RaceTrack;

/**
 * An racing event which a driver may participate in
 * @author Nick Sabia
 *
 */
public class RaceEvent {
	private String eventName;
	private RaceTrack raceTrack;
	private DriverInventory drivers;
	private int timeElapsed;
	private int grandPrize;
	
	/**
	 * Construct a new race event. This is where drivers will compete for a cash prize.
	 */
	public RaceEvent() {
		this.eventName = "Some event";
		this.raceTrack = new RaceTrack();
		this.drivers = new DriverInventory();
		this.timeElapsed = 0;
		this.grandPrize = 10000;
	}

	/**
	 * Retrieve the track which the Drivers will be competing on.
	 * @return the track
	 */
	public RaceTrack getRaceTrack() {
		return raceTrack;
	}

	/**
	 * Set the track which the Drivers will be competing on.
	 * @param track the track to set
	 */
	public void setRaceTrack(RaceTrack track) {
		this.raceTrack = track;
	}

	/**
	 * Retrieve an inventory of all drivers participating in the race event.
	 * @return the drivers as a DriverInventory
	 */
	public DriverInventory getDrivers() {
		return drivers;
	}

	/**
	 * Adds a driver to the race event.
	 * @param driver the driver to add
	 */
	public void addDriver(Driver driver) {
		this.drivers.add(driver);
	}
	
	/**
	 * Removes a driver from the race event.
	 * @param driver the driver to add
	 */
	public void removeDriver(Driver driver) {
		if (this.drivers.remove(driver)) 
			System.out.println("Driver removed");
		System.out.println("Unable to remove driver. The specified driver is not in the race event.");
	}

	/**
	 * Retrieve the current amount of time elapsed during the race.
	 * @return the totalTime
	 */
	public int getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * Set the current amount of time elapsed during the race.
	 * @param totalTime the totalTime to set
	 */
	public void setTimeElapsed(int time) {
		this.timeElapsed = time;
	}
	
	/**
	 * Add one time unit to the current amount of time elapsed during the race.
	 * @param totalTime the totalTime to set
	 */
	public void incrementTimeElapsed() {
		this.timeElapsed++;
	}

	/**
	 * Retrieve the grand prize amount for placing first in the event.
	 * @return the grandPrize
	 */
	public int getGrandPrize() {
		return grandPrize;
	}

	/**
	 * Set the grand prize amount for placing first in the event.
	 * @param grandPrize the grandPrize to set
	 */
	public void setGrandPrize(int grandPrize) {
		this.grandPrize = grandPrize;
	}
	
	/**
	 * Retrieve the name for this race event.
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * Set the name for this race event.
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * Lets each driver perform a step on the race track to advance forward or run down an idle timer.
	 */
	public void stepAllDrivers() {
		// step every driver in the list.
	}
	
	/**
	 * Indicate whether or not there are still Drivers racing on the track.
	 * @return boolean
	 */
	public boolean isFinished() {
		// Loop through each driver and check their states. 
		// If any one Driver is still in a Racing state, then return false. 
		// Otherwise, everyone is finished so return true.
		InventoryIterator<Driver> driverIterator = this.getDrivers().iterator();
		boolean isFinished = true;
		while(driverIterator.hasNext() && isFinished) {
			if (!(driverIterator.next().getState() instanceof Racing)) {
				isFinished = false;
			}
		}
		return isFinished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// RaceTrack goes here
		result = prime * result + grandPrize;
		result = prime * result + timeElapsed;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof RaceEvent) {
			RaceEvent otherObj = (RaceEvent) other;
			
			// Time elapsed and the drivers list are not included here
			// since they don't define the event itself.
			if (!this.getEventName().equals(otherObj.getEventName()))
				return false;
			if (!this.getRaceTrack().equals(otherObj.getRaceTrack()))
				return false;
			if (this.getGrandPrize() != otherObj.getGrandPrize())
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.eventName;
	}
}
