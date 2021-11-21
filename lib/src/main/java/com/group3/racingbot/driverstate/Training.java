package com.group3.racingbot.driverstate;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.Car;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.standings.DriverStanding;

/**
 * A state which the Driver remains in once initiating a training session. Only when the training cooldown expires can the Driver move to another state.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="Training", key="_cls")
public class Training implements DriverState {
	private String playerId;
	private String driverId;
	@BsonIgnore
	private Driver driver;
	private Skill skillToTrain;
	private Intensity intensity;
	private int trainingReward;
	private long cooldown;
	
	/**
	 * Constructs the training state.
	 * @param driver allow this state to change the driver's state.
	 * @param skillToTrain this is what skill will be added to after training completes.
	 * @param intensity governs the length of the training session and the size of the reward.
	 */
	@BsonCreator
	public Training(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("skillToTrain") Skill skillToTrain, @BsonProperty("intensity") Intensity intensity) {
		final long THIRTY_MINS = 1800000; // 30 minutes in milliseconds.
		final Date d = new Date();
		final long now = d.getTime();
		this.driver = null;
		this.playerId = playerId;
		this.driverId = driverId;
		this.skillToTrain = skillToTrain;
		this.intensity = intensity;
		switch (intensity) {
			case LIGHT:
				this.trainingReward = 1;
				this.cooldown = now + THIRTY_MINS;
				break;
			case MEDIUM:
				this.trainingReward = 3;
				this.cooldown = now + (THIRTY_MINS * 2);
				break;
			case INTENSE:
				this.trainingReward = 5;
				this.cooldown = now + (THIRTY_MINS * 3);
				break;
			default:
				this.trainingReward = 1;
				this.cooldown = now + THIRTY_MINS;
				break;
		}
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
	 * Retrieve the intensity of the training which the driver is enduring.
	 * @return the intensity of the training session
	 */
	public Intensity getIntensity() {
		return intensity;
	}

	/**
	 * Set the intensity of the training which the driver is enduring.
	 * @param intensity the intensity of the training session to set
	 */
	public void setIntensity(Intensity intensity) {
		this.intensity = intensity;
	}

	/**
	 * Retrieve the player id of the player who is training their driver.
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the player id of the player who is training their driver.
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * Retrieve the driver id of the driver being trained.
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}

	/**
	 * Set the driver id of the driver being trained.
	 * @param driverId the driverId to set
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	/**
	 * Retrieve the driver which is undergoing training.
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}
	
	/**
	 * Set the driver which is undergoing training.
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
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
	public String rest(Driver driver) {
		DBHandler dbh = DBHandler.getInstance();
		// Cancels the training session.
		dbh.updateDriverStateInDB(playerId, driverId, new Resting());
		driver.setState(new Resting());
		return "Ended a traning session which " + driver.getName() + " (" + driver.getId() + ") was in the middle of. The driver will not recieve the training reward for this session.";
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
	public DriverStanding raceStep(Driver driver, DriverStanding driverStanding) {
		// If in Racing state, calculate the distance which the driver can travel on straights and corners. 
		// Next, randomize if a crash will occur this roll. If so, add to the idleTime and damage the Car. Otherwise, continue.
		// Finally, if the Driver's on a straight node then travel the straight distance + Math.floor(cornerDistance/3). Otherwise, vice versa.
		
		// Do nothing
		return null;
	}

	@Override
	public void completedRace(Driver driver) {
		// If in the Racing state, move to FinishedRace state.
		// Do nothing
	}

	@Override
	public void completedTraining(Driver driver) {
		DBHandler dbh = DBHandler.getInstance();
		refreshFromDB();
		
		// Move to FinishedTraining state so long as the cooldown has expired.
		Date d = new Date();
		long now = d.getTime();
		if (now > this.cooldown) {
			if (dbh.updateDriverStateInDB(this.playerId, this.driverId, new FinishedTraining(this.playerId, this.driverId, this.getTrainingReward(), this.getSkillToTrain()))) {
				this.driver.setState(new FinishedTraining(this.playerId, this.driverId, this.getTrainingReward(), this.getSkillToTrain()));
				System.out.println("Driver " + this.driverId + " has completed training for " + getSkillToTrain().toString() + ".");
			}
			else {
				System.out.println("Unable to set the state of Driver " + this.driverId + ".");
			}
		}
	}
	
	@Override
	public boolean refreshFromDB() {
		DBHandler dbh = DBHandler.getInstance();
		
		// Verify that the driver still exists.
		if (this.driver == null) {
			// The server could have restarted and the instance of this class may only hold the id's
			// In this case, retrieve all necessary info from the database.
			Player p = dbh.getPlayer(this.playerId);
			try {
				if (p != null) {
					this.driver = p.getOwnedDrivers().getById(this.driverId);
				}
				else {
					System.out.println("Player " + this.playerId + " is missing from Database. Attempting to put Driver " + this.playerId + " into a resting state...");
				}
			}
			catch(NotFoundException e) {
				// Driver is missing from DB
				System.out.println("Driver " + this.playerId + " is missing from Database. Attempting to put Driver " + this.playerId + " into a resting state...");
			}
			
			if (this.driver == null) {
				// Put the driver into a resting state
				if (dbh.updateDriverStateInDB(this.playerId, this.driverId, new Resting())) {
					System.out.println("Driver " + this.driverId + " is now in a resting state.");
				}
				else {
					System.out.println("Unable to put Driver " + this.driverId + " into a resting state.");
				}

				return false;
			}
		}
		return true;
	}
	
	@Override
	public String driverStatus(Driver driver) {
		return driver.getName() + " (" + driver.getId() + ") is currently training. You may cancel their training session, but " + driver.getName() + " will not earn a skill reward as a result.\n**Cancel**\n!r debug driver train withdraw";
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