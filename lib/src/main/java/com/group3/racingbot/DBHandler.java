package com.group3.racingbot;


import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.driverstate.Aggressive;
import com.group3.racingbot.driverstate.Crashed;
import com.group3.racingbot.driverstate.DNF;
import com.group3.racingbot.driverstate.Defensive;
import com.group3.racingbot.driverstate.DriverState;
import com.group3.racingbot.driverstate.FinishedRace;
import com.group3.racingbot.driverstate.FinishedTraining;
import com.group3.racingbot.driverstate.Normal;
import com.group3.racingbot.driverstate.RacePending;
import com.group3.racingbot.driverstate.Racing;
import com.group3.racingbot.driverstate.Resting;
import com.group3.racingbot.driverstate.Training;
import com.group3.racingbot.inventory.ComponentInventory;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.TrackNode;
import com.group3.racingbot.shop.Shop;
import com.group3.racingbot.standings.DriverStanding;
import com.group3.racingbot.standings.Standings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Sorts.descending;

/**
 * Handles the requests and connections to the database.
 * @author Maciej Bregisz
 *
 */
public class DBHandler {
	private static DBHandler instance; // Singleton
	private static ConnectionString connectionString;
	private static MongoClientSettings settings;
	private static ConfigPropertiesHandler configProperties;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Player> userCollection;
	private MongoCollection<Shop> shopCollection;
	private MongoCollection<RaceEvent> raceEventCollection;
	
	/**
	 * Constructor initializes the necessary settings required for connecting to the MongoDB.
	 */
	private DBHandler() {
		ClassModel<Shop> shopModel = ClassModel.builder(Shop.class).enableDiscriminator(true).build();
		ClassModel<ComponentFactory> componentFactoryModel = ClassModel.builder(ComponentFactory.class).enableDiscriminator(true).build();
		ClassModel<ConcreteComponentFactory> concreteComponentFactoryModel = ClassModel.builder(ConcreteComponentFactory.class).enableDiscriminator(true).build();
		ClassModel<ComponentInventory> componenInventorytModel = ClassModel.builder(ComponentInventory.class).enableDiscriminator(true).build();
		ClassModel<Component> componentModel = ClassModel.builder(Component.class).enableDiscriminator(true).build();
		ClassModel<RaceEvent> raceEventModel = ClassModel.builder(RaceEvent.class).enableDiscriminator(true).build();
		ClassModel<RaceTrack> raceTrackModel = ClassModel.builder(RaceTrack.class).enableDiscriminator(true).build();
		ClassModel<Standings> standingsModel = ClassModel.builder(Standings.class).enableDiscriminator(true).build();
		ClassModel<DriverStanding> driverStandingModel = ClassModel.builder(DriverStanding.class).enableDiscriminator(true).build();
		//ClassModel<TrackNode> trackNodeModel = ClassModel.builder(TrackNode.class).enableDiscriminator(true).build();
		// States
		ClassModel<DriverState> driverStateModel = ClassModel.builder(DriverState.class).enableDiscriminator(true).build();
		ClassModel<Racing> racingStateModel = ClassModel.builder(Racing.class).enableDiscriminator(true).build();
		ClassModel<Resting> restingStateModel = ClassModel.builder(Resting.class).enableDiscriminator(true).build();
		ClassModel<Training> trainingStateModel = ClassModel.builder(Training.class).enableDiscriminator(true).build();
		ClassModel<RacePending> racePendingStateModel = ClassModel.builder(RacePending.class).enableDiscriminator(true).build();
		ClassModel<Defensive> defensiveStateModel = ClassModel.builder(Defensive.class).enableDiscriminator(true).build();
		ClassModel<Normal> normalStateModel = ClassModel.builder(Normal.class).enableDiscriminator(true).build();
		ClassModel<Aggressive> aggressiveStateModel = ClassModel.builder(Aggressive.class).enableDiscriminator(true).build();
		ClassModel<Crashed> crashedStateModel = ClassModel.builder(Crashed.class).enableDiscriminator(true).build();
		ClassModel<DNF> dnfStateModel = ClassModel.builder(DNF.class).enableDiscriminator(true).build();
		ClassModel<FinishedRace> finishedRaceStateModel = ClassModel.builder(FinishedRace.class).enableDiscriminator(true).build();
		ClassModel<FinishedTraining> finishedTrainingStateModel = ClassModel.builder(FinishedTraining.class).enableDiscriminator(true).build();
		 CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(shopModel)
				 .register(driverStateModel)
				 .register(racingStateModel)
				 .register(shopModel)
				 .register(componentFactoryModel)
				 .register(concreteComponentFactoryModel)
				 .register(componenInventorytModel)
				 .register(componentModel)
				 .register(restingStateModel)
				 .register(trainingStateModel)
				 .register(racePendingStateModel)
				 .register(defensiveStateModel)
				 .register(normalStateModel)
				 .register(aggressiveStateModel)
				 .register(crashedStateModel)
				 .register(dnfStateModel)
				 .register(finishedRaceStateModel)
				 .register(finishedTrainingStateModel)
				 .register(raceEventModel)
				 .register(raceTrackModel)
				 .register(standingsModel)
				 .register(driverStandingModel)
				 .automatic(true).build();
		 CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		            MongoClientSettings.getDefaultCodecRegistry(),
		            CodecRegistries.fromProviders(pojoCodecProvider)
		    );
		configProperties = ConfigPropertiesHandler.getInstance();

		connectionString = new ConnectionString("mongodb+srv://"+configProperties.getProperty("mongoDBUsername") +":"+ configProperties.getProperty("mongoDBPass") +"@racingbot.rjpmq.mongodb.net/"+configProperties.getProperty("mongoDBDatabase")+"?retryWrites=true&w=majority");
		//connectionString = new ConnectionString("mongodb://127.0.0.1:27017/RacingBot");
		settings = MongoClientSettings.builder().applyConnectionString(connectionString).retryWrites(true).build();
				mongoClient = MongoClients.create(settings);
				database = mongoClient.getDatabase(configProperties.getProperty("mongoDBDatabase")).withCodecRegistry(codecRegistry);
				userCollection = database.getCollection("Users",Player.class).withCodecRegistry(codecRegistry);
				shopCollection = database.getCollection("Shops",Shop.class).withCodecRegistry(codecRegistry);
				raceEventCollection = database.getCollection("Events",RaceEvent.class).withCodecRegistry(codecRegistry);
				//System.out.println(userCollection.countDocuments());
	}
	
	/**
	 * Returns an instance of DBHandler.
	 * @return
	 */
	public static DBHandler getInstance() {
		if (instance == null)
        	instance = new DBHandler();
 
        return instance;
	}
	
	/**
	 * Checks to see if a given race event exists within the database based on an event id.
	 * @param id Race event ID
	 * @return Whether or not the event exists within the database
	 */
	public boolean raceEventExists(String id) {
		if(raceEventCollection.find(eq("_id",id)).first() != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Insert a race event into the database.
	 * @param raceEvent RaceEvent object being stored into the database
	 */
	public void insertRaceEvent(RaceEvent raceEvent) {
		raceEventCollection.insertOne(raceEvent);
	}
	
	/**
	 * Update a RaceEvent in the database.
	 * @param raceEvent RaceEvent object being updated in the database
	 */
	public void updateRaceEvent(RaceEvent raceEvent) {
		raceEventCollection.findOneAndReplace(eq("_id",raceEvent.getId()), raceEvent);
	}
	
	/**
	 * Retrieve the most recent race event from the database.
	 */
	public RaceEvent pullMostRecentRaceEvent() {
		FindIterable<RaceEvent> iterable = raceEventCollection.find().limit(1).sort(descending("createdOn"));
		return iterable.first();
	}
	
	/**
	 * Retrieve a race event's info from the database.
	 * @param id RaceEvent id to use to obtain info from the database
	 * @return RaceEvent object based on the id supplied
	 */
	public RaceEvent getRaceEvent(String id) {
		RaceEvent raceEvent = (RaceEvent) raceEventCollection.find(eq("_id",id)).first();
		return raceEvent;
	}
	
	/**
	 * @return the raceEventCollection
	 */
	public MongoCollection<RaceEvent> getRaceEventCollection() {
		return raceEventCollection;
	}

	/**
	 * @param raceEventCollection the raceEventCollection to set
	 */
	public void setRaceEventCollection(MongoCollection<RaceEvent> raceEventCollection) {
		this.raceEventCollection = raceEventCollection;
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
	
	/**
	 * Finds and replaces the Shop stored in DB with a new one
	 * @param shop new Shop object replacing the one in the DB
	 */
	public void updateShop(Shop shop) {
		shopCollection.findOneAndReplace(eq("_id",shop.getId()),shop);
		
	}
	
	public List<Shop> getShops(){
		//System.out.println(shopCollection.find().first());
		List<Shop> iterablelist = shopCollection.find().into(new ArrayList<Shop>());
		
		return iterablelist;
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
