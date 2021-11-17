package com.group3.racingbot.driverstate;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.group3.racingbot.Driver;

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
	public FinishedTraining(Driver driver, int reward, Skill skill) {
		super(driver);
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
		// Reward the player with skill points toward the skill the Driver trained for
		int currentSkillPoints = 0;
		switch (this.getSkill()) {
		 case COMPOSURE: 
			 currentSkillPoints = this.getDriver().getComposure();
			 this.getDriver().setComposure(currentSkillPoints + this.getReward());
			 break;
		 case AWARENESS: 
			 currentSkillPoints = this.getDriver().getAwareness();
			 this.getDriver().setAwareness(currentSkillPoints + this.getReward());
			 break;
		 case DRAFTING: 
			 currentSkillPoints = this.getDriver().getDrafting();
			 this.getDriver().setDrafting(currentSkillPoints + this.getReward());
			 break;
		 case STRAIGHTS: 
			 currentSkillPoints = this.getDriver().getStraights();
			 this.getDriver().setStraights(currentSkillPoints + this.getReward());
			 break;
		 case CORNERING: 
			 currentSkillPoints = this.getDriver().getCornering();
			 this.getDriver().setCornering(currentSkillPoints + this.getReward());
			 break;
		 case RECOVERY: 
			 currentSkillPoints = this.getDriver().getRecovery();
			 this.getDriver().setRecovery(currentSkillPoints + this.getReward());
			 break;
		}
		
		// Return the driver to a resting state
		this.getDriver().setState(new Resting());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((skill == null) ? 0 : skill.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof FinishedTraining) {
			FinishedTraining otherObj = (FinishedTraining) other;
			
			if (this.getSkill() != otherObj.getSkill())
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