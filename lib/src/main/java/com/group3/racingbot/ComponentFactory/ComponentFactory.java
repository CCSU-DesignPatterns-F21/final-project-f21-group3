package com.group3.racingbot.ComponentFactory;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Jack Gola
 * Defines the abstract class ComponentFactory, will be utilized in next sprint
 * Does not serve purpose now but will once "shop" is implemented
 */

//TODO: edit javadoc once shop is implemented
@JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConcreteComponentFactory.class)})
@BsonDiscriminator
public abstract class ComponentFactory{
	public abstract Component createComponent(String type, int cost); 
		
}
