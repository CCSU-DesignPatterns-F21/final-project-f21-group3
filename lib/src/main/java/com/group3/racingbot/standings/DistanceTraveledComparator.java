/**
 * 
 */
package com.group3.racingbot.standings;

import java.util.Comparator;

/**
 * @author Nick Sabia
 *
 */
public class DistanceTraveledComparator implements Comparator<DriverStanding> {
	@Override
    public int compare(DriverStanding a, DriverStanding b) {
		return Integer.compare(a.getDistanceTraveled(), b.getDistanceTraveled());
    }
}