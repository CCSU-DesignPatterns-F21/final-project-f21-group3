package com.group3.racingbot.inventory.filter;

/**
 * Represents various qualities of materials that can be owned.
 * @author Nick Sabia
 *
 */
public enum Quality {
	LEMON(0),
	JUNKYARD(1),
	OEM(2),
	SPORTS(3),
	RACING(4);
	
	private final int quality;

	private Quality(int quality) {
		this.quality = quality;
	}
	
	/**
	 * Returns the integer representation of the quality
	 * @return int
	 */
	public int getQuality() {
		return this.quality;
	}
	
	@Override
	/**
	 * Display the textual representation of the enumeration
	 */
	public String toString() {
		switch (this.quality) {
			case 0:
				return "LEMON";
			case 1:
				return "JUNKYARD";
			case 2:
				return "OEM";
			case 3:
				return "SPORTS";
			case 4:
				return "RACING";
			default:
				return "INVALID QUALITY";
		}
	}
}
