/**
 * 
 */
package com.group3.racingbot.driverstate;

import com.group3.racingbot.RaceEvent;

/**
 * An idle state for a Driver. A Driver may leave this state once their cooldown has expired.
 * @author Nick Sabia
 *
 */
public class Resting {
	/**
	 * Switch to the training state.
	 * @param skillToLearn The Driver skill to improve
	 */
	public void train(Skill skillToLearn) {
		
	}
	
	/**
	 * Switch to a racing state.
	 */
	public void race(RaceEvent raceEvent) {
		
	}
}
