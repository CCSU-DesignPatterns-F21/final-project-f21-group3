package com.group3.racingbot.driverstate;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;

/**
 * A state which the Driver remains in once initiating a training session. Only when the training cooldown expires can the Driver move to another state.
 * @author Nick Sabia
 *
 */
@BsonDiscriminator(value="Training", key="_cls")
public class Training implements DriverState {
	private final Driver driver;
	private Skill skillToTrain;
	private int trainingReward;
	
	/**
	 * Constructs the training state.
	 * @param driver allow this state to change the driver's state.
	 * @param skillToTrain this is what skill will be added to after training completes.
	 * @param intensity governs the length of the training session and the size of the reward.
	 */
	public Training(Driver driver, Skill skillToTrain, Intensity intensity) {
		this.driver = driver;
		this.skillToTrain = skillToTrain;
		switch (intensity) {
			case LIGHT:
				this.trainingReward = 1;
				break;
			case MEDIUM:
				this.trainingReward = 3;
				break;
			case INTENSE:
				this.trainingReward = 5;
				break;
		}
	}

	/**
	 * Retrieve the driver which is undergoing training.
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Retrieve the skill that this driver is training for.
	 * @return the skillToTrain
	 */
	public Skill getSkillToTrain() {
		return skillToTrain;
	}

	/**
	 * Set the skill that this driver is training for.
	 * @param skillToTrain the skillToTrain to set
	 */
	public void setSkillToTrain(Skill skillToTrain) {
		this.skillToTrain = skillToTrain;
	}

	/**
	 * Retrieve the training reward.
	 * @return the trainingReward
	 */
	public int getTrainingReward() {
		return trainingReward;
	}

	/**
	 * Set the training reward.
	 * @param trainingReward the trainingReward to set
	 */
	public void setTrainingReward(int trainingReward) {
		this.trainingReward = trainingReward;
	}

	@Override
	public void rest() {
		// Cancels the training session.
		// Do nothing
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
	public void raceStep(Driver driver) {
		// If in Racing state, calculate the distance which the driver can travel on straights and corners. 
		// Next, randomize if a crash will occur this roll. If so, add to the idleTime and damage the Car. Otherwise, continue.
		// Finally, if the Driver's on a straight node then travel the straight distance + Math.floor(cornerDistance/3). Otherwise, vice versa.
		
		// Do nothing
	}

	@Override
	public void completedRace(Driver driver, RaceEvent raceEvent) {
		// If in the Racing state, move to FinishedRace state.
		// Do nothing
	}

	@Override
	public void completedTraining(Driver driver) {
		// Move to FinishedTraining state so long as the cooldown has expired.
		Date d = new Date();
		long now = d.getTime();
		if (now > this.getDriver().getCooldown()) {
			this.getDriver().setState(new FinishedTraining(this.getDriver(), this.getTrainingReward(), this.getSkillToTrain()));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + skillToTrain.getSkill();
		result = prime * result + trainingReward;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Training) {
			Training otherObj = (Training) other;
			
			if (!(this.getDriver().equals(otherObj.getDriver())))
				return false;
			if (this.getSkillToTrain() != otherObj.getSkillToTrain())
				return false;
			if (this.getTrainingReward() != otherObj.getTrainingReward())
				return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Training [driver=" + driver + ", skillToTrain=" + skillToTrain + ", trainingReward=" + trainingReward + "]";
	}
	
}
