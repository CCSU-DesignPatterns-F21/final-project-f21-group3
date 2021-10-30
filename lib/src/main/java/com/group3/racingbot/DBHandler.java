package com.group3.racingbot;


import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.group3.racingbot.shop.Shop;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Handles the requests and connections to the database.
 * @author Maciej Bregisz
 *
 */
public class DBHandler {
	private static ConnectionString connectionString;
	private static MongoClientSettings settings;
	private static ConfigPropertiesHandler configProperties;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Player> userCollection;
	private MongoCollection<Shop> shopCollection;
	
	/**
	 * Constructor initializes the necessary settings required for connecting to the MongoDB.
	 */
	public DBHandler() {
		ClassModel<Shop> shopModel = ClassModel.builder(Shop.class).enableDiscriminator(true).build();
		
		 CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(shopModel).automatic(true).build();
		 CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		            MongoClientSettings.getDefaultCodecRegistry(),
		            CodecRegistries.fromProviders(pojoCodecProvider)
		    );
		configProperties = ConfigPropertiesHandler.getInstance();

		//connectionString = new ConnectionString("mongodb+srv://"+configProperties.getProperty("mongoDBUsername") +":"+ configProperties.getProperty("mongoDBPass") +"@racingbot.rjpmq.mongodb.net/"+configProperties.getProperty("mongoDBDatabase")+"?retryWrites=true&w=majority");
		connectionString = new ConnectionString("mongodb://127.0.0.1:27017/RacingBot");
		settings = MongoClientSettings.builder().applyConnectionString(connectionString).retryWrites(true).build();
				mongoClient = MongoClients.create(settings);
				database = mongoClient.getDatabase(configProperties.getProperty("mongoDBDatabase")).withCodecRegistry(codecRegistry);
				userCollection = database.getCollection("Users",Player.class).withCodecRegistry(codecRegistry);
				shopCollection = database.getCollection("Shops",Shop.class).withCodecRegistry(codecRegistry);
				//System.out.println(userCollection.countDocuments());
	}
	
	/**
	 * Gets a User record from the database by specified id. ID is the Discord User id.  
	 * @param id the Discord user ID
	 * @return whether or not the User with the given ID exists.
	 */
	public boolean userExists(String id) {
		if(userCollection.find(eq("_id",id)).first() != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Inserts the Player object into the database collection.
	 * @param p Player Object being stored in database collection.
	 */
	public void insertUser(Player p) {
		userCollection.insertOne(p);			
	}
	
	/**
	 * Find and replace a Player object in the Database with a modified Player object
	 * @param p Player object, will replace a Player database record with the one passed in. 
	 */
	public void updateUser(Player p) {
		userCollection.findOneAndReplace(eq("_id",p.getId()), p);
	}
	
	/**
	 * Returns a Player object from the Database based on the Discord ID.
	 * @param id Discord User ID, used for identifying and retrieving of stored Player objects.
	 * @return Parsed Player object from the database.
	 */
	public Player getPlayer(String id) {
		Player player = (Player) userCollection.find(eq("_id",id)).first();
		return player;
		
	}
	
	public void insertShop(Shop shop)
	{
		Shop s = shopCollection.find(eq("_id",shop.getId())).first();
		if(s != null)
		{
			System.out.println("Shop already in DB: " + s.getName());
		}else {
			shopCollection.insertOne(shop);
		}
		
	}
	
	public Shop getShop(int id) {
		return (Shop)shopCollection.find(eq("_id",id)).first();
	}
	
	public List<Shop> getShops(){
		System.out.println(shopCollection.find().first());
		return null;
	}
	
	/**
	 * @return the database reference
	 */
	public MongoDatabase getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set for MongoClient to connect to
	 */
	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}

	/**
	 * @return the MongoDB UserCollection
	 */
	public MongoCollection<Player> getUserCollection() {
		return userCollection;
	}
	
	/**
	 * @return the MongoDB ShopCollection
	 */
	public MongoCollection<Shop> getShopCollection() {
		return shopCollection;
	}

	/**
	 * @param userCollection the userCollection to set
	 */
	public void setUserCollection(MongoCollection<Player> userCollection) {
		this.userCollection = userCollection;
	}

	/**
	 * @param connectionString the connectionString to set
	 */
	public static void setConnectionString(ConnectionString connectionString) {
		DBHandler.connectionString = connectionString;
	}
	
	/**
	 * 
	 * @return returns the connection string of the server
	 */
	public static ConnectionString getConnectionString() {
		return connectionString;
	}
	/**
	 * 
	 * @return the MongoDB client settings
	 */
	public static MongoClientSettings getSettings() {
		return settings;
	}

	
	/**
	 * @param settings the settings to set
	 */
	public static void setSettings(MongoClientSettings settings) {
		DBHandler.settings = settings;
	}
	/**
	 * 
	 * @return the MongoDB Client
	 */
	public MongoClient getMongoClient() {
		return mongoClient;
	}


	/**
	 * @param mongoClient the mongoClient to set
	 */
	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;

	}

	/**
	 * @param configProperties the configProperties to set
	 */
	public static void setConfigProperties(ConfigPropertiesHandler configProperties) {
		DBHandler.configProperties = configProperties;
	}
	
	/**
	 * 
	 * @return the ConfigPropertiesHandler instance.
	 */
	public static ConfigPropertiesHandler getConfigProperties() {
		return configProperties;
	}
	/**
	 * @return Object parsed to string.
	 */
	@Override 
	public String toString() {
		return connectionString.toString();
		
	}
	
	/**
	 * Custom hashCode method for DBHandler
	 * @return calculated hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((mongoClient == null) ? 0 : mongoClient.hashCode());
		result = prime * result + ((userCollection == null) ? 0 : userCollection.hashCode());
		return result;
	}
	
	/**
	 * Checks whether two objects are the same or equal instances.
	 * @return whether or not two instances of objects are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBHandler other = (DBHandler) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database))
			return false;
		if (mongoClient == null) {
			if (other.mongoClient != null)
				return false;
		} else if (!mongoClient.equals(other.mongoClient))
			return false;
		if (userCollection == null) {
			if (other.userCollection != null)
				return false;
		} else if (!userCollection.equals(other.userCollection))
			return false;
		return true;
	}
}
