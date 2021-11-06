/**
 * 
 */
package com.group3.racingbot.racetrack;

import com.group3.racingbot.exceptions.RaceTrackEndException;

/**
 * @author Nick Sabia
 *
 */
public class StraightNode extends TrackNode {
	
	/**
	 * Construct a straight node
	 */
	public StraightNode() {
		super();
	}
	
	@Override
	public void progressForward(int distance) throws RaceTrackEndException {
		if ((super.getDistanceRemaining() - distance) >= 0) {
			// There was more of the track node left to cover.
			super.setDistanceRemaining(super.getDistanceRemaining() - distance);
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
		result = prime * result + super.getNodeLength();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof StraightNode) {
			StraightNode otherObj = (StraightNode) other;
			
			if (this.getNodeLength() != otherObj.getNodeLength())
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Straight: distance " + super.getNodeLength();
	}
}
