/**
 * 
 */
package com.group3.racingbot.ComponentFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the different type of components which can be fitted to a car.
 * @author Nick Sabia
 */
public enum ComponentType {
	CHASSIS(0),
	ENGINE(1),
	SUSPENSION(2),
	TRANSMISSION(3),
	WHEELS(4);
	
	private final int componentType;

	private ComponentType(int componentType) {
		this.componentType = componentType;
	}
	
	/**
	 * Returns the integer representation of the component type.
	 * @return int
	 */
	public int getComponentType() {
		return this.componentType;
	}
	
	/**
	 * Retrieve a randomly selected component type
	 * @return componentType
	 */
	public static ComponentType random() {
		return ComponentType.values()[ThreadLocalRandom.current().nextInt(0, 5)];
	}
	
	@Override
	/**
	 * Display the textual representation of the enumeration
	 */
	public String toString() {
		switch (this.componentType) {
			case 0:
				return "CHASSIS";
			case 1:
				return "ENGINE";
			case 2:
				return "SUSPENSION";
			case 3:
				return "TRANSMISSION";
			case 4:
				return "WHEELS";
			default:
				return "INVALID COMPONENT TYPE";
		}
	}
}
