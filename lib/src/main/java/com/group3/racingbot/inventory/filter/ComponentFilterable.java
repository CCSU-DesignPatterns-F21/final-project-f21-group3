/**
 * 
 */
package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.ComponentFactory.ComponentType;

/**
 * Classes which implement this can be filtered by a concrete IteratorDecorator which filters by component type.
 * @author Nick Sabia
 */
public interface ComponentFilterable {
	/**
	 * Retrieves what kind of component the component is. For instance, it could be an engine or a transmission.
	 * @return the component type
	 */
	public ComponentType getComponentType();

	/**
	 * Sets what kind of component the component is. For instance, it could be set to an engine or a transmission.
	 * @param componentType the component type to set
	 */
	public void setComponentType(ComponentType componentType);
}
