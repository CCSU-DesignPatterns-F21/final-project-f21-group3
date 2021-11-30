package com.group3.racingbot.driverstate;

/**
 * This signifies that the class has attributes which may need to be pulled from the database. 
 * @author Nick Sabia
 */
public interface Refreshable {
	/**
	 * In the event that this driver is in this state when the server shuts down, this will grab all necessary data from the database in order to get back up and running.
	 * @return whether or not all missing objects were successfully obtained from the database.
	 */
	boolean refreshFromDB();
}
