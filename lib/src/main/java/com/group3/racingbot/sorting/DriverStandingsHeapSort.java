/**
 * 
 */
package com.group3.racingbot.sorting;

import java.util.List;

import com.group3.racingbot.standings.DriverStanding;

/**
 * Sorts a given list of DriverStandings using the heap sort algorithm.
 * 
 * @author Jack Gola
 */
public class DriverStandingsHeapSort implements SortStandings {

	@Override
	public List<DriverStanding> sortByTimeCompleted(List<DriverStanding> list) {
		heapSortByTimeCompleted(list);
		return null;
	}

	@Override
	public List<DriverStanding> sortByDistanceTraveled(List<DriverStanding> list) {
		heapSortByDistanceTraveled(list);
		return null;
	}

	static void heapifyByTimeCompleted(List<DriverStanding> list, int length, int i) {
		int leftChild = 2 * i + 1;
		int rightChild = 2 * i + 2;
		int largest = i;

		// if the left child is larger than parent
		if (leftChild < length && list.get(leftChild).getTimeCompleted() > list.get(largest).getTimeCompleted()) {
			largest = leftChild;
		}

		// if the right child is larger than parent
		if (rightChild < length && list.get(leftChild).getTimeCompleted() > list.get(largest).getTimeCompleted()) {
			largest = rightChild;
		}

		// if a swap needs to occur
		if (largest != i) {
			int temp = list.get(i).getTimeCompleted();
			i = list.get(largest).getTimeCompleted();
			largest = temp;
			heapifyByTimeCompleted(list, length, largest);
		}
	}

	private static void heapSortByTimeCompleted(List<DriverStanding> list) {
		if (list.size() == 0)
			return;

		// Building the heap
		int length = list.size() - 1;
		// we're going from the first non-leaf to the root
		for (int i = length / 2 - 1; i >= 0; i--)
			heapifyByTimeCompleted(list, length, i);

		for (int i = length - 1; i >= 0; i--) {
			DriverStanding temp = list.get(0);
			list.set(0, list.get(i));
			list.set(i, temp);

			heapifyByTimeCompleted(list, i, 0);
		}
	}
	static void heapifyByDistanceTraveled(List<DriverStanding> list, int length, int i) {
		int leftChild = 2 * i + 1;
		int rightChild = 2 * i + 2;
		int largest = i;

		// if the left child is larger than parent
		if (leftChild < length && list.get(leftChild).getDistanceTraveled() > list.get(largest).getDistanceTraveled()) {
			largest = leftChild;
		}

		// if the right child is larger than parent
		if (rightChild < length && list.get(leftChild).getDistanceTraveled() > list.get(largest).getDistanceTraveled()) {
			largest = rightChild;
		}

		// if a swap needs to occur
		if (largest != i) {
			int temp = list.get(i).getDistanceTraveled();
			i = list.get(largest).getDistanceTraveled();
			largest = temp;
			heapifyByDistanceTraveled(list, length, largest);
		}
	}

	private static void heapSortByDistanceTraveled(List<DriverStanding> list) {
		if (list.size() == 0)
			return;

		// Building the heap
		int length = list.size() - 1;
		// we're going from the first non-leaf to the root
		for (int i = length / 2 - 1; i >= 0; i--)
			heapifyByDistanceTraveled(list, length, i);

		for (int i = length - 1; i >= 0; i--) {
			DriverStanding temp = list.get(0);
			list.set(0, list.get(i));
			list.set(i, temp);

			heapifyByDistanceTraveled(list, i, 0);
		}
	}
}
