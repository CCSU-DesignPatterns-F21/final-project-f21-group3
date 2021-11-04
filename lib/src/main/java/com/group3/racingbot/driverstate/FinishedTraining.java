package com.group3.racingbot.driverstate;

import com.group3.racingbot.Driver;

/**
 * A state which the Driver moves into after completing a training session.
 * @author Nick Sabia
 *
 */
public class FinishedTraining extends Completed {
	private final Skill skill;
	
	/**
	 * Constructs a finished training state.
	 * @param driver allows this state to set the state of the driver and add skill points to the Driver.
	 * @param reward amount of skill points to add to the Driver
	 * @param skill the Driver skill to add the skill points to.
	 */
	public FinishedTraining(Driver driver, int reward, Skill skill) {
		super(driver, reward);
		this.skill = skill;
	}
	
	/**
	 * Retrieve the skill which the Driver trained.
	 * @return the skill
	 */
	public Skill getSkill() {
		return this.skill;
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
}