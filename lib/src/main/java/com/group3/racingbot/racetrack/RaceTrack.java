/**
 * 
 */
package com.group3.racingbot.racetrack;

import java.util.ArrayList;
import java.util.List;

/**
 * A race track which Drivers can race and compete on.
 * @author Maciej Bregisz
 *
 */
public class RaceTrack {
	private List<TrackNode> trackNodes;
	
	public RaceTrack() {
		this.trackNodes = new ArrayList<TrackNode>();
	}
	
	public void setTrackNodes(List<TrackNode> tn) {
		this.trackNodes = tn;
	}
	public List<TrackNode> getTrackNodes(){
		return trackNodes;
	}
	public TrackNode getFirstNode() {
		return trackNodes.get(0);
	}
	public List<TrackNode> generateRaceTrack(int nodes) {
		
		int maxNodes = Math.round(nodes / 2) * 2;
		int currentNode = 0;
		List<TrackNode> track = new ArrayList<TrackNode>();
		while (track.size() < maxNodes) {
		    if (currentNode % 2 == 0) {
		    	track.add(new StraightNode());
		    	currentNode++;
		    }
		    else {
		    	track.add(new CornerNode());
		    	currentNode++;
		    }
		}
		//settings successors for CoR
		for(int i = 1; i < track.size();i++)
		{
			track.get(i-1).setSuccessor(track.get(i));
		}
		setTrackNodes(track);
		return track;
		
	}
	public void progressForward(int distance) {
		// CoR
	}
	
}
