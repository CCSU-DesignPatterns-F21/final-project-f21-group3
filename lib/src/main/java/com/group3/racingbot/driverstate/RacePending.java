package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.racetrack.RaceTrack;

/**
 * A state where the Driver is signed up for a race and is ready for it to start.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="RacePending", key="_cls")
public class RacePending implements DriverState {
	@BsonIgnore
	private Driver driver;
	private Car car;
	//private RaceEvent raceEvent;
	private String raceEventId;
	
	/**
	 * Commit a driver to a race that will start sometime soon.
	 * @param driver
	 * @param car
	 * @param raceEventId
	 */
	@BsonCreator
	public RacePending(@BsonProperty("driver") Driver driver, @BsonProperty("car") Car car, @BsonProperty("raceEventId") String raceEventId) {
		this.driver = driver;
		this.car = car;
		//this.raceEvent = raceEvent;
		this.raceEventId = raceEventId;
		
		// TODO: Register for the event and update the DB
		
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

	/**
	 * Retrieve the race event which the driver will take part in.
	 * @return the raceEvent
	 */
	//public RaceEvent getRaceEvent() {
	//	return raceEvent;
	//}

	/**
	 * Set the race event which the driver will take part in.
	 * @param raceEvent the raceEvent to set
	 */
	//public void setRaceEvent(RaceEvent raceEvent) {
	//	this.raceEvent = raceEvent;
	//}

	@Override
	public void rest() {
		// TODO Auto-generated method stub
		this.getDriver().setState(new Resting());
	}

	@Override
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public void signUpForRace(Driver driver, Car car, String raceEventId) {
		// TODO Auto-generated method stub
		System.out.println("Already registed for a race event. Withdraw from the event currently registered for then try again."); 
	}
	
	@Override
	public void beginRace() {
		// If in RacePending state and all fields are not null, then race!
		if (this.getDriver() != null) {
			if (this.getCar() != null) {
				if(this.getRaceEventId() != null) {
					// TODO: get race track from the race event in the database.
					DBHandler dbh = DBHandler.getInstance();
					RaceTrack raceTrack = null; // Initialize the race track
					if (dbh.raceEventExists(this.getRaceEventId())) {
						// We now know the event exists.
						// Obtain the race track from the database.
						raceTrack = dbh.getRaceEvent(this.getRaceEventId()).getRaceTrack();
						this.getDriver().setState(new Normal(this.getDriver(), this.getCar(), raceTrack, this.getRaceEventId()));
					}
					else {
						System.out.println("Cannot enter race, that race event does not exist.");
					}
				}
				else {
					System.out.println("Cannot enter race, no race event has been chosen."); 
				}
			}
			else {
				System.out.println("Cannot enter race, no car has been chosen to race."); 
			}
		}
		else { 
			System.out.println("Cannot enter race, no driver has been chosen to race."); 
		}
	}

	@Override
	public void collectReward() {
		// TODO Auto-generated method stub
		// Do nothing
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// TODO Auto-generated method stub
		this.getDriver().setState(new Resting());
		return true;
	}

	@Override
	public String raceStep(Driver driver) {
		// TODO Auto-generated method stub
		// Do nothing
		return "";
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
			// Check that the two race pending states are for the same race event.
			if (!this.getRaceEventId().equals(otherObj.getRaceEventId()))
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