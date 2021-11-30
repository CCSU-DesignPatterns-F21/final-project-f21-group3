package com.group3.racingbot.racetrack;
import java.util.Random;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * A portion of a race track which can either be a corner or a straight.
 * @author Nick Sabia
 *
 */
public abstract class TrackNode {
	@BsonIgnore
	private TrackNode successor;
	private int nodeLength;
	private int distanceRemaining;
	private long seed;
	private int order;
	
	/**
	 * Construct a piece of a race track.
	 */
	@BsonCreator
	public TrackNode(@BsonProperty("seed") long seed) {
		// Randomly pick a difficulty for the corner.
    	ThreadLocal<Random> rand = new ThreadLocal<Random>(); // Utilize threads with the Random class
    	rand.set(new Random());
    	rand.get().setSeed(seed); // Set the seed for the random number generator.
    	
		this.successor = null;
		this.nodeLength = rand.get().nextInt(2000) + 1;
		this.distanceRemaining = this.nodeLength;
		this.seed = seed;
		this.order = 0;
	}

	/**
	 * Retrieve the (index + 1) of the track node. In other words, the number representing the order with which the track node appears on the track.
	 * @return the index
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Set the (index + 1) of the track node. In other words, the number representing the order with which the track node appears on the track.
	 * @param index the index to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Retrieve the seed used to generate this track node.
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Set the seed used to generate this track node.
	 * @param seed the seed to set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
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
	 * Retrieve the track node which the driver is currently on. If the driver has reached the end of the track, returns the last track node of the race track.
	 * @return the node which the driver is currently on.
	 */
	protected TrackNode obtainCurrentNode() {
		if (this.successor != null && this.distanceRemaining <= 0) {
			return this.successor.obtainCurrentNode();
		}
		else {
			return this;
		}
	}
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object other);
	
	@Override
	public abstract String toString();
}