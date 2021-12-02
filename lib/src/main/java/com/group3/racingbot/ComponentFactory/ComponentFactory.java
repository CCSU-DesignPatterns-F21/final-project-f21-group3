package com.group3.racingbot.ComponentFactory;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Jack Gola
 * Defines the abstract class ComponentFactory, will be utilized in next sprint
 * Does not serve purpose now but will once "shop" is implemented
 */

@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConcreteComponentFactory.class)})
@BsonDiscriminator
public abstract class ComponentFactory{
	/**
	 * Creates a new component with the given parameters.
	 * @param type the type of component to construct
	 * @param cost the price of the component being constructed.
	 * @return the component
	 */
	public abstract Component createComponent(ComponentType type, int cost); 
}
