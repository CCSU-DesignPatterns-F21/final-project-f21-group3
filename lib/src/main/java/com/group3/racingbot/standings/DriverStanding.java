/**
 * 
 */
package com.group3.racingbot.standings;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.driverstate.Resting;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.TrackNode;

/**
 * Keep track of a driver's position within a race event.
 * @author Nick Sabia
 */
public class DriverStanding {
	@BsonIgnore
	private  Player player;
	private final String playerId;
	@BsonIgnore
	private Driver driver;
	private final String driverId;
	private RaceTrack raceTrack;
	private String raceEventId;
	private TrackNode currentNode;
	private int position;
	private int distanceTraveled;
	private int timeCompleted;
	
	/**
	 * Constructs a driver standing. In other words, this holds the pole position as well as distance traveled of the Driver.
	 * @param playerId the player which owns the driver
	 * @param driverId the driver who participated in the race
	 * @param raceEventId the event which the driver participated in
	 */
	@BsonCreator
	public DriverStanding(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("raceEventId") String raceEventId) {
		this.player = null;
		this.playerId = playerId;
		this.driver = null;
		this.driverId = driverId;
		this.raceTrack = null;
		this.raceEventId = raceEventId;
		this.position = 1;
		this.distanceTraveled = 0;
		this.timeCompleted = 0;
		this.currentNode = null;
	}
	
	/**
	 * Retrieve the player which owns the driver which this driver standing is based upon.
	 * @return the player
	 */
	public Player getPlayer() {
		this.refreshFromDB();
		return player;
	}

	/**
	 * Set the player which owns the driver which this driver standing is based upon.
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Retrieve the driver which this driver standing is based upon.
	 * @return the driver
	 */
	public Driver getDriver() {
		this.refreshFromDB();
		return driver;
	}

	/**
	 * Set the driver which this driver standing is based upon.
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	/**
	 * Retrieve the race track from the race event that the driver is participating in.
	 * @return the raceTrack
	 */
	public RaceTrack getRaceTrack() {
		this.refreshFromDB();
		return raceTrack;
	}

	/**
	 * Set the race track from the race event that the driver is participating in.
	 * @param raceTrack the raceTrack to set
	 */
	public void setRaceTrack(RaceTrack raceTrack) {
		this.raceTrack = raceTrack;
	}

	/**
	 * Retrieve the race event id of the race event that the driver is participating in.
	 * @return the raceEventId
	 */
	public String getRaceEventId() {
		return raceEventId;
	}

	/**
	 * Set the race event id of the race event that the driver is participating in.
	 * @param raceEventId the raceEventId to set
	 */
	public void setRaceEventId(String raceEventId) {
		this.raceEventId = raceEventId;
	}

	/**
	 * Retrieve the node which the driver is currently on.
	 * @return the currentNode
	 */
	public TrackNode getCurrentNode() {
		this.refreshFromDB();
		return currentNode;
	}

	/**
	 * Set the node which the driver is currently on.
	 * @param currentNode the currentNode to set
	 */
	public void setCurrentNode(TrackNode currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * Retrieve the pole position of the driver in the race event.
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set the pole position of the driver in the race event.
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Retrieve the total distance which the driver has traveled along the race track.
	 * @return the distanceTraveled
	 */
	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * Set the total distance which the driver has traveled along the race track.
	 * @param distanceTraveled the distanceTraveled to set
	 */
	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	/**
	 * Retrieve the time which the driver reached the end of the race track. If the driver has not reached the end of the track, then return 0.
	 * @return the timeCompleted
	 */
	public int getTimeCompleted() {
		return timeCompleted;
	}

	/**
	 * Set the time which the driver reached the end of the race track.
	 * @param timeCompleted the timeCompleted to set
	 */
	public void setTimeCompleted(int timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	/**
	 * Retrieve the playerid of the player which owns the driver which this driver standing is based upon.
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the playerid of the player which owns the driver which this driver standing is based upon.
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}

	/**
	 * In the event that the server shuts down, this will grab all necessary data from the database in order to get back up and running.
	 * @return whether or not all missing objects were successfully obtained from the database.
	 */
	public boolean refreshFromDB() {
		DBHandler dbh = DBHandler.getInstance();
		
		// Verify that the driver still exists.
		// Sets both the driver and player objects if needed.
		if (this.driver == null) {
			// The server could have restarted and the instance of this class may only hold the id's
			// In this case, retrieve all necessary info from the database.
			this.player = dbh.getPlayer(this.playerId);
			try {
				if (this.player != null) {
					this.driver = this.player.getOwnedDrivers().getById(this.driverId);
				}
				else {
					System.out.println("Player " + this.playerId + " is missing from Database. Attempting to remove Driver " + this.driverId + " from race event " + this.raceEventId + "...");
				}
			}
			catch(NotFoundException e) {
				// Driver is missing from DB
				System.out.println("Driver " + this.driverId + " is missing from Database. Attempting to remove Driver from race event " + this.raceEventId + "...");
			}
			
			if (this.player == null || this.driver == null) {
				if (dbh.removeDriverFromRaceEventInDB(this.driverId, this.raceEventId)) {
					System.out.println("Driver " + this.driverId + " has been removed from race event " + this.raceEventId + ". Setting driver state to resting...");
					if (dbh.updateDriverStateInDB(this.playerId, this.driverId, new Resting())) {
						System.out.println("Driver " + this.driverId + " is now in a resting state.");
					}
					else {
						System.out.println("Unable to put Driver " + this.driverId + " into a resting state.");
					}
				}
				else {
					System.out.println("Race event " + this.raceEventId + " does not exist. No further actions taken.");
				}
				return false;
			}
		}
		
		// Sets the race track and current node objects if needed.
		if (this.raceTrack == null) {
			// Verify that the race event still exists.
			if (!dbh.raceEventExists(this.raceEventId)) {
				System.out.println("Race event " + this.raceEventId + " does not exist. Attempting to change the state of Driver " + this.driverId + " to a resting state.");
				
				// Update driver state in DB
				if (dbh.updateDriverStateInDB(this.playerId, this.driverId, new Resting())) {
					System.out.println("Driver " + this.driverId + " is now in a resting state.");
				}
				else {
					System.out.println("Unable to change Driver " + this.driverId + " to a resting state in the Database.");
				}
				
				// Database updated, now update locally.
				this.driver.setState(new Resting());
				return false;
			}
			else {
				// Get the race event's race track and the current node which the driver is in.
				RaceEvent raceEvent = dbh.getRaceEvent(this.raceEventId);
				this.raceTrack = raceEvent.getRaceTrack();
				if (this.currentNode != null) {
					try {
						this.currentNode = raceEvent.getStandings().getDriverStandingById(driverId).getCurrentNode();
						// Resume from where the driver left off.
						this.raceTrack.resumeProgressFromTrackNode(this.currentNode);
						System.out.println("Found the DriverStanding for Driver " + this.driverId + ". The driver can now resume from where they left off in the race.");
						return true;
					}
					catch (NotFoundException e) {
						System.out.println("Unable to find the the DriverStanding for Driver " + this.driverId + ".");
						e.printStackTrace();
						return false;
					}
				}
				// Driver is starting from the beginning
				this.currentNode = this.raceTrack.obtainFirstNode();
				return true;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result += Long.parseLong(this.playerId, 36);
		result += Long.parseLong(this.driverId, 36);
		result += Long.parseLong(this.raceEventId, 36);
		result += this.position;
		result += this.distanceTraveled;
		result += this.timeCompleted;
		result *= prime;
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof DriverStanding) {
			DriverStanding otherObj = (DriverStanding) other;
			
			if (!(this.getPlayerId().equals(otherObj.getPlayerId())))
				return false;
			if (!(this.getDriverId().equals(otherObj.getDriverId())))
				return false;
			if (!(this.getRaceEventId().equals(otherObj.getRaceEventId())))
				return false;
			if (this.getPosition() != otherObj.getPosition())
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		this.refreshFromDB();
		return this.position + ". | Driver: " + this.driver.getName() + " (" + this.driverId + ") | Time Completed: " + this.timeCompleted;
	}
}
