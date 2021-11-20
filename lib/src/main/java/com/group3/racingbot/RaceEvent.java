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
	private String id;
	private RaceTrack raceTrack;
	//private DriverInventory drivers;
	private final long createdOn;
	private int timeElapsed;
	private int grandPrize;
	//private List<DriverStanding> standings;
	private Standings standings;
	
	public RaceEvent() {
		this.id = "";
		this.raceTrack = null;
		//this.drivers = new DriverInventory();
		this.timeElapsed = 0;
		this.grandPrize = 10000;
		this.createdOn = new Date().getTime();
		this.standings = new Standings();
	}
	
	/**
	 * Generates a race track using the ID of the race event as a seed
	 */
	public RaceTrack generateRaceTrackFromId() {
		return new RaceTrack(Long.parseLong(this.id, 36));
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the ID for this race event.
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Lets each driver perform a step on the race track to advance forward or run down an idle timer.
	 */
	public String stepAllDrivers() {
		DBHandler dbh = DBHandler.getInstance();
		
		this.incrementTimeElapsed(); // Advance time
		Iterator<DriverStanding> driverIterator = standings.iterator();
		String stepResult = "";
		//Player currentPlayer = null;
		Driver currentDriver = null;
		Racing currentRacingState = null;
		while (driverIterator.hasNext()) {
			DriverStanding currentDriverStanding = driverIterator.next();
			if (currentDriverStanding.getDriver().getState() instanceof Racing) {
				currentDriver = currentDriverStanding.getDriver();
				currentRacingState = (Racing) currentDriver.getState();
				
				// Allow the driver to make their move on the track
				stepResult += currentDriver.raceStep() + "\n";
				
				// Update the total distance traveled to later find out the position of this driver in the race.
				currentDriverStanding.setDistanceTraveled(currentRacingState.getTotalDistanceTraveled()); 
				
				// Update the standings to reflect updated driver positions.
				this.standings.updateStandings();
				
				// Update the driver within the Player object to reflect state changes.
				//currentPlayer = currentDriver.getPlayer();
				//currentPlayer.getOwnedDrivers().update(currentDriver);
				//dbh.updateUser(currentPlayer);
			}	
		}
		// Update the db with the details of the standings.
		dbh.updateRaceEvent(this);
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
		return "**ID:** " + this.id + "\n**PRIZE:** " + this.grandPrize + "\n**TRACK INFO:**\n" + this.raceTrack;
	}
}
