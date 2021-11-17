package com.group3.racingbot.racetrack;
import java.util.concurrent.ThreadLocalRandom;

import com.group3.racingbot.exceptions.RaceTrackEndException;

/**
 * A portion of a race track which can either be a corner or a straight.
 * @author Nick Sabia
 *
 */
public abstract class TrackNode {
	private TrackNode successor;
	private int nodeLength;
	private int distanceRemaining;
	
	/**
	 * Construct a piece of a race track.
	 */
	public TrackNode() {
		this.successor = null;
		this.nodeLength = ThreadLocalRandom.current().nextInt(1, 2000);
		this.distanceRemaining = this.nodeLength;
	}

	/**
	 * @return the successor
	 */
	public TrackNode getSuccessor() {
		return successor;
	}

	/**
	 * @param successor the successor to set
	 */
	public void setSuccessor(TrackNode successor) {
		this.successor = successor;
	}

	/**
	 * @return the nodeLength
	 */
	public int getNodeLength() {
		return nodeLength;
	}

	/**
	 * @param nodeLength the nodeLength to set
	 */
	public void setNodeLength(int nodeLength) {
		this.nodeLength = nodeLength;
	}

	/**
	 * @return the distanceRemaining
	 */
	public int getDistanceRemaining() {
		return distanceRemaining;
	}

	/**
	 * @param distanceRemaining the distanceRemaining to set
	 */
	public void setDistanceRemaining(int distanceRemaining) {
		this.distanceRemaining = distanceRemaining;
	}
	
	/**
	 * Subtract some amount from the distance covered of this track node (and others within the Chain of Responsibility if this track node has been traversed completely)
	 * @param distance The distance to travel along the track node
	 * @throws RaceTrackEndException no more track nodes, so the driver has reached the end
	 */
	abstract protected void progressForward(int distance) throws RaceTrackEndException;
	
	/**
	 * Prints what node the Driver is on out of the total number of track nodes.
	 * @return the current node the Driver is on and the distance left in that node.
	 * @throws RaceTrackEndException 
	 */
	public String currentProgressToString(RaceTrack raceTrack, int nodeCount) throws RaceTrackEndException {
		if (this.getDistanceRemaining() <= 0) {
			if (this.getSuccessor() != null) {
				return this.getSuccessor().currentProgressToString(raceTrack, nodeCount + 1);
			}
			else {
				throw new RaceTrackEndException("The driver has reached the end of the track");
			}
		}
		else {
			return "Node " + nodeCount + " of " + raceTrack.getTrackNodes().size();
		}
	}
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object other);
	
	@Override
	public abstract String toString();
}