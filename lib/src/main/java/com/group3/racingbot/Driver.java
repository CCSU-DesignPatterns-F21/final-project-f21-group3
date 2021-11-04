package com.group3.racingbot;

import com.group3.racingbot.driverstate.DriverState;
import com.group3.racingbot.driverstate.Intensity;
import com.group3.racingbot.driverstate.Resting;
import com.group3.racingbot.driverstate.Skill;

/**
 * Drives cars in races. A driver has their own stats that govern how well they do on the track.
 * @author Nick Sabia
 *
 */
public class Driver {
	private Player player;
	private DriverState state;
	private RaceEvent lastRegisteredEvent;
	private String name;
	private int composure;
	private int awareness;
	private int drafting;
	private int straights;
	private int cornering;
	private int recovery;
	private float payPercentage; // Percentage of money deducted from each event's cash prize.
	private long cooldown; // If training or racing is performed, then this is the time to wait until you can use this Driver again.
	
	/**
	 * Creates a Driver and assigns it to a player. Base stats all start off at 10 and the pay percentage starts at 0.15.
	 * @param player the Player who owns this Driver
	 * @param name the name of the Driver
	 */
	public Driver(Player player, String name) {
		this.player = player;
		this.state = new Resting();
		this.name = name;
		this.composure = 10;
		this.awareness = 10;
		this.drafting = 10;
		this.straights = 10;
		this.cornering = 10;
		this.recovery = 10;
		this.payPercentage = (float) 0.15;
		this.cooldown = 0;
	}
	
	/**
	 * Creates a Driver. Base stats all start off at 10 and the pay percentage starts at 0.15.
	 * @param name the name of the Driver
	 */
	public Driver(String name) {
		this.player = null;
		this.name = name;
		this.composure = 10;
		this.awareness = 10;
		this.drafting = 10;
		this.straights = 10;
		this.cornering = 10;
		this.recovery = 10;
		this.payPercentage = (float) 0.15;
		this.cooldown = 0;
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
	 * Retrieve the last event that this Driver has registered for.
	 * @return the lastRegisteredEvent
	 */
	public RaceEvent getLastRegisteredEvent() {
		return lastRegisteredEvent;
	}

	/**
	 * Set the last event that this Driver has registered for.
	 * @param lastRegisteredEvent the lastRegisteredEvent to set
	 */
	public void setLastRegisteredEvent(RaceEvent lastRegisteredEvent) {
		this.lastRegisteredEvent = lastRegisteredEvent;
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
	 * The recovery skill decreases time lost during a race due to crashing.
	 * @return the recovery
	 */
	public int getRecovery() {
		return recovery;
	}

	/**
	 * Set the Driver's recovery skill.
	 * 
	 * The recovery skill decreases time lost during a race due to crashing.
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
	 * Retrieve the Driver's cooldown.
	 * 
	 * If training or racing is performed, then the cooldown is the time to wait until you can use this Driver again.
	 * @return the cooldown
	 */
	public long getCooldown() {
		return cooldown;
	}

	/**
	 * Set the Driver's cooldown.
	 * 
	 * If training or racing is performed, then the cooldown is the time to wait until you can use this Driver again.
	 * @param cooldown the cooldown to set
	 */
	public void setCooldown(long cooldown) {
		this.cooldown = cooldown;
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
	 * Puts the Driver into a Resting state.
	 */
	public void rest() {
		this.state = new Resting();
	}
	
	/**
	 * Puts the Driver into a training state to improve a skill.
	 * @param driver
	 * @param skillToTrain
	 * @param intensity
	 */
	public void beginTraining(Driver driver, Skill skillToTrain, Intensity intensity) {
		this.getState().beginTraining(driver, skillToTrain, intensity);
	}
	
	/**
	 * Switch to the RacePending state.
	 */
	public void signUpForRace(Car car, RaceEvent raceEvent) {
		this.getState().signUpForRace(this, car, raceEvent);
	}
	
	/**
	 * When there is a reward to collect, the Driver will be in a Completed state. This will collect a reward and add the reward where it fits. For example, if the Driver finished training, then collecting the reward will add skill points to the Driver.
	 */
	public void collectReward() {
		this.getState().collectReward();
	}
	
	/**
	 * If the Driver is currently registered for an event, this allows the Driver to back out of the event.
	 * @return the success of race withdrawal
	 */
	public boolean withdrawFromRace() {
		return this.getState().withdrawFromRace(this);
	}
	
	/**
	 * Allows the Driver to progress through the track.
	 */
	public void raceRoll() {
		this.getState().raceRoll(this);
	}
	
	/**
	 * Once the Driver completes the race, this will move the Driver to a Completed state.
	 */
	public void completedRace() {
		this.getState().completedRace(this, this.getLastRegisteredEvent());
	}
	
	/**
	 * Once the Driver completes a training session, this will move the Driver to a Completed state.
	 */
	public void completedTraining() {
		this.getState().completedTraining(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + awareness;
		result = prime * result + composure;
		result = prime * result + (int) (cooldown ^ (cooldown >>> 32));
		result = prime * result + cornering;
		result = prime * result + drafting;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(payPercentage);
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + recovery;
		result = prime * result + straights;
		return result;
	}
	
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
			if (this.getCooldown() != otherObj.getCooldown())
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
		return "Driver's Name: " + this.getName() + " | Pay Percentage: " + this.getPayPercentage() + " | Current State: " + this.getState() + " | Cooldown: " + this.getCooldown()
		     + "\nComposure: " + this.getComposure() + " | Awareness: " + this.getAwareness() + " | Drafting: " + this.getDrafting() + " | Straights: " + this.getStraights() + " | Cornering: " + this.getCornering() + " | Recovery: " + this.getRecovery();
	}
}