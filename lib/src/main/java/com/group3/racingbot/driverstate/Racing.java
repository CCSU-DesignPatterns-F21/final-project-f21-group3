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
import com.group3.racingbot.ComponentFactory.ChassisComponent;
import com.group3.racingbot.ComponentFactory.ComponentType;
import com.group3.racingbot.ComponentFactory.EngineComponent;
import com.group3.racingbot.ComponentFactory.SuspensionComponent;
import com.group3.racingbot.ComponentFactory.TransmissionComponent;
import com.group3.racingbot.ComponentFactory.WheelComponent;
import com.group3.racingbot.exceptions.RaceTrackEndException;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.racetrack.CornerNode;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.StraightNode;
import com.group3.racingbot.racetrack.TrackNode;
import com.group3.racingbot.standings.DriverStanding;
import com.group3.racingbot.standings.Standings;

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
	@BsonIgnore
	private RaceEvent raceEvent;
	private String raceEventId;
	private int straightDistance;
	private int cornerDistance;
	//private int position;
	private int distanceTraveledThisStep; // How far the driver traveled this race step.
	//private int totalDistanceTraveled; // Used to compare with other drivers to determine position.
	//private TrackNode currentNode;
	private double multiplier;

	@BsonCreator
	public Racing(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("carId") String carId, @BsonProperty("raceEventId") String raceEventId) {
		this.player = null;
		this.playerId = playerId;
		this.driver = null;
		this.driverId = driverId;
		this.car = null;
		this.carId = carId;
		//this.raceTrack = raceTrack;
		this.raceEventId = raceEventId;
		this.straightDistance = 0;
		this.cornerDistance = 0;
		this.distanceTraveledThisStep = 0;
		//this.totalDistanceTraveled = 0;
		//this.currentNode = null;
		this.multiplier = 1;
	}
	
	/**
	 * Retrieve how far the driver went in the last race step.
	 * @return the distanceTraveledThisStep
	 */
	public int getDistanceTraveledThisStep() {
		return distanceTraveledThisStep;
	}

	/**
	 * Set how far the driver went in the last race step.
	 * @param distanceTraveledThisStep the distanceTraveledThisStep to set
	 */
	public void setDistanceTraveledThisStep(int distanceTraveledThisStep) {
		this.distanceTraveledThisStep = distanceTraveledThisStep;
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
	//public RaceTrack getRaceTrack() {
	//	return raceTrack;
	//}

	/**
	 * Set the race track which is currently being raced on.
	 * @param raceTrack the raceTrack to set
	 */
	//public void setRaceTrack(RaceTrack raceTrack) {
	//	this.raceTrack = raceTrack;
	//}

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
	//public int getTotalDistanceTraveled() {
	//	return totalDistanceTraveled;
	//}

	/**
	 * @param totalDistanceTraveled the totalDistanceTraveled to set
	 */
	//public void setTotalDistanceTraveled(int totalDistanceTraveled) {
	//	this.totalDistanceTraveled = totalDistanceTraveled;
	//}

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
	 * Retrieve the node which the Driver is currently on.
	 * @return the currentNode
	 */
	//public TrackNode getCurrentNode() {
	//	return currentNode;
	//}

	/**
	 * Set the node which the Driver is currently on.
	 * @param currentNode the currentNode to set
	 */
	//public void setCurrentNode(TrackNode currentNode) {
	//	this.currentNode = currentNode;
	//}

	/**
	 * Return a randomly rolled driver state. The likelihood of rolling a certain racing state depends on the racing state the driver is in. Has a chance to return a DNF state if the driver cannot finish the race.
	 * @return a racing state
	 */
	abstract public DriverState rollDriverState();
	
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
		if (lowerBound >= upperBound) {
			// Since the lower bound matches the upper bound, we can simply return the upper bound as the distance to travel.
			return upperBound;
		}
		// Randomize the distance to travel based on the bounds defined above.
		return ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
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
		if (lowerBound >= upperBound) {
			// Since the lower bound matches the upper bound, we can simply return the upper bound as the distance to travel.
			return upperBound;
		}
		// Randomize the distance to travel based on the bounds defined above.
		return ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
	}
	
	@Override
	public String rest(Driver driver) {
		return driver.getName() + "(" + driver.getId() + ") is currently racing and cannot rest until the race finishes.";
	}

	@Override
	public String beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		// Check cooldown, if ok then train. Otherwise, remain resting. 
		return this.driverStatus(driver);
	}

	@Override
	public String signUpForRace(Driver driver, Car car, RaceEvent raceEvent) {
		// Check cooldown, if ok then sign up for race. Otherwise, remain resting. 
		return this.driverStatus(driver);
	}
	
	@Override
	public String beginRace(Driver driver) {
		// If in RacePending state and all fields are not null, then race!
		return this.driverStatus(driver);
	}

	@Override
	public String collectReward() {
		// If in completed state, execute this and go to resting state. Otherwise, do nothing.
		// Do nothing
		return "";
	}

	@Override
	public boolean withdrawFromRace(Driver driver) {
		// If in RacePending state, execute this, go to resting state, and return true. Otherwise do nothing and return false.
		return false;
	}

	@Override
	// Calculate the distance the driver will travel this turn
	public DriverStanding raceStep(Driver driver, DriverStanding driverStanding) {
		this.refreshFromDB();
		
		DBHandler dbh = DBHandler.getInstance();
		Player updatedPlayer = driver.getPlayer();
		Driver updatedDriver = driver;
		Car updatedCar = this.car;
		DriverStanding updatedDriverStanding = driverStanding;
		RaceTrack raceTrack = updatedDriverStanding.getRaceTrack();
		
		// Calculate the distances that the driver would travel on either type of track node.
		// These will be used to calculate the actual distance traveled.
		this.cornerDistance = this.rollCornerDistance(this.multiplier);
		this.straightDistance = this.rollStraightDistance(this.multiplier);
		
		// Calculate the track node based on the type of track node.
		if (driverStanding.getCurrentNode() instanceof StraightNode) {
			this.distanceTraveledThisStep = this.straightDistance + (int) Math.floor(this.cornerDistance/3);
		}
		else if (driverStanding.getCurrentNode() instanceof CornerNode) {
			this.distanceTraveledThisStep = this.cornerDistance + (int) Math.floor(this.straightDistance/3);
		}
		
		// Advance the driver forward along the track
		boolean completedRace = false;
		try {
			raceTrack.progressForward(updatedDriver, this.distanceTraveledThisStep);
		}
		catch (RaceTrackEndException e) {
			completedRace = true;
		}
		updatedDriverStanding.setRaceTrack(raceTrack);
		
		// Get the total distance traveled from the driver standings in order to prevent progress from being erased on driver state change.
		updatedDriverStanding.setDistanceTraveled(updatedDriverStanding.getDistanceTraveled() + this.distanceTraveledThisStep);
		updatedDriverStanding.setCurrentNode(raceTrack.obtainCurrentNode());
		
		//boolean isDamaged = false; // Flag showing if the car gets damaged this step.
		if (!completedRace) {
			// Before printing the result of this step,
			// roll to see what racing state the driver will be in next step.
			DriverState rolledState = rollDriverState();
			if (!this.equals(rolledState)) {
				if (rolledState instanceof Racing) {
					// Update distance values
					((Racing) rolledState).setStraightDistance(this.straightDistance);
					((Racing) rolledState).setCornerDistance(this.cornerDistance);
					((Racing) rolledState).setDistanceTraveledThisStep(this.distanceTraveledThisStep);
					
					// Check if the driver crashed
					if (rolledState instanceof Crashed) {
						updatedCar = this.crash(updatedCar);
						if (updatedCar.getDurability() <= 0) {
							// The car is wrecked, the driver is unable to continue.
							rolledState = new DNF(playerId, driverId);
						}
					}
				}
				else {
					return updatedDriverStanding;
				}
				updatedDriver.setState(rolledState);
			}
			else {
				updatedDriver.setState(this);
			}
		}
		else {
			// The driver has completed the race
			updatedDriverStanding.setTimeCompleted(raceEvent.getTimeElapsed());
			updatedDriver.completedRace();
		}
		
		// Update the driver and car within the player's inventory in the database
		updatedPlayer.getOwnedCars().update(updatedCar);
		updatedPlayer.getOwnedDrivers().update(updatedDriver);
		dbh.updateUser(updatedPlayer);
		
		//return stepResult;
		return updatedDriverStanding;
	}

	@Override
	public String completedRace(Driver driver) {
		// If in the Racing state, move to FinishedRace state.
		driver.setState(new FinishedRace(this.playerId, this.driverId, this.raceEventId));
		return this.driverStatus(driver);
	}

	@Override
	public String completedTraining(Driver driver) {
		// If in the Training state, move to FinishedTraining state.
		// Do nothing
		return "";
	}
	
	@Override
	public String driverStatus(Driver driver) {
		return driver.getName() + " (" + driver.getId() + ") is currently racing. Wait until the race has finished in order to interact with " + driver.getName() + ".";
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
		
		// Sets the race event, race track, and current node objects if needed.
		if (this.raceEvent == null) {
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
				//this.raceTrack = this.raceEvent.getRaceTrack();
				//this.currentNode = this.raceTrack.obtainFirstNode();
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
	 * @return car with damage applied to its components
	 */
	public Car crash(Car car) {
		Car updatedCar = car;
		
		int updatedDurability = 0;
		int amountOfComponentsToDamage = ThreadLocalRandom.current().nextInt(1, 4);
		System.out.print("Driver crashed! ");
		for (int i = 0; i < amountOfComponentsToDamage; i++) {
			ComponentType damagedComponent = ComponentType.random();
			int damage = ThreadLocalRandom.current().nextInt(0, 50);
			System.out.println(damage + " points of damage inflicted on the " + damagedComponent.toString().toLowerCase());
			switch (damagedComponent) {
				case CHASSIS:
					updatedDurability = updatedCar.getChassis().getDurability() - damage;
					if (updatedDurability < 0) {
						updatedDurability = 0;
					}
					updatedCar.getChassis().setDurability(updatedDurability);
					break;
				case ENGINE:
					updatedDurability = updatedCar.getEngine().getDurability() - damage;
					if (updatedDurability < 0) {
						updatedDurability = 0;
					}
					updatedCar.getEngine().setDurability(updatedDurability);
					break;
				case SUSPENSION:
					updatedDurability = updatedCar.getSuspension().getDurability() - damage;
					if (updatedDurability < 0) {
						updatedDurability = 0;
					}
					updatedCar.getSuspension().setDurability(updatedDurability);
					break;
				case TRANSMISSION:
					updatedDurability = updatedCar.getTransmission().getDurability() - damage;
					if (updatedDurability < 0) {
						updatedDurability = 0;
					}
					updatedCar.getTransmission().setDurability(updatedDurability);
					break;
				case WHEELS:
					updatedDurability = updatedCar.getWheels().getDurability() - damage;
					if (updatedDurability < 0) {
						updatedDurability = 0;
					}
					updatedCar.getWheels().setDurability(updatedDurability);
					break;
				default:
					break;
			}
		}
		
		return updatedCar;
	}
}