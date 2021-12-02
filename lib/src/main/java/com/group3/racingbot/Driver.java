package com.group3.racingbot;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.driverstate.DriverState;
import com.group3.racingbot.driverstate.Intensity;
import com.group3.racingbot.driverstate.Resting;
import com.group3.racingbot.driverstate.Skill;
import com.group3.racingbot.inventory.Unique;
import com.group3.racingbot.inventory.filter.SkillFilterable;
import com.group3.racingbot.standings.DriverStanding;

/**
 * Drives cars in races. A driver has their own stats that govern how well they do on the track.
 * @author Nick Sabia
 */
public class Driver implements Unique, SkillFilterable {
	private String id;
	@BsonIgnore
	private Player player;
	private String playerId;
	private DriverState state;
	private String name;
	private int composure;
	private int awareness;
	private int drafting;
	private int straights;
	private int cornering;
	private int recovery;
	private float payPercentage; // Percentage of money deducted from each event's cash prize.
	
	/**
	 * Constructs a new Driver. Base stats all start off at 10, the pay percentage starts at 0.15, and the driver's state is set to resting.
	 * @param name the name of the Driver
	 */
	@BsonCreator
	//@BsonProperty("name")
	public Driver(@BsonProperty("name") String name) {
		this.id = "";
		this.player = null;
		this.playerId = null;
		this.state = new Resting();
		this.name = name;
		this.composure = 10;
		this.awareness = 10;
		this.drafting = 10;
		this.straights = 10;
		this.cornering = 10;
		this.recovery = 10;
		this.payPercentage = (float) 0.15;
	}
	
	/**
	 * Retrieve the Driver's name. 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the Driver's name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieve the Driver's current state. 
	 * @return the state
	 */
	public DriverState getState() {
		return state;
	}

	/**
	 * Set the Driver's current state.
	 * @param state the state to set
	 */
	public void setState(DriverState state) {
		this.state = state;
	}

	/**
	 * Return the id which identifies this Driver
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id which identifies this Driver
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieve the Driver's composure skill.
	 * 
	 * The composure skill lowers the chance to roll into a debuff state during a race.
	 * @return the composure
	 */
	public int getComposure() {
		return composure;
	}

	/**
	 * Set the Driver's composure skill.
	 * 
	 * The composure skill lowers the chance to roll into a debuff state during a race.
	 * @param composure the composure to set
	 */
	public void setComposure(int composure) {
		this.composure = composure;
	}

	/**
	 * Retrieve the Driver's awareness skill.
	 * 
	 * The awareness skill lowers the chance of crashing during a race.
	 * @return the awareness
	 */
	public int getAwareness() {
		return awareness;
	}

	/**
	 * Set the Driver's awareness skill.
	 * 
	 * The awareness skill lowers the chance of crashing during a race.
	 * @param awareness the awareness to set
	 */
	public void setAwareness(int awareness) {
		this.awareness = awareness;
	}

	/**
	 * Retrieve the Driver's drafting skill.
	 * 
	 * The drafting skill increases the number of nodes you can travel when in the bottom half of the pack.
	 * @return the drafting
	 */
	public int getDrafting() {
		return drafting;
	}

	/**
	 * Set the Driver's drafting skill.
	 * 
	 * The drafting skill increases the number of nodes you can travel when in the bottom half of the pack.
	 * @param drafting the drafting to set
	 */
	public void setDrafting(int drafting) {
		this.drafting = drafting;
	}

	/**
	 * Retrieve the Driver's straights skill.
	 * 
	 * The straights skill increases the driver's ability to use the car's speed and acceleration to the fullest on straights.
	 * @return the straights
	 */
	public int getStraights() {
		return straights;
	}

	/**
	 * Set the Driver's straights skill.
	 * 
	 * The straights skill increases the driver's ability to use the car's speed and acceleration to the fullest on straights.
	 * @param straights the straights to set
	 */
	public void setStraights(int straights) {
		this.straights = straights;
	}

	/**
	 * Retrieve the Driver's cornering skill.
	 * 
	 * The cornering skill increases the driver's ability to use the car's handling and braking to the fullest on corners.
	 * @return the cornering
	 */
	public int getCornering() {
		return cornering;
	}

	/**
	 * Set the Driver's cornering skill.
	 * 
	 * The cornering skill increases the driver's ability to use the car's handling and braking to the fullest on corners.
	 * @param cornering the cornering to set
	 */
	public void setCornering(int cornering) {
		this.cornering = cornering;
	}

	/**
	 * Retrieve the Driver's recovery skill.
	 * 
	 * The recovery skill increases the odds of breaking out of the crashed state.
	 * @return the recovery
	 */
	public int getRecovery() {
		return recovery;
	}

	/**
	 * Set the Driver's recovery skill.
	 * 
	 * The recovery skill increases the odds of breaking out of the crashed state.
	 * @param recovery the recovery to set
	 */
	public void setRecovery(int recovery) {
		this.recovery = recovery;
	}

	/**
	 * Retrieve the Driver's pay percentage.
	 * 
	 * This is the percentage of winnings which the Driver will take for themselves after each reward for a race they participated in.
	 * @return the payPercentage
	 */
	public float getPayPercentage() {
		return payPercentage;
	}

	/**
	 * Set the Driver's pay percentage.
	 * 
	 * This is the percentage of winnings which the Driver will take for themselves after each reward for a race they participated in.
	 * @param payPercentage the payPercentage to set
	 */
	public void setPayPercentage(float payPercentage) {
		this.payPercentage = payPercentage;
	}
	
	/**
	 * Retrieve the Player who uses this Driver.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Set the Player who uses this Driver.
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Retrieve the player ID of the Player who uses this Driver.
	 * @return the player
	 */
	public String getPlayerId() {
		return playerId;
	}
	
	/**
	 * Set the player ID of the Player who uses this Driver.
	 * @param player the player to set
	 */
	public void setPlayerId(String id) {
		this.playerId = id;
	}
	
	/**
	 * Puts the Driver into a Resting state. If the Driver is currently registered for an event, this allows the Driver to back out of the event.
	 * @return context for what state the user may have left.
	 */
	public String rest() {
		return this.state.rest(this);
	}
	
	/**
	 * Puts the Driver into a training state to improve a skill.
	 * @param skillToTrain this is the skill which will be improved by the end of the training session.
	 * @param intensity governs how long the training will take and how large the reward for training will be.
	 * @return String containing contextual info about beginning training.
	 */
	public String beginTraining(Skill skillToTrain, Intensity intensity) {
		return this.state.beginTraining(this, skillToTrain, intensity);
	}
	
	/**
	 * Enters the driver into a racing state.
	 */
	public void beginRace() {
		this.state.beginRace(this);
	}
	
	/**
	 * Switch to the RacePending state. This is signing up the driver for an event.
	 * @param car the car the driver will use for the event.
	 * @param raceEvent the race event which the driver will participate in.
	 */
	public void signUpForRace(Car car, RaceEvent raceEvent) {
		System.out.println(this);
		this.state.signUpForRace(this, car, raceEvent);
	}
	
	/**
	 * When there is a reward to collect, the Driver will be in a Completed state. This will collect a reward and add the reward where it fits. For example, if the Driver finished training, then collecting the reward will add skill points to the Driver.
	 */
	public String collectReward() {
		return this.state.collectReward();
	}
	
	/**
	 * Allows the Driver to progress through the track.
	 * @param driverStanding holds info related to the driver's standing in the race event, such as pole position and distance traveled along the track.
	 * @return an updated driver standing which holds the current node the driver is in on the track.
	 */
	public DriverStanding raceStep(DriverStanding driverStanding) {
		return this.state.raceStep(this, driverStanding);
	}
	
	/**
	 * Once the Driver completes the race, this will move the Driver to a Completed state.
	 */
	public void completedRace() {
		this.state.completedRace(this);
	}
	
	/**
	 * Move to the finished training state upon training completion.
	 * @return String indicating that the user can now claim a reward.
	 */
	public String completedTraining() {
		return this.state.completedTraining(this);
	}
	
	/**
	 * Gives helpful information about the current state of the driver.
	 * @return a contextual string which offers helpful information about a particular driver.
	 */
	public String driverStatus() {
		return this.state.driverStatus(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + awareness;
		result = prime * result + composure;
		result = prime * result + cornering;
		result = prime * result + drafting;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(payPercentage);
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + recovery;
		result = prime * result + straights;
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Driver) {
			Driver otherObj = (Driver) other;
			
			if (this.getComposure() != otherObj.getComposure())
				return false;
			if (this.getAwareness() != otherObj.getAwareness())
				return false;
			if (this.getDrafting() != otherObj.getDrafting())
				return false;
			if (this.getStraights() != otherObj.getStraights())
				return false;
			if (this.getCornering() != otherObj.getCornering())
				return false;
			if (this.getRecovery() != otherObj.getRecovery())
				return false;
			if (this.getPayPercentage() != otherObj.getPayPercentage())
				return false;
			if (!this.getName().equals(otherObj.getName()))
				return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Driver's Name: " + this.getName() + " | Pay Percentage: " + this.getPayPercentage() + " | Current State: " + this.getState()
		     + "\nComposure: " + this.getComposure() + " | Awareness: " + this.getAwareness() + " | Drafting: " + this.getDrafting() + " | Straights: " + this.getStraights() + " | Cornering: " + this.getCornering() + " | Recovery: " + this.getRecovery();
	}
}