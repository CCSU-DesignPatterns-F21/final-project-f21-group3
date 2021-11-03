package com.group3.racingbot.driverstate;

/**
 * Represents skills which a driver possesses.
 * @author Nick Sabia
 *
 */
public enum Skill {
	COMPOSURE(0),
	AWARENESS(1),
	DRAFTING(2),
	STRAIGHTS(3),
	CORNERING(4),
	RECOVERY(5);
	
	private final int skill;

	private Skill(int skill) {
		this.skill = skill;
	}
	
	/**
	 * Returns the integer representation of the skill
	 * @return int
	 */
	public int getSkill() {
		return this.skill;
	}
	
	@Override
	/**
	 * Display the textual representation of the enumeration
	 */
	public String toString() {
		switch (this.skill) {
			case 0:
				return "COMPOSURE";
			case 1:
				return "AWARENESS";
			case 2:
				return "DRAFTING";
			case 3:
				return "STRAIGHTS";
			case 4:
				return "CORNERING";
			case 5:
				return "RECOVERY";
			default:
				return "INVALID SKILL";
		}
	}
}
