/**
 * 
 */
package com.group3.racingbot.standings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
	private String raceEventId;
	
	@BsonCreator
	public Standings(@BsonProperty("raceEventId") String raceEventId) {
		this.standings = new ArrayList<DriverStanding>();
		this.raceEventId = raceEventId;
	}
	
	/**
	 * Sorts the standings based on driver's distance traveled to find out their positions.
	 */
	public void sortStandings() {
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
		if (finished.size() > 0) {
			Collections.sort(finished, timeCompletedComparator);
			for (int i = 0, len = finished.size(); i < len; i++) {
				if (i > 0) {
					boolean isTied = finished.get(i-1).getTimeCompleted() == finished.get(i).getTimeCompleted();
					if (isTied) {
						int rand = ThreadLocalRandom.current().nextInt(0, 2);
						if (rand == 1) {
							Collections.swap(finished, i-1, i);
						}
					}
				}
				//finished.get(i).setPosition(i+1); // Set the driver's position in the race
			}
			//finished.get(finished.size()-1).setPosition(finished.size()); // Set last place
		}
		
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
		for (int i = 0, len = result.size(); i < len; i++) {
			result.get(i).setPosition(i+1);
		}
		
		this.setStandings(result);
	}

	/**
	 * @return the raceEventId
	 */
	public String getRaceEventId() {
		return raceEventId;
	}

	/**
	 * @param raceEventId the raceEventId to set
	 */
	public void setRaceEventId(String raceEventId) {
		this.raceEventId = raceEventId;
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
				System.out.println("Standings; addDriver method: Unable to add driver. Driver " + driverId + " already exists in the event.");
				return;
			}
		}
		this.standings.add(new DriverStanding(playerId, driverId, this.raceEventId));
		System.out.println("Standings; addDriver method: Driver " + driverId + " added to the standings of a race event");
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
	 * Updates a driver standing in the standings
	 * @param driverStanding the driver standing to put into the list of standings.
	 * @return whether or not the update was successful
	 */
	public boolean update(DriverStanding driverStanding) {
		Iterator<DriverStanding> iterator = this.iterator();
		while (iterator.hasNext()) {
			int currentIndex = iterator.getCurrentIndex();
			DriverStanding currentDriver = iterator.next();
			if (currentDriver.getDriverId().equals(driverStanding.getDriverId())) {
				this.standings.set(currentIndex, driverStanding);
				System.out.println("Standings; update method: Driver standing for Driver " + driverStanding.getDriverId() + " has been updated in the driver inventory of Player " + driverStanding.getPlayerId() + ".");
				return true;
			}
		}
		System.out.println("Standings; update method: Unable to find a driver standing for a driver with the id: " + driverStanding.getDriverId());
		return false;
	}
	
	/**
	 * Updates a driver standing in the standings at a specified index.
	 * @param driverStanding the driver standing to put into the list of standings.
	 * @param index the index to update in standings
	 */
	public boolean update(DriverStanding driverStanding, int index) {
		try {
			this.standings.set(index, driverStanding);
			System.out.println("Standings; update method: Driver standing for Driver " + driverStanding.getDriverId() + " has been updated in the driver inventory of Player " + driverStanding.getPlayerId() + ".");
			return true;
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Standings; update method: Unable to find a driver standing for a driver with the id: " + driverStanding.getDriverId());
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Retrieve the DriverStanding from the race event's standings based on driver id.
	 * @param driverId
	 * @return the DriverStanding
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
		
		@Override
		public int getCurrentIndex() {
			return current;
		}
		
		@Override
		public boolean hasNext() {
			if (this.current < Standings.this.standings.size()) {
				return true;
			}
			return false;
		}
		
		@Override
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		Iterator<DriverStanding> iterator = this.iterator();
		while (iterator.hasNext()) {
			result += iterator.next().hashCode();
		}
		result *= prime;
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof Standings) {
			Standings otherObj = (Standings) other;
			
			Iterator<DriverStanding> thisIterator = this.iterator();
			Iterator<DriverStanding> otherIterator = otherObj.iterator();
			while (thisIterator.hasNext() && otherIterator.hasNext()) {
				if (!thisIterator.next().equals(otherIterator.next())) {
					return false;
				}
			}
			if (thisIterator.hasNext() || otherIterator.hasNext()) {
				return false;
			}
			if (!(this.getRaceEventId().equals(otherObj.getRaceEventId())))
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String results = "";
		Iterator<DriverStanding> iterator = this.iterator();
		while (iterator.hasNext()) {
			DriverStanding currentDriverStanding = iterator.next();
			results += currentDriverStanding + "\n";
		}
		return results;
	}
}
