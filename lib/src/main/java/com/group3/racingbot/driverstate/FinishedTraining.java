package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.inventory.NotFoundException;

/**
 * A state which the Driver moves into after completing a training session.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="FinishedTraining", key="_cls")
public class FinishedTraining extends Completed {
	private final Skill skill;
	private final int reward;
	
	/**
	 * Constructs a finished training state.
	 * @param driver allows this state to set the state of the driver and add skill points to the Driver.
	 * @param reward amount of skill points to add to the Driver
	 * @param skill the Driver skill to add the skill points to.
	 */
	@BsonCreator
	public FinishedTraining(@BsonProperty("playerId") String playerId, @BsonProperty("driverId") String driverId, @BsonProperty("reward") int reward, @BsonProperty("skill") Skill skill) {
		super(playerId, driverId);
		this.reward = reward;
		this.skill = skill;
	}
	
	/**
	 * Retrieve the skill which the Driver trained.
	 * @return the skill
	 */
	public Skill getSkill() {
		return this.skill;
	}
	
	/**
	 * Retrieve the amount of skill points which the Driver will earn.
	 * @return the reward
	 */
	public int getReward() {
		return this.reward;
	}
	
	@Override
	public void collectReward() {
		this.refreshFromDB();
		
		DBHandler dbh = DBHandler.getInstance();
		Player updatedPlayer = super.getPlayer();
		Driver updatedDriver = super.getDriver();
		
		// Reward the player with skill points toward the skill the Driver trained for
		int currentSkillPoints = 0;
		switch (this.getSkill()) {
		 case COMPOSURE: 
			 currentSkillPoints = updatedDriver.getComposure();
			 updatedDriver.setComposure(currentSkillPoints + this.getReward());
			 break;
		 case AWARENESS: 
			 currentSkillPoints = updatedDriver.getAwareness();
			 updatedDriver.setAwareness(currentSkillPoints + this.getReward());
			 break;
		 case DRAFTING: 
			 currentSkillPoints = updatedDriver.getDrafting();
			 updatedDriver.setDrafting(currentSkillPoints + this.getReward());
			 break;
		 case STRAIGHTS: 
			 currentSkillPoints = updatedDriver.getStraights();
			 updatedDriver.setStraights(currentSkillPoints + this.getReward());
			 break;
		 case CORNERING: 
			 currentSkillPoints = updatedDriver.getCornering();
			 updatedDriver.setCornering(currentSkillPoints + this.getReward());
			 break;
		 case RECOVERY: 
			 currentSkillPoints = updatedDriver.getRecovery();
			 updatedDriver.setRecovery(currentSkillPoints + this.getReward());
			 break;
		}
		
		// Return the driver to a resting state and save the skill point reward
		updatedDriver.setState(new Resting());
		updatedPlayer.getOwnedDrivers().update(updatedDriver);
		dbh.updateUser(updatedPlayer);
	}
	
	@Override
	public boolean refreshFromDB() {
		DBHandler dbh = DBHandler.getInstance();
		
		// Verify that the driver still exists.
		if (super.getDriver() == null) {
			// The server could have restarted and the instance of this class may only hold the id's
			// In this case, retrieve all necessary info from the database.
			super.setPlayer(dbh.getPlayer(super.getPlayerId()));
			try {
				if (super.getPlayer() != null) {
					super.setDriver(super.getPlayer().getOwnedDrivers().getById(super.getDriverId()));
				}
				else {
					System.out.println("Player " + super.getPlayerId() + " is missing from Database. Attempting to put Driver " + super.getPlayerId() + " into a resting state...");
				}
			}
			catch(NotFoundException e) {
				// Driver is missing from DB
				System.out.println("Driver " + super.getPlayerId() + " is missing from Database. Attempting to put Driver " + super.getPlayerId() + " into a resting state...");
			}
			
			if (super.getPlayer() == null || super.getDriver() == null) {
				// Put the driver into a resting state
				if (dbh.updateDriverStateInDB(super.getPlayerId(), super.getDriverId(), new Resting())) {
					System.out.println("Driver " + super.getDriverId() + " is now in a resting state.");
				}
				else {
					System.out.println("Unable to put Driver " + super.getDriverId() + " into a resting state.");
				}

				return false;
			}
		}
		return true;
	}
	
	@Override
	public String driverStatus(Driver driver) {
		return driver.getName() + " (" + driver.getId() + ") has completed training for " + this.skill.toString().toLowerCase() + ". You can now claim the skill reward. \n**Claim a reward**\n!r debug claim";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((skill == null) ? 0 : skill.hashCode());
		result *= reward;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof FinishedTraining) {
			FinishedTraining otherObj = (FinishedTraining) other;
			
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
		return "FinishedTraining [skill=" + skill + "]";
	}
}