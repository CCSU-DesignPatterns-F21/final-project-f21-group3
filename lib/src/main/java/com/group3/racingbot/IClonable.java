package com.group3.racingbot;

/*
 * Clonable interface for prototype pattern
 * @author Maciej Bregisz
 */
public interface IClonable {
	/**
	 * Creates a copy of the object
	 * @return a clone of the object
	 */
	IClonable clone();
}
