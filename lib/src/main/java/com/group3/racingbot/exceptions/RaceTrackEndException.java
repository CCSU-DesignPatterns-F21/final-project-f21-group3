package com.group3.racingbot.exceptions;


/** 
 * 	Exception that is thrown when the driver or client reaches the end of the race track and no more nodes exist.
 *  @author Maciej Bregisz
*/
public class RaceTrackEndException extends Exception{

	private static final long serialVersionUID = -7879917841881436381L;
	
	/**
	 * Exception constructor, takes a String message and passes it to Exception superclass.
	 * @param message Error message which is passed to the Exception superclass.
	 */
	public RaceTrackEndException(String message) {
		super(message);
	}
}
