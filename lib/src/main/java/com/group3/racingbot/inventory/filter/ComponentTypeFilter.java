package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.ComponentFactory.ComponentType;
import com.group3.racingbot.inventory.Iterator;

/**
 * Decorates an inventory iterator and filters out anything which doesn't match the given criteria.
 * This filters results based on the name of a class.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class ComponentTypeFilter<T extends ComponentFilterable> extends IteratorDecorator<T> {
	private ComponentType componentType;
	private FilterOperation operation;
	
	/**
	 * Applies the class filter to whatever inventory iterator is passed into it.
	 * @param iterator the iterator which is to be decorated
	 * @param op the logical operator
	 * @param componentType the type of component to filter by
	 */
	public ComponentTypeFilter(Iterator<T> iterator, FilterOperation op, ComponentType componentType) {
		super(iterator);
		this.componentType = componentType;
		this.operation = op;
	}
	
	/**
	 * Associates a class name with a component type if possible. Returns null if not possible.
	 * @param className the class name to pair with a component type.
	 */
	/*private ComponentType componentTypeFromClassName(String className) {
		if (className.equals("EngineComponent")) {
			return ComponentType.ENGINE;
		}
		else if (className.equals("SuspensionComponent")) {
			return ComponentType.SUSPENSION;
		}
		else if (className.equals("ChassisComponent")) {
			return ComponentType.CHASSIS;
		}
		else if (className.equals("TransmissionComponent")) {
			return ComponentType.TRANSMISSION;
		}
		else if (className.equals("WheelComponent")) {
			return ComponentType.WHEELS;
		}
		else {
			return null;
		}
	}*/
	
	@Override
	public T next() {
		T item = super.getIterator().next();
		boolean itemMatchesContraints = false;
		//ComponentType itemComponentType = null;//componentTypeFromClassName(item.getClass().getSimpleName());
		switch (this.operation) {
			case IS_NOT_EQUAL:
				itemMatchesContraints = item.getComponentType() != null 
					&& item.getComponentType() != this.componentType;
				break;
			case IS_EQUAL:
				itemMatchesContraints = item.getComponentType() != null 
					&& item.getComponentType() == this.componentType;
				break;
			default:
				System.out.println("ComponentTypeFilter: Invalid operator supplied. Only supports equals and not equals.");
				break;
		}
		if (!itemMatchesContraints) {
			// If the item doesn't match the given criteria, 
			// we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	@Override
	public String getCriteria() {
		return this.operation.toString().toLowerCase() + " " + this.componentType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentType == null) ? 0 : componentType.hashCode());
		result = prime * result + super.getCurrentIndex();
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof ComponentTypeFilter) {
			ComponentTypeFilter<?> otherObj = (ComponentTypeFilter<?>) other;
			if (this.getCriteria().equals(otherObj.getCriteria())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "ComponentTypeFilter which filters for items that are " + this.getCriteria();
	}
}
