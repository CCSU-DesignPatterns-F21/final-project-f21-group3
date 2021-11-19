/**
 * 
 */
package com.group3.racingbot.standings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import com.group3.racingbot.Driver;
import com.group3.racingbot.driverstate.FinishedRace;
import com.group3.racingbot.driverstate.Racing;
import com.group3.racingbot.inventory.Iterator;
//import com.group3.racingbot.inventory.Iterator;
import com.group3.racingbot.inventory.NotFoundException;

/**
 * Keeps track of which drivers are winning within a race event.
 * @author Nick Sabia
 */
public class Standings {
	private List<DriverStanding> standings;
	//private int highestPositionRemaining; // For those still racing, this is the highest position which they can receive.
	//private int lowestPositionRemaining; // For those still racing, this is the lowest position which they can receive.
	
	public Standings() {
		this.standings = new ArrayList<DriverStanding>();
		//this.highestPositionRemaining = 1;
		//this.lowestPositionRemaining = 1;
	}
	
	/**
	 * Retrieve the highest position which currently racing drivers can achieve.
	 * @return the highestPositionRemaining
	 */
	//public int getHighestPositionRemaining() {
	//	return highestPositionRemaining;
	//}

	/**
	 * Set the highest position which currently racing drivers can achieve.
	 * @param highestPositionRemaining the highestPositionRemaining to set
	 */
	//public void setHighestPositionRemaining(int highestPositionRemaining) {
	//	this.highestPositionRemaining = highestPositionRemaining;
	//}
	
	/**
	 * Retrieve the lowest position which currently racing drivers can achieve.
	 * @return the highestPositionRemaining
	 */
	//public int getLowestPositionRemaining() {
	//	return lowestPositionRemaining;
	//}

	/**
	 * Set the lowest position which currently racing drivers can achieve.
	 * @param highestPositionRemaining the highestPositionRemaining to set
	 */
	//public void setLowestPositionRemaining(int lowestPositionRemaining) {
	//	this.lowestPositionRemaining = lowestPositionRemaining;
	//}
	
	/**
	 * Sorts the standings based on driver's distance traveled to find out their positions.
	 */
	public void updateStandings() {
		// Create four lists.
		List<DriverStanding> finished = new ArrayList<DriverStanding>(); // This list holds all drivers which have completed the race.
		List<DriverStanding> racing = new ArrayList<DriverStanding>(); // This list holds all drivers who are currently racing.
		List<DriverStanding> dnf = new ArrayList<DriverStanding>(); // This list holds all drivers who couldn't complete the race.
		List<DriverStanding> result = new ArrayList<DriverStanding>(); // This list holds all drivers in the order which they stand within the race event.
		
		// Separate driver standings into different lists based on their states.
		Iterator<DriverStanding> iterator = this.iterator();
		while (iterator.hasNext()) {
			DriverStanding current = iterator.next();
			if (current.getDriver().getState() instanceof Racing) {
				racing.add(current);
			}
			else if (current.getDriver().getState() instanceof FinishedRace) {
				finished.add(current);
			}
			else {
				dnf.add(current);
			}
		}
		
		// Sort the racers who have finished by timeCompleted, then if there's a tie randomly pick one.
		TimeCompletedComparator timeCompletedComparator = new TimeCompletedComparator();
		Collections.sort(finished, timeCompletedComparator);
		for (int i = 1, len = finished.size()-1; i < len; i++) {
			if (finished.get(i-1).getTimeCompleted() == finished.get(i).getTimeCompleted()) {
				int rand = ThreadLocalRandom.current().nextInt(0, 2);
				if (rand == 1) {
					Collections.swap(finished, i-1, i);
				}
				finished.get(i-1).setPosition(i); // Set the driver's position in the race
			}
		}
		finished.get(finished.size()-1).setPosition(finished.size()-1);
		
		// Sort the racers who are still currently racing.
		DistanceTraveledComparator distanceTraveledComparator = new DistanceTraveledComparator();
		Collections.sort(racing, distanceTraveledComparator);
		
		// Sort the racers who couldn't complete the race
		Collections.sort(dnf, timeCompletedComparator);
		Collections.reverse(dnf);
		
		// Add each list together into the result list
		result.addAll(finished);
		result.addAll(racing);
		result.addAll(dnf);
		
		// Finally, set the positions of each driver based on their index in the result list.
		for (int i = 0, len = result.size()-1; i < len; i++) {
			result.get(i).setPosition(i);
		}
		
		this.setStandings(result);
	}

	/**
	 * @return the standings
	 */
	public List<DriverStanding> getStandings() {
		return standings;
	}

	/**
	 * @param standings the standings to set
	 */
	public void setStandings(List<DriverStanding> standings) {
		this.standings = standings;
	}
	
	/**
	 * Adds a driver to the race event.
	 * @param playerId
	 * @param driverId
	 */
	public void addDriver(String playerId, String driverId) {
		//Predicate<DriverStanding> condition = driverPosition -> driverPosition.getDriverId().equals(driver.getId());
		
		for (int i = 0, len = this.standings.size()-1; i < len; i++) {
			if (this.standings.get(i).getDriverId().equals(driverId)) {
				System.out.println("Unable to add driver. Driver already exists in the event.");
				return;
			}
		}
		this.standings.add(new DriverStanding(playerId, driverId));
		System.out.println("Driver added");
	}

	/**
	 * Removes a driver from the race event.
	 * @param driver the driver to remove
	 */
	public void removeDriver(String driverId) {
		Predicate<DriverStanding> condition = driverPosition -> driverPosition.getDriverId().equals(driverId);
		if (this.standings.removeIf(condition)) {
			System.out.println("Driver removed");
		}
		else {
			System.out.println("Unable to remove driver. The specified driver is not in the race event.");
		}
	}
	
	/**
	 * Retrieve the DriverPosition from the race event's standings based on driver id.
	 * @param driverId
	 * @return the DriverPosition
	 * @throws NotFoundException 
	 */
	public DriverStanding getDriverStandingById(String driverId) throws NotFoundException {
		Iterator<DriverStanding> iterator = this.iterator();
		while (iterator.hasNext()) {
			DriverStanding currentDriverStanding = iterator.next();
			if (currentDriverStanding.getDriver().getId().equals(driverId)) {
				return currentDriverStanding;
			}
		}
		throw new NotFoundException("Driver was not found in this race event.");
	}
	
	/**
	 * Creates an instance of an iterator which can be used to traverse the standings of this event.
	 */
	public Iterator<DriverStanding> iterator() {
		return new StandingsIterator();
	}
	
	/**
	 * Provides a way to traverse the driver standings of a race event.
	 * @author Nick Sabia
	 *
	 */
	private class StandingsIterator implements Iterator<DriverStanding> {
		private int current;
		
		private StandingsIterator() {
			this.current = 0;
		}
		
		/**
		 * Verifies that there is another entry ahead of the current one.
		 */
		public boolean hasNext() {
			if (this.current < Standings.this.standings.size()) {
				return true;
			}
			return false;
		}
		
		/**
		 * Grab the next entry.
		 */
		public DriverStanding next() {
			DriverStanding item = null;
			try {
				item = Standings.this.standings.get(this.current);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("End of the list has been reached.");
			}
			this.current++;
			return item;
		}
	}
}
