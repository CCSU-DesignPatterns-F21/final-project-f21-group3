package com.group3.racingbot;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class CustomUser {

	@BsonProperty(value="_id")
	private String id;
	@BsonProperty(value="currency")
	private int currency;
	@BsonProperty(value="wins")
	private int wins;
	@BsonProperty(value="losses")
	private int losses;
	
	//Default constuctor
	public CustomUser(String uid, int money,int win, int loss) {
		id = uid;
		currency = money;
		wins = win;
		losses = loss;
		
	}
	
	//Constructor for DB handling
	public CustomUser(int money) {
		currency = money;
		
	}

}
