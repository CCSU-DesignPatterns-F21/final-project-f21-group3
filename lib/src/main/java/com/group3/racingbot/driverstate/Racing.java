/**
 * 
 */
package com.group3.racingbot.driverstate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.TrackNode;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * A state where the Driver is currently racing. A Driver may leave this state once they finish the race.
 * @author Nick Sabia
 *
 */
//@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Aggressive.class),
//        @JsonSubTypes.Type(value = Normal.class),
//        @JsonSubTypes.Type(value = Defensive.class),
//		@JsonSubTypes.Type(value = Crashed.class)})
//@BsonDiscriminator(value="Racing", key="_cls")
public abstract class Racing implements DriverState {
	@BsonIgnore
	private Player player;
	private String playerId;
	@JsonBackReference
	private Driver driver;
	private String driverId;
	@BsonIgnore
	private Car car;
	private String carId;
	private RaceTrack raceTrack;
	@BsonIgnore
	private RaceEvent raceEvent;
	private String raceEventId;
	private int straightDistance;
	private int cornerDistance;
	//private int position;
	private int totalDistanceTraveled; // Used to compare with other drivers to determine position.
	private TrackNode currentNode;

	@BsonCreator
	public Racing(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("carId") String carId, @BsonProperty("raceEventId") String raceEventId, @BsonProperty("raceTrack") RaceTrack raceTrack) {
		this.player = null;
		this.playerId = playerId;
		this.driver = null;
		this.driverId = driverId;
		this.car = null;
		this.carId = carId;
		this.raceTrack = raceTrack;
		this.raceEventId = raceEventId;
		this.straightDistance = 0;
		this.cornerDistance = 0;
		this.totalDistanceTraveled = 0;
		this.currentNode = null;
		//this.position = 1;
	}
	
	/**
	 * Retrieve the race event the driver is participating in.
	 * @return the raceEvent
	 */
	public RaceEvent getRaceEvent() {
		return raceEvent;
	}

	/**
	 * Set the race event the driver is participating in.
	 * @param raceEvent the raceEvent to set
	 */
	public void setRaceEvent(RaceEvent raceEvent) {
		this.raceEvent = raceEvent;
	}

	/**
	 * Retrieve the player which owns this driver.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the player which owns this driver.
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Retrieve the player id of the player which owns this driver.
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the player id of the player which owns this driver.
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * Retrieve the driver id of this driver.
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}

	/**
	 * Set the driver id of this driver.
	 * @param driverId the driverId to set
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	/**
	 * Retrieve the car id of the car which the driver is using.
	 * @return the carId
	 */
	public String getCarId() {
		return carId;
	}

	/**
	 * Set the car id of the car which the driver is using.
	 * @param carId the carId to set
	 */
	public void setCarId(String carId) {
		this.carId = carId;
	}

	/**
	 * Retrieve the race track which is currently being raced on.
	 * @return the raceTrack
	 */
	public RaceTrack getRaceTrack() {
		return raceTrack;
	}

	/**
	 * Set the race track which is currently being raced on.
	 * @param raceTrack the raceTrack to set
	 */
	public void setRaceTrack(RaceTrack raceTrack) {
		this.raceTrack = raceTrack;
	}

	/**
	 * Retrieve the driver who is currently racing.
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver who is currently racing.
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	/**
	 * Retrieve the car which is currently racing.
	 * @return the car
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * Set the car which is currently racing.
	 * @param car the car to set
	 */
	public void setCar(Car car) {
		this.car = car;
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
	 * @return the totalDistanceTraveled
	 */
	public int getTotalDistanceTraveled() {
		return totalDistanceTraveled;
	}

	/**
	 * @param totalDistanceTraveled the totalDistanceTraveled to set
	 */
	public void setTotalDistanceTraveled(int totalDistanceTraveled) {
		this.totalDistanceTraveled = totalDistanceTraveled;
	}

	/**
	 * Retrieve the race event.
	 * @return the raceEvent
	 */
	//public RaceEvent getRaceEvent() {
	//	return raceEvent;
	//}

	/**
	 * Set the race event.
	 * @param raceEvent the raceEvent to set
	 */
	//public void setRaceEvent(RaceEvent raceEvent) {
	//	this.raceEvent = raceEvent;
	//}

	/**
	 * Retrieve the distance this Driver is able to travel on straight track nodes this turn.
	 * @return the straightDistance
	 */
	public int getStraightDistance() {
		return straightDistance;
	}

	/**
	 * Set the distance this Driver is able to travel on straight track nodes this turn.
	 * @param straightDistance the straightDistance to set
	 */
	public void setStraightDistance(int straightDistance) {
		this.straightDistance = straightDistance;
	}

	/**
	 * Retrieve the distance this Driver is able to travel on corner track nodes this turn.
	 * @return the cornerDistance
	 */
	public int getCornerDistance() {
		return cornerDistance;
	}

	/**
	 * Set the distance this Driver is able to travel on straight track nodes this turn.
	 * @param cornerDistance the cornerDistance to set
	 */
	public void setCornerDistance(int cornerDistance) {
		this.cornerDistance = cornerDistance;
	}
	
	/**
	 * Retrieve the pole position of this driver in the race.
	 * @return the position
	 */
	//public int getPosition() {
	//	return position;
	//}

	/**
	 * Set the pole position of this driver in the race.
	 * @param position the position to set
	 */
	//public void setPosition(int position) {
	//	this.position = position;
	//}

	/**
	 * Retrieve the node which the Driver is currently on.
	 * @return the currentNode
	 */
	public TrackNode getCurrentNode() {
		return currentNode;
	}

	/**
	 * Set the node which the Driver is currently on.
	 * @param currentNode the currentNode to set
	 */
	public void setCurrentNode(TrackNode currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * Determines which state the driver will be in for the current unit of time.
	 */
	abstract public void rollDriverState();
	
	/**
	 * Determine how far the driver can go on straight nodes this turn based on the Driver's straights skill and the Car's speed and acceleration.
	 * @param bonusMultiplier increase (or decrease) the resulting distance.
	 * @return distance which the driver can travel this turn.
	 */
	public int rollStraightDistance(double bonusMultiplier) {
		// Acceleration affects lower bound, speed affects upper bound
		// Multiply lowerBound and upperBound by the log of the straightSkill.
		// If acceleration is 4x more than speed, then lower bound == upper bound
		double straightSkillBonus = Math.log(this.getDriver().getStraights());
		int lowerBound = (int) Math.floor((this.getCar().getAccelerationRating()/4)*straightSkillBonus * bonusMultiplier);
		int upperBound = (int) Math.floor(this.getCar().getSpeedRating()*straightSkillBonus * bonusMultiplier);
		if (lowerBound > upperBound)
			lowerBound = upperBound - 1;
		int distance = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
		return distance;
	}
	
	/**
	 * Determine how far the driver can go on corner nodes this turn based on the Driver's cornering skill and the Car's braking and handling.
	 * @param bonusMultiplier increase (or decrease) the resulting distance.
	 * @return distance which the driver can travel this turn.
	 */
	public int rollCornerDistance(double bonusMultiplier) {
		// Braking affects lower bound, handling affects upper bound
		// Multiply lowerBound and upperBound by the log of the corneringSkill.
		// If braking is 4x more than handling, then lower bound == upper bound
		double cornerSkillBonus = Math.log(this.getDriver().getCornering());
		int lowerBound = (int) Math.floor((this.getCar().getBrakingRating()/4)*cornerSkillBonus * bonusMultiplier);
		int upperBound = (int) Math.floor(this.getCar().getHandlingRating()*cornerSkillBonus * bonusMultiplier);
		if (lowerBound > upperBound)
			lowerBound = upperBound - 1;
		int distance = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
		return distance;
	}
	
	@Override
	public void rest() {
		// do nothing
	}

	@Override
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// Check cooldown, if ok then train. Otherwise, remain resting. 
		// Do nothing
	}

	@Override
	public void signUpForRace(Driver driver, Car car, RaceEvent raceEvent) {
		// Check cooldown, if ok then sign up for race. Otherwise, remain resting. 
		// Do nothing
	}
	
	@Override
	public void beginRace(Driver driver) {
		// If in RacePending state and all fields are not null, then race!
		// Do nothing
	}

	@Override
	public void collectReward() {
		// If in completed state, execute this and go to resting state. Otherwise, do nothing.
		// Do nothing
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// If in RacePending state, execute this, go to resting state, and return true. Otherwise do nothing and return false.
		return false;
	}

	@Override
	abstract public String raceStep(Driver driver);

	@Override
	public void completedRace(Driver driver) {
		// If in the Racing state, move to FinishedRace state.
		this.getDriver().setState(new FinishedRace(this.getDriver(), this.getRaceEvent()));
	}

	@Override
	public void completedTraining(Driver driver) {
		// If in the Training state, move to FinishedTraining state.
		// Do nothing
	}
	
	@Override
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
		
		// Sets both the race event and race track objects if needed.
		if (this.raceEvent == null || this.raceTrack == null) {
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
				// Get the race event's race event and race track
				this.raceEvent = dbh.getRaceEvent(this.raceEventId);
				this.raceTrack = this.raceEvent.getRaceTrack();
			}
		}
		
		// Sets the car object if needed.
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
	
	/**
	 * Apply a random amount of damage to at least 1 component and at most 3 components of the car.
	 * @param car
	 */
	abstract public void crash(Car car);
}