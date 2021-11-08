package com.group3.racingbot.driverstate;

/**
 * Represents training intensity
 * @author Nick Sabia
 *
 */
public enum Intensity {
	LIGHT(0),
	MEDIUM(1),
	INTENSE(2);
	
	private final int intensity;

	private Intensity(int intensity) {
		this.intensity = intensity;
	}
	
	/**
	 * Returns the integer representation of intensity 
	 * @return int
	 */
	public int getIntensity() {
		return this.intensity;
	}
	
	@Override
	/**
	 * Display the textual representation of the enumeration
	 */
	public String toString() {
		switch (this.intensity) {
			case 0:
				return "LIGHT";
			case 1:
				return "MEDIUM";
			case 2:
				return "INTENSE";
			default:
				return "INVALID INTENSITY";
		}
	}
}