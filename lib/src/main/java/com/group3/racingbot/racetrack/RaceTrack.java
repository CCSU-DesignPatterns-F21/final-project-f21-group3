/**
 * 
 */
package com.group3.racingbot.racetrack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.exceptions.RaceTrackEndException;

/**
 * A race track which Drivers can race and compete on.
 * @author Maciej Bregisz
 *
 */
public class RaceTrack {
	@BsonIgnore
	private List<TrackNode> trackNodes;
	private long seed;
	
	/**
	 * Construct a race track based on a seed.
	 * @param seed used to generate the track
	 */
	@BsonCreator
	public RaceTrack(@BsonProperty("seed") long seed) {
		this.trackNodes = new ArrayList<TrackNode>();
		this.seed = seed;
		generateRaceTrack(seed);
	}
	
	/**
	 * Set the track nodes which will make up the race track.
	 * @param tn
	 */
	public void setTrackNodes(List<TrackNode> tn) {
		this.trackNodes = tn;
	}
	
	/**
	 * Retrieve the track nodes which will make up the race track.
	 * @return track nodes in a list
	 */
	public List<TrackNode> getTrackNodes() {
		return trackNodes;
	}
	
	
	/**
	 * Retrieve the seed used in generating the race track.
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Set the seed used in generating the race track.
	 * @param seed the seed to set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * Retrieve the first track node in the track.
	 * @return first track node
	 */
	public TrackNode obtainFirstNode() {
		if (trackNodes.size() > 0) {
			return trackNodes.get(0);
		}
		System.out.println("obtainFirstNode: Cannot obtain first node. Track has no nodes.");
		return null;
	}
	
	/**
	 * Create a new random race track using a seed
	 * @param seed seed to form the race track
	 * @return List of track nodes which make up a race track
	 */
	public List<TrackNode> generateRaceTrack(long seed) {
		// Randomly pick a difficulty for the corner.
    	ThreadLocal<Random> rand = new ThreadLocal<Random>(); // Utilize threads with the Random class
    	rand.set(new Random());
    	rand.get().setSeed(seed); // Set the seed for the random number generator.
    	
		int maxNodes = (int) (Math.ceil((rand.get().nextInt(21) + 2) / 2) * 2); // Ensure that we have an even number of track nodes.
		int currentNode = 0;
		List<TrackNode> track = new ArrayList<TrackNode>();
		while (track.size() < maxNodes) {
		    if (currentNode % 2 == 0) {
		    	track.add(new StraightNode(this.seed));
		    }
		    else {
		    	int difficultyValue = rand.get().nextInt(3);
		    	Difficulty difficulty = null;
		    	switch (difficultyValue) {
		    		case 0:
		    			difficulty = Difficulty.EASY;
		    			break;
		    		case 1:
		    			difficulty = Difficulty.MEDIUM;
		    			break;
		    		case 2:
		    			difficulty = Difficulty.HARD;
		    			break;
		    		default:
		    			difficulty = Difficulty.EASY;
		    			break;
		    	}
		    	track.add(new CornerNode(this.seed, difficulty));
		    }
		    currentNode++;
		}

		//settings successors for CoR and trackNode ordering.
		track.get(0).setOrder(1);
		for(int i = 1; i < track.size();i++)
		{
			track.get(i).setOrder(i+1); // Set the order which this track node appears (starting from 1)
			track.get(i-1).setSuccessor(track.get(i)); // Set successor
		}
		setTrackNodes(track);
		return track;
	}
	
	/**
	 * Calculate and retrieve the total distance of the race track across all track nodes.
	 * @return the length of the track in distance.
	 */
	public int calculateTrackLength() {
		int totalDistance = 0;
		for (int i = 0, len = this.trackNodes.size(); i < len; i++) {
			totalDistance += this.trackNodes.get(i).getNodeLength();
		}
		return totalDistance;
	}
	
	/**
	 * Makes the driver advance forward along the track.
	 * @param distance distance to travel
	 */
	public void progressForward(Driver driver, int distance) {
		// CoR
		try {
			this.obtainFirstNode().progressForward(distance);
		} catch (RaceTrackEndException e) {
			System.out.println("Driver " + driver.getId() + " has finished the race!");
			driver.completedRace();
		}
	}
	
	/**
	 * Given a track node (from the DB), restore the state of the race track. This allows a driver to resume from a node they left off on. For example, if given a track node with an order of 3 and a distance remaining of 200, then this will set track nodes before it to have a distance remaining of 0 and set the distance remaining of the track node at order 3 to have a distance remaining of 200. 
	 * @param trackNode the track node to resume the race from.
	 * @return whether or not the resuming was successful.
	 */
	public boolean resumeProgressFromTrackNode(TrackNode trackNode) {
		if (trackNode.getSeed() == this.seed) {
			int index = 0;
			for (int len = trackNode.getOrder()-1; index < len; index++) {
				this.trackNodes.get(index).setDistanceRemaining(0);
			}
			this.trackNodes.set(index, trackNode);
			return true;
		}
		return false;
	}
	
	/**
	 * Retrieve the track node which the driver is currently on. If the driver has reached the end of the track, returns the last track node of the race track.
	 * @return the node which the driver is currently on.
	 */
	public TrackNode obtainCurrentNode() {
		return this.obtainFirstNode().obtainCurrentNode();
	}
	
	/**
	 * Retrieve how many nodes the race track has.
	 * @return the amount of nodes within the race track.
	 */
	public int size() {
		return this.trackNodes.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trackNodes == null) ? 0 : trackNodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof RaceTrack) {
			RaceTrack otherObj = (RaceTrack) other;
			
			Iterator<TrackNode> trackNodeIterator = trackNodes.iterator();
			Iterator<TrackNode> otherObjIterator = otherObj.getTrackNodes().iterator();
			while(trackNodeIterator.hasNext() && otherObjIterator.hasNext()) {
				// Check the equality of each track node.
				// If any differ, return false
				if (!(trackNodeIterator.next().equals(otherObjIterator.next()))) {
					return false;
				}
			}
			
			// If any one track is longer than the other, return false.
			if (trackNodeIterator.hasNext() || otherObjIterator.hasNext()) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String track = "";
		int totalLength = 0;
		for (int i = 0, len = this.trackNodes.size(); i < len; i++) {
			track += this.trackNodes.get(i).toString() + "\n";
			totalLength += this.trackNodes.get(i).getNodeLength();
		}
		return "**TRACK LENGTH:** " + totalLength + "ft" + "\n" + track;
	}
}