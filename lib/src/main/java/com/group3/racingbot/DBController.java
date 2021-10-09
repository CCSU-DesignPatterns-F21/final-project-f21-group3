package com.group3.racingbot;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBController {
	
	private static DBController dbControllerInstance = null;
	
	private ConnectionString connectionString;
	private MongoClientSettings settings;
	private MongoClient mongoClient;
	private MongoDatabase userDatabase;


	private DBController() {
		// TODO Auto-generated constructor stub
		connectionString = new ConnectionString("mongodb+srv://RacingBotDBAdmin:Yif9e8qiTpSddzu7@racingbot.rjpmq.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .build();
		mongoClient = MongoClients.create(settings);
		userDatabase = mongoClient.getDatabase("RacingBot");

	}
	
	public static DBController getInstance()
    {
        if (dbControllerInstance == null)
        	dbControllerInstance = new DBController();
 
        return dbControllerInstance;
    }

}
