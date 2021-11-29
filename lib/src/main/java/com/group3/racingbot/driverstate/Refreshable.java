package com.group3.racingbot.driverstate;

public interface Refreshable {
	/**
	 * In the event that this driver is in this state when the server shuts down, this will grab all necessary data from the database in order to get back up and running.
	 * @return whether or not all missing objects were successfully obtained from the database.
	 */
	boolean refreshFromDB();
}
