package com.group3.racingbot;

import com.group3.racingbot.driverstate.Racing;
import com.group3.racingbot.inventory.Iterator;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.standings.DriverStanding;
import com.group3.racingbot.standings.Standings;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An event which a Driver can sign up for and participate in for rewards.
 * @author Nick Sabia
 *
 */
public class RaceEvent {
	private final String id;
	private RaceTrack raceTrack;
	//private DriverInventory drivers;
	private final long createdOn;
	private int timeElapsed;
	private int grandPrize;
	//private List<DriverStanding> standings;
	private Standings standings;
	
	public RaceEvent() {
		this.id = this.generateId();
		this.raceTrack = new RaceTrack();
		//this.drivers = new DriverInventory();
		this.timeElapsed = 0;
		this.grandPrize = 10000;
		this.createdOn = new Date().getTime();
		this.standings = new Standings();
	}
	
	private String generateId() {
		String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int alphabetLength = 62;
		int length = 6;
		String result = "";
		for (int i = 0; i < length; i++) {
			result += String.valueOf(alphabet.charAt(ThreadLocalRandom.current().nextInt(0, alphabetLength-1)));
		}
		return result;
	}
	
	/**
	 * Retrieve the time in milliseconds which this event was created.
	 * @return the createdOn
	 */
	public long getCreatedOn() {
		return createdOn;
	}

	/**
	 * Retrieve the current driver standings for this race event
	 * @return the standings
	 */
	public Standings getStandings() {
		return standings;
	}

	/**
	 * Set the current driver standings for this race event
	 * @param standings the standings to set
	 */
	public void setStandings(Standings standings) {
		this.standings = standings;
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
	//public DriverInventory getDrivers() {
	//	return drivers;
	//}

	/**
	 * Adds a driver to the race event.
	 * @param driver the driver to add
	 */
	//public void addDriver(Driver driver) {
	//	this.drivers.add(driver);
	//}

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
	 * Retrieve the ID for this race event.
	 * @return the eventName
	 */
	public String getId() {
		return id;
	}

	/**
	 * Lets each driver perform a step on the race track to advance forward or run down an idle timer.
	 */
	public String stepAllDrivers() {
		this.incrementTimeElapsed(); // Advance time
		Iterator<DriverStanding> driverIterator = standings.iterator();
		String stepResult = "";
		while (driverIterator.hasNext()) {
			DriverStanding currentDriverStanding = driverIterator.next();
			if (currentDriverStanding.getDriver().getState() instanceof Racing) {
				// Allow the driver to make their move on the track
				Racing currentDriverState = (Racing) currentDriverStanding.getDriver().getState();
				stepResult += currentDriverStanding.getDriver().raceStep() + "\n";
				
				// Update the total distance traveled to later find out the position of this driver in the race.
				currentDriverStanding.setDistanceTraveled(currentDriverState.getTotalDistanceTraveled()); 
				
				// Update the standings to reflect updated driver positions.
				this.standings.updateStandings();
			}	
		}
		return stepResult;
	}
	
	/**
	 * Indicate whether or not there are still Drivers racing on the track.
	 * @return boolean
	 */
	public boolean isFinished() {
		// Loop through each driver and check their states. 
		// If any one Driver is still in a Racing state, then return false. 
		// Otherwise, everyone is finished so return true.
		//InventoryIterator<Driver> driverIterator = this.getDrivers().iterator();
		Iterator<DriverStanding> driverIterator = standings.iterator();
		boolean isFinished = true;
		while(driverIterator.hasNext() && isFinished) {
			if (driverIterator.next().getDriver().getState() instanceof Racing) {
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
			if (!this.getId().equals(otherObj.getId()))
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
		return this.id;
	}
}
