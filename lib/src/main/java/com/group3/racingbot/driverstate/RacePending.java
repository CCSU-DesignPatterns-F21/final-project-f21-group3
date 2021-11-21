package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.standings.DriverStanding;

/**
 * A state where the Driver is signed up for a race and is ready for it to start.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="RacePending", key="_cls")
public class RacePending implements DriverState {
	@BsonIgnore
	private Player player;
	private String playerId;
	@BsonIgnore
	private Driver driver;
	private String driverId;
	@BsonIgnore
	private Car car;
	private String carId;
	@BsonIgnore
	private RaceEvent raceEvent;
	private String raceEventId;
	
	/**
	 * Commit a driver to a race that will start sometime soon.
	 * @param driverId
	 * @param carId
	 * @param raceEventId
	 */
	@BsonCreator
	public RacePending(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("carId") String carId, @BsonProperty("raceEventId") String raceEventId) {
		this.player = null;
		this.playerId = playerId;
		this.driver = null;
		this.driverId = driverId;
		this.car = null;
		this.carId = carId;
		this.raceEvent = null;
		this.raceEventId = raceEventId;
	}
	
	/**
	 * Retrieve the player who owns the driver which is awaiting the start of the race event.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the player who owns the driver which is awaiting the start of the race event.
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Retrieve the player id of the player who owns the driver which is awaiting the start of the race event.
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the player id of the player who owns the driver which is awaiting the start of the race event.
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * Retrieve the race event which the driver will take part in.
	 * @return the raceEvent
	 */
	public RaceEvent getRaceEvent() {
		return raceEvent;
	}

	/**
	 * Set the race event which the driver will take part in.
	 * @param raceEvent the raceEvent to set
	 */
	public void setRaceEvent(RaceEvent raceEvent) {
		this.raceEvent = raceEvent;
	}

	/**
	 * Retrieve the Driver who's signed up for the race.
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}
	
	/**
	 * Set the Driver who's signed up for the race.
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	/**
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}

	/**
	 * @param driverId the driverId to set
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	/**
	 * @return the carId
	 */
	public String getCarId() {
		return carId;
	}

	/**
	 * @param carId the carId to set
	 */
	public void setCarId(String carId) {
		this.carId = carId;
	}

	/**
	 * Retrieve the car that will be used in the race.
	 * @return the car
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * Set the car that will be used in the race.
	 * @param car the car to set
	 */
	public void setCar(Car car) {
		this.car = car;
	}
	
	/**
	 * Retrieve the race event id for the race event which the driver will take part in.
	 * @return the raceEventId
	 */
	public String getRaceEventId() {
		return raceEventId;
	}

	/**
	 * Set the race event id for the race event which the driver will take part in.
	 * @param raceEventId the raceEventId to set
	 */
	public void setRaceEventId(String raceEventId) {
		this.raceEventId = raceEventId;
	}

	@Override
	public String rest(Driver driver) {
		DBHandler dbh = DBHandler.getInstance();
		// Withdraws the driver from a race event.
		this.withdrawFromRace(driver);
		return driver.getName() + "(" + driver.getId() + ") withdrew from the race event " + this.raceEventId + ".";
	}

	@Override
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void signUpForRace(Driver driver, Car car, RaceEvent raceEvent) {
		// TODO Auto-generated method stub
		System.out.println("Already registed for a race event. Withdraw from the event currently registered for then try again."); 
	}
	
	@Override
	public void beginRace(Driver driver) {
		// Ensure that all necessary data can be pulled using id's from the database.
		if (refreshFromDB()) {
			driver.setState(new Normal(this.playerId, this.driverId, this.carId, this.raceEventId));
		}
		else {
			System.out.println("Unable to enter into a race, values in the database may have been deleted which prevents Driver " + this.driverId + " from participating in RaceEvent " + this.raceEventId + ".");
		}
	}

	@Override
	public void collectReward() {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		refreshFromDB();
		
		DBHandler dbh = DBHandler.getInstance();
		Player p = driver.getPlayer();
		Driver updatedDriver = driver;
		
		// Check that the event hasn't been deleted.
		if (dbh.raceEventExists(raceEventId)) {
			// Remove the driver from the event in the DB
			RaceEvent updatedRaceEvent = dbh.getRaceEvent(this.raceEventId);
			updatedRaceEvent.getStandings().removeDriver(this.driver.getId());
			dbh.updateRaceEvent(updatedRaceEvent);
		}
		
		// Update the driver's state to the Resting state
		updatedDriver.setState(new Resting());
		if (p.getOwnedDrivers().update(updatedDriver)) {
			dbh.updateUser(p);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public DriverStanding raceStep(Driver driver, DriverStanding driverStanding) {
		// TODO Auto-generated method stub
		// Do nothing
		return null;
	}

	@Override
	public void completedRace(Driver driver) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void completedTraining(Driver driver) {
		// TODO Auto-generated method stub
		// Do nothing
	}
	
	@Override
	public boolean refreshFromDB() {
		DBHandler dbh = DBHandler.getInstance();
		
		// Verify that the driver still exists.
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
			// Get the race event from the DB and set it for the state
			this.raceEvent = dbh.getRaceEvent(this.raceEventId);
		}
		
		if (this.car == null) {
			try {
				this.car = this.player.getOwnedCars().getById(carId);
			}
			catch (NotFoundException e) {
				System.out.println("Driver " + this.driverId + " is using Car " + this.carId + " which doesn't exist in the Database. Attempting to remove Driver from race event " + this.raceEventId + "...");
				
				// Update race event in DB
				if (dbh.removeDriverFromRaceEventInDB(this.driverId, this.raceEventId)) {
					System.out.println("Driver " + this.driverId + " removed from race event " + this.raceEventId + ". Switching driver state to resting...");
				}
				else {
					System.out.println("Unable to remove Driver " + this.driverId + " from race event " + this.raceEventId + ". Switching driver state to resting...");
				}
				
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
		}
		return true;
	}
	
	@Override
	public String driverStatus(Driver driver) {
		return driver.getName() + "(" + driver.getId() + ") is currently awaiting the start of race event " + this.raceEventId + ". You may withdraw " + driver.getName() + " from the race before the race starts.\n**Withdraw**\n!r debug driver withdraw";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((raceEventId == null) ? 0 : raceEventId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof RacePending) {
			RacePending otherObj = (RacePending) other;
			
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
		return "RacePending: " + this.getRaceEventId();
	}
}