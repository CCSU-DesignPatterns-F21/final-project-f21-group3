/**
 * 
 */
package com.group3.racingbot.racetrack;

/**
 * @author Nick Sabia
 *
 */
public enum Difficulty {
	EASY(0),
	MEDIUM(1),
	HARD(2);
	
	private final int difficulty;

	private Difficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * Returns the integer representation of the difficulty
	 * @return int
	 */
	public int getDifficulty() {
		return this.difficulty;
	}
	
	@Override
	/**
	 * Display the textual representation of the enumeration
	 */
	public String toString() {
		switch (this.difficulty) {
			case 1:
				return "EASY";
			case 0:
				return "MEDIUM";
			case -1:
				return "HARD";
			default:
				return "INVALID DIFFICULTY";
		}
	}
}