package com.group3.racingbot;

import com.group3.racingbot.driverstate.Racing;
import com.group3.racingbot.inventory.Iterator;
import com.group3.racingbot.inventory.Unique;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.TrackNode;
import com.group3.racingbot.standings.DriverStanding;
import com.group3.racingbot.standings.Standings;

import java.util.Date;

/**
 * An event which a Driver can sign up for and participate in for rewards.
 * @author Nick Sabia
 *
 */
public class RaceEvent implements Unique {
	private String id;
	private RaceTrack raceTrack;
	//private DriverInventory drivers;
	private final long createdOn;
	private int timeElapsed;
	private int grandPrize;
	//private List<DriverStanding> standings;
	private Standings standings;
	
	/**
	 * Constructs a new race event. Race events do not start with an id, standings, or a race track for mongodb purposes. To generate and setup all of these things, call the initialize method.
	 */
	public RaceEvent() {
		this.id = "";
		this.raceTrack = null;
		//this.drivers = new DriverInventory();
		this.timeElapsed = 0;
		this.grandPrize = 1000;
		this.createdOn = new Date().getTime();
		this.standings = null;
	}
	
	/**
	 * Assigns a randomly generated id to the race event and uses that id to setup the race event. Generates a race track, sets up the standings so that drivers may register, and sets the grand prize for winning the event.
	 */
	public void initialize() {
		DBHandler dbh = DBHandler.getInstance();
		this.id = dbh.generateId(6);
		this.standings = new Standings(this.id);
		this.raceTrack = this.generateRaceTrackFromId();
		this.grandPrize = ((this.raceTrack.calculateTrackLength() + 99) / 100) * 100; // Uses the distance of the track to calculate the grand prize. Rounds to nearest hundred.
	}
	
	/**
	 * Generates a race track using the ID of the race event as a seed
	 * @return the generated race track
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
	 * @return the race track
	 */
	public RaceTrack getRaceTrack() {
		return raceTrack;
	}

	/**
	 * Set the track which the Drivers will be competing on.
	 * @param track the race track to set
	 */
	public void setRaceTrack(RaceTrack track) {
		this.raceTrack = track;
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
	 * @return the progress as a string of all drivers who are still competing in the race
	 */
	public String stepAllDrivers() {
		DBHandler dbh = DBHandler.getInstance();
		
		this.timeElapsed++; // Advance time
		Iterator<DriverStanding> driverIterator = standings.iterator();
		Driver currentDriver = null;
		DriverStanding currentDriverStanding = null;
		while (driverIterator.hasNext()) {
			currentDriverStanding = driverIterator.next();
			currentDriver = currentDriverStanding.getDriver();
			if (currentDriver.getState() instanceof Racing) {
				// Allow the driver to make their move on the track
				currentDriverStanding = currentDriver.raceStep(currentDriverStanding);
				this.standings.update(currentDriverStanding);
			}
		}
		// Sort the standings to reflect updated driver positions.
		this.standings.sortStandings();
		
		// Update the db with the details of the standings.
		dbh.updateRaceEvent(this);
		return this.standings.toString();
	}
	
	/**
	 * Indicate whether or not there are still Drivers racing on the track.
	 * @return boolean indicating whether or not the race has finished.
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
