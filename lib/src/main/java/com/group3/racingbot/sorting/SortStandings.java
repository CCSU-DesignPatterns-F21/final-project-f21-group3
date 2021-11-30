/**
 * 
 */
package com.group3.racingbot.sorting;

import java.util.List;

import com.group3.racingbot.standings.DriverStanding;

/**
 * Classes which implement this must support some sorting implementation for sorting DriverStanding objects.
 * @author Nick Sabia
 */
public interface SortStandings {
	/**
	 * Sorts a list of driver standings by time completed of a race event.
	 * @return list of driver standings sorted by time completed
	 */
	public List<DriverStanding> sortByTimeCompleted(List<DriverStanding> list);
	
	/**
	 * Sorts a list of driver standings by total distance traveled in a race event.
	 * @return list of driver standings sorted by distance traveled
	 */
	public List<DriverStanding> sortByDistanceTraveled(List<DriverStanding> list);
}
