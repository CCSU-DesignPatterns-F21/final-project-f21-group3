/**
 * 
 */
package com.group3.racingbot.driverstate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.TrackNode;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

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
	
	private Driver driver;
	private Car car;
	private RaceTrack raceTrack;
	private RaceEvent raceEvent;
	private int straightDistance;
	private int cornerDistance;
	private int position;
	private TrackNode currentNode;
	
	public Racing(Driver driver, Car car, RaceEvent raceEvent) {
		this.driver = driver;
		this.car = car;
		this.raceTrack = raceEvent.getRaceTrack();
		this.raceEvent = raceEvent;
		this.straightDistance = 0;
		this.cornerDistance = 0;
		this.position = 1;
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
	 * Retrieve the race event.
	 * @return the raceEvent
	 */
	public RaceEvent getRaceEvent() {
		return raceEvent;
	}

	/**
	 * Set the race event.
	 * @param raceEvent the raceEvent to set
	 */
	public void setRaceEvent(RaceEvent raceEvent) {
		this.raceEvent = raceEvent;
	}

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
	public int getPosition() {
		return position;
	}

	/**
	 * Set the pole position of this driver in the race.
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

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
	public void beginRace() {
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
	abstract public void raceStep(Driver driver);

	@Override
	public void completedRace(Driver driver, RaceEvent raceEvent) {
		// If in the Racing state, move to FinishedRace state.
		this.getDriver().setState(new FinishedRace(this.getDriver(), raceEvent.getGrandPrize(), this.getPosition()));
	}

	@Override
	public void completedTraining(Driver driver) {
		// If in the Training state, move to FinishedTraining state.
		// Do nothing
	}
	
	/**
	 * Apply a random amount of damage to at least 1 component and at most 3 components of the car.
	 * @param car
	 */
	abstract public void crash(Car car);
}