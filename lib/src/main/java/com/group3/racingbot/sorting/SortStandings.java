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
	public List<DriverStanding> sortByTime();
}
