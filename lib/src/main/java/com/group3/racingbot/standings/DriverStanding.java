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
	 * @return the player
	 */
	public Player getPlayer() {
		this.refreshFromDB();
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		this.refreshFromDB();
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	/**
	 * @return the raceTrack
	 */
	public RaceTrack getRaceTrack() {
		this.refreshFromDB();
		return raceTrack;
	}

	/**
	 * @param raceTrack the raceTrack to set
	 */
	public void setRaceTrack(RaceTrack raceTrack) {
		this.raceTrack = raceTrack;
	}

	/**
	 * @return the raceEventId
	 */
	public String getRaceEventId() {
		return raceEventId;
	}

	/**
	 * @param raceEventId the raceEventId to set
	 */
	public void setRaceEventId(String raceEventId) {
		this.raceEventId = raceEventId;
	}

	/**
	 * @return the currentNode
	 */
	public TrackNode getCurrentNode() {
		this.refreshFromDB();
		return currentNode;
	}

	/**
	 * @param currentNode the currentNode to set
	 */
	public void setCurrentNode(TrackNode currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the distanceTraveled
	 */
	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * @param distanceTraveled the distanceTraveled to set
	 */
	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	/**
	 * @return the timeCompleted
	 */
	public int getTimeCompleted() {
		return timeCompleted;
	}

	/**
	 * @param timeCompleted the timeCompleted to set
	 */
	public void setTimeCompleted(int timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
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
	
}
