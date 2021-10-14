package com.group3.racingbot;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBHandler {
	private static ConnectionString connectionString;
	private static MongoClientSettings settings;
	private MongoClient mongoClient;
	private MongoDatabase userDatabase;
	private static ConfigPropertiesHandler configProperties;
	
	public DBHandler() {
		
		configProperties = ConfigPropertiesHandler.getInstance();
		String uri = "mongodb+srv://"+configProperties.getProperty("mongoDBUsername") +":"+ configProperties.getProperty("mongoDBPass") +"@racingbot.rjpmq.mongodb.net/"+configProperties.getProperty("mongoDBDatabase")+"?retryWrites=true&w=majority";
		System.out.println(uri);
		connectionString = new ConnectionString(uri);
		settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .build();
				mongoClient = MongoClients.create(settings);
				userDatabase = mongoClient.getDatabase(configProperties.getProperty("mongoDBDatabase"));
		
				System.out.println(userDatabase.getCollection("User").countDocuments());
	}

	public static ConnectionString getConnectionString() {
		return connectionString;
	}

	public static MongoClientSettings getSettings() {
		return settings;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getUserDatabase() {
		return userDatabase;
	}

	public static ConfigPropertiesHandler getConfigProperties() {
		return configProperties;
	}

}
