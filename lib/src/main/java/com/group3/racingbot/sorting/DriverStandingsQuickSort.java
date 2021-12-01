/**
 * 
 */
package com.group3.racingbot.sorting;

import java.util.List;

import com.group3.racingbot.standings.DriverStanding;

/**
 * Sorts a given list of DriverStandings using the quick sort algorithm.
 * @author Nick Sabia
 */
public class DriverStandingsQuickSort implements SortStandings {

	@Override
	public List<DriverStanding> sortByTimeCompleted(List<DriverStanding> list) {
		quicksortByTimeCompleted(list, 0, list.size()-1);
		return null;
	}

	@Override
	public List<DriverStanding> sortByDistanceTraveled(List<DriverStanding> list) {
		quicksortByDistance(list, 0, list.size()-1);
		return null;
	}
	
	private static void quicksortByTimeCompleted(List<DriverStanding> list, int low, int high){
	    if(low < high){
	        int p = partitionByTimeCompleted(list, low, high);
	        quicksortByTimeCompleted(list, low, p-1);
	        quicksortByTimeCompleted(list, p+1, high);
	    }
	}
	
	private static int partitionByTimeCompleted(List<DriverStanding> list, int low, int high){
	    int p = low, j;
	    for(j=low+1; j <= high; j++) {
	    	if (list.get(j).getTimeCompleted() < list.get(low).getTimeCompleted()) {
	        	swap(list, ++p, j);
	        }
	    }
	    
	    swap(list, low, p);
	    return p;
	}
	
	private static void quicksortByDistance(List<DriverStanding> list, int low, int high){
	    if(low < high){
	        int p = partitionByDistance(list, low, high);
	        quicksortByDistance(list, low, p-1);
	        quicksortByDistance(list, p+1, high);
	    }
	}
	
	private static int partitionByDistance(List<DriverStanding> list, int low, int high){
	    int p = low, j;
	    for(j=low+1; j <= high; j++) {
	    	if (list.get(j).getDistanceTraveled() > list.get(low).getDistanceTraveled()) {
	        	swap(list, ++p, j);
	        }
	    }
	    
	    swap(list, low, p);
	    return p;
	}
	
	private static void swap(List<DriverStanding> list, int a, int b){
		DriverStanding tmp = list.get(a);
		list.set(a, list.get(b));
		list.set(b, tmp);
	}
	
	@Override
	public String toString() {
		return "Quick sort";
	}
}
