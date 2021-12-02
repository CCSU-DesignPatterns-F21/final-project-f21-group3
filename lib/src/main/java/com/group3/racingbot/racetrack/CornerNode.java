/**
 * 
 */
package com.group3.racingbot.racetrack;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * Corner section of a race track
 * @author Nick Sabia
 *
 */
public class CornerNode extends TrackNode {
	private Difficulty difficulty;
	private double multiplier;

	/**
	 * Construct a corner node
	 * @param difficulty governs how quickly a driver can navigate through this track node
	 * @param seed governs the creation of the track node
	 */
	@BsonCreator
	public CornerNode(@BsonProperty("seed") long seed, @BsonProperty("difficulty") Difficulty difficulty) {
		super(seed);
		this.difficulty = difficulty;
		switch (difficulty) {
			case EASY:
				this.multiplier = 1.0;
				break;
			case MEDIUM:
				this.multiplier = 0.75;
				break;
			case HARD:
				this.multiplier = 0.5;
				break;
		}
	}
	
	/**
	 * Retrieve the multiplier which will effect the speed at which the Driver can travel through.
	 * @return the multiplier the multiplier which affects how quickly drivers can traverse this track node.
	 */
	public double getMultiplier() {
		return multiplier;
	}

	/**
	 * Set the multiplier which will effect the speed at which the Driver can travel through.
	 * @param multiplier the multiplier to set
	 */
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * Retrieve the rating for how difficult the corner is
	 * @return the difficulty of the corner
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Set the rating for how difficult the corner is
	 * @param difficulty the difficulty of the corner to set
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	@Override
	public void progressForward(int distance) throws RaceTrackEndException {
		if ((super.getDistanceRemaining() - distance) >= 0) {
			// There was more of the track node left to cover.
			super.setDistanceRemaining(super.getDistanceRemaining() - (int) (distance * multiplier));
		}
		else {
			// The driver has covered the entire distance of this track node.
			// Deducting part of the full amount.
			int amountNotDeducted = distance - super.getDistanceRemaining();
			try {
				if (super.getSuccessor() != null) {
					super.getSuccessor().progressForward(amountNotDeducted);
				}
				else {
					throw new RaceTrackEndException("The driver has reached the end of the track");
				}
			} 
			catch (RaceTrackEndException e) {
				super.setDistanceRemaining(0);
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			super.setDistanceRemaining(0);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + difficulty.getDifficulty() + super.getNodeLength();
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof CornerNode) {
			CornerNode otherObj = (CornerNode) other;
			
			if (this.getNodeLength() != otherObj.getNodeLength())
				return false;
			if (this.getDifficulty() != otherObj.getDifficulty())
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "**Corner:** " + super.getNodeLength() + "ft long | difficulty: " + this.difficulty;
	}
}