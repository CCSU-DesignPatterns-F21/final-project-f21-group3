/**
 * 
 */
package com.group3.racingbot.racetrack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A race track which Drivers can race and compete on.
 * @author Maciej Bregisz
 *
 */
public class RaceTrack {
	private List<TrackNode> trackNodes;
	
	/**
	 * Construct a race track
	 */
	public RaceTrack() {
		this.trackNodes = new ArrayList<TrackNode>();
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
	public List<TrackNode> getTrackNodes(){
		return trackNodes;
	}
	
	/**
	 * Retrieve the first track node in the track.
	 * @return first track node
	 */
	public TrackNode getFirstNode() {
		return trackNodes.get(0);
	}
	
	/**
	 * Create a new random race track
	 * @param nodes pieces of the race track
	 * @return List of track nodes which make up a race track
	 */
	public List<TrackNode> generateRaceTrack(int nodes) {
		int maxNodes = Math.round(nodes / 2) * 2;
		int currentNode = 0;
		List<TrackNode> track = new ArrayList<TrackNode>();
		while (track.size() < maxNodes) {
		    if (currentNode % 2 == 0) {
		    	track.add(new StraightNode());
		    }
		    else {
		    	// Randomly pick a difficulty for the corner.
		    	int rand = ThreadLocalRandom.current().nextInt(0, 2);
		    	Difficulty difficulty = null;
		    	switch (rand) {
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
		    	track.add(new CornerNode(difficulty));
		    }
		    currentNode++;
		}
		//settings successors for CoR
		for(int i = 1; i < track.size();i++)
		{
			track.get(i-1).setSuccessor(track.get(i));
		}
		setTrackNodes(track);
		return track;
		
	}
	
	/**
	 * Makes the driver advance forward along the track.
	 * @param distance distance to travel
	 */
	public void progressForward(int distance) {
		// CoR
		
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
		return "RaceTrack [trackNodes=" + trackNodes + "]";
	}
}
