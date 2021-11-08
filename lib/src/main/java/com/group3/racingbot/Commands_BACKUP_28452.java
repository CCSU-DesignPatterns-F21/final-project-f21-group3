package com.group3.racingbot;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.driverstate.Completed;
import com.group3.racingbot.driverstate.RacePending;
import com.group3.racingbot.driverstate.Racing;
import com.group3.racingbot.driverstate.Training;
<<<<<<< HEAD
import com.group3.racingbot.inventory.DriverInventory;
import com.group3.racingbot.inventory.Iterator;
=======
import com.group3.racingbot.inventory.Iterator;
import com.group3.racingbot.racetrack.RaceTrack;
import com.group3.racingbot.racetrack.TrackNode;
import com.group3.racingbot.ComponentFactory.EngineComponent;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.Inventory;
import com.group3.racingbot.inventory.InventoryIterator;
import com.group3.racingbot.inventory.filter.QualityFilter;
>>>>>>> development
import com.group3.racingbot.gameservice.GameplayHandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.group3.racingbot.shop.Shop;

/**
 * Handles Discord user command inputs and interacts with the gameplay handler
 * and DB Handler
 * 
 * @author Maciej Bregisz
 * @author Jack Gola - "factorymethod" command
 *
 */
public class Commands extends ListenerAdapter {

	private DBHandler dbh;
	private EmbedBuilder eb;
	private ComponentFactory component;
	private GameplayHandler gph;
	private RaceEvent raceEvent;
	
	public Commands(DBHandler db) {
		eb = new EmbedBuilder();
		dbh = db;
		component = new ConcreteComponentFactory();
<<<<<<< HEAD
		this.raceEvent = new RaceEvent();
=======
>>>>>>> development
	} 
	
	/**
	 * Handles the event when a new User joins the Discord Channel, in this case it will register the user to the database it that user isn't in the database.
	 * @param event The event which is triggered when a new User joins the Discord Channel
	*/
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event)
	{
		Member user = event.getMember(); //Gets the id of the user who called the command.
	    JDA client = event.getJDA(); //Gets the JDA object for later manipulation.
		eb.clear();
		try {
			//Example response, gets the name of the User which called the command and returns a message with a @User mention in it's content.
    		if(dbh.userExists(user.getId())){
    			Player p = dbh.getPlayer(user.getId());
    			eb.setTitle("User Already Exists!");
    			eb.setColor(Color.green);
    			eb.setThumbnail(user.getUser().getAvatarUrl());
    			
	    		eb.setDescription("Total Wins: "+ p.getTotalWins()
	    				+ "\n Total Losses: " + p.getTotalLosses()
	    				+ "\n Credits: " + p.getCredits()
	    				+ "\n # of Components: " + p.getOwnedComponents().getItems().size()
	    				+ "\n # of Cars: " + p.getOwnedCars().getItems().size());
	    		//eb.addField("Title of field", "test of field", false);
	    		event.getGuild().getSystemChannel().sendMessage(eb.build()).queue();
    			
    		}else {
    			event.getGuild().getSystemChannel().sendMessage("Registering User: " + user.getAsMention() + " with RacingBot!").queue();
    			Player p = new Player();
    			p.setId(user.getId());
    			p.setUsername(user.getUser().getName());
    			p.setLastWorked(0);
    			dbh.insertUser(p);
    			eb.setThumbnail(user.getUser().getAvatarUrl());
    			eb.setTitle("User Already Exists!");
    			eb.setColor(Color.green);
	    		eb.setDescription("Total Wins: "+ p.getTotalWins()
	    				+ "\n Total Losses: " + p.getTotalLosses()
	    				+ "\n Credits: " + p.getCredits());
	    		event.getGuild().getSystemChannel().sendMessage(eb.build()).queue();
	    		//eb.addField("Title of field", "test of field", false);
    		}
		}catch(Exception e) {
			event.getGuild().getSystemChannel().sendMessage("Unexpected error when registering User, try again!");
		}
	}
	
	
	/**
	 * Handles the commands sent by the Discord User. Player command is parsed by
	 * spaces, ex. !iracer help. !iracer is required followed by a desired command.
	 * @param event The event which is triggered when a User sends a message in any text channel.
	 */
	 @Override
	  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
	    String[] args = event.getMessage().getContentRaw().split(" ");
	    Member user = event.getMember(); //Gets the id of the user who called the command.
	    JDA client = event.getJDA(); //Gets the JDA object for later manipulation.
	    
	    // Verify that each Driver which a user owns is paired with a Player object
	    /*if(dbh.userExists(user.getId())) {
	    	Player p = dbh.getPlayer(user.getId());
	    	dbh.getPlayer(null);
	    	//setPlayerFromDB(dbh, user.getId());
	    }*/
	    
	    if(args[0].equalsIgnoreCase(RacingBot.prefix+"iracer") || args[0].equalsIgnoreCase(RacingBot.prefix+"r"))
	    {
	    	if(args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("?"))
	    	{
	    		eb.clear();
	    		//Embed example
	    		eb.setColor(Color.red);
	    		eb.setDescription("**RacingBot commands:** \n"
	    				+ "**!iracer help or !r ?**\n"
	    				+ "**!iracer register or !r r** | Register with the bot, should be done automaticaly.\n"
	    				+ "**!iracer guess <number 1-50> or !r g <number 1-50** | Bet a certain amount of your Credits, if you win, you double your bet!\n"
	    				+ "**!iracer work or !r w** | Earn credits by performing work every hour! \n"
	    				+ "**!iracer profile *<Optional @mention>* or !r p** | Display your profile or someone elses profile by using @ mentions \n"
	    				+ "**!iracer shops** | Lists the items for sale of all stores. \n"
	    				+ "**!iracer shop (chopshop, junkyard, dealership, importer) or !r s (c,j,d,i)** | Lists the items for sale of a specific store.\n"
	    				+ "**!iracer race register or !r r r** | Register for the upcoming race");
	    		eb.setFooter("Text", "https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/zekroBot_Logo_-_round_small.png?raw=true");
	    		
	    	event.getChannel().sendMessage(eb.build()).queue();
	    		
	    	}
	    	//Handle User registering for bot and other 
	    	if(args[1].equalsIgnoreCase("register") || args[1].equalsIgnoreCase("r"))
	    	{
	    		eb.clear();
	    		try {
	    			//Example response, gets the name of the User which called the command and returns a message with a @User mention in it's content.
		    		if(dbh.userExists(user.getId())){
		    			Player p = dbh.getPlayer(user.getId());
		    			//eb.setImage(user.getUser().getAvatarUrl());
		    			eb.setTitle("User Already Exists!");
		    			eb.setColor(Color.green);
		    			eb.setThumbnail(user.getUser().getAvatarUrl());
		    			
			    		eb.setDescription("Total Wins: "+ p.getTotalWins()
			    				+ "\n Total Losses: " + p.getTotalLosses()
			    				+ "\n Credits: " + p.getCredits()
			    				+ "\n # of Components: " + p.getOwnedComponents().getItems().size()
			    				+ "\n # of Cars: " + p.getOwnedCars().getItems().size());
			    		//eb.addField("Title of field", "test of field", false);
			    		event.getChannel().sendMessage(eb.build()).queue();
		    			
		    		}else {
		    			event.getChannel().sendMessage("Registering User: " + user.getAsMention() + " with RacingBot!").queue();
		    			Player p = new Player();
		    			p.setId(user.getId());
		    			p.setUsername(user.getUser().getName());
		    			p.setLastWorked(0);
		    			dbh.insertUser(p);
		    			eb.setThumbnail(user.getUser().getAvatarUrl());
		    			eb.setTitle("User Already Exists!");
		    			eb.setColor(Color.green);
			    		eb.setDescription("Total Wins: "+ p.getTotalWins()
			    				+ "\n Total Losses: " + p.getTotalLosses()
			    				+ "\n Credits: " + p.getCredits());
			    		//eb.addField("Title of field", "test of field", false);
		    		}	    	
	    			
	    		}catch(Exception e) {
	    			
	    			event.getChannel().sendMessage("Unexpected error when registering User, try again!");
	    		}
	    			
	    	}
	    	//Example command, simple guessing command
	    	//TODO: Rewrite to actually use the player's credits as currency for betting.
	    	if(args[1].equalsIgnoreCase("guess") || args[1].equalsIgnoreCase("g"))
	    	{
	    		if(args[2]!=null)
	    		{
	    			int randomNum = ThreadLocalRandom.current().nextInt(0, 49);
	    	    	event.getChannel().sendMessage("Guessing: " + Integer.parseInt(args[2])).queue();
	    	    	if (Integer.parseInt(args[2]) == randomNum) {
	    	    		event.getChannel().sendMessage("You guessed right").queue();
	    	    	}else {
	    	    		event.getChannel().sendMessage("Not quite! Number was: " + randomNum).queue();
	    	    	}
	    		}else {
	    			event.getChannel().sendMessage("No bet amount parameter specified!").queue();
	    		}
		    	
	    	}
	    	//TODO: Working for the first time gives double credits.
	    	if(args[1].equalsIgnoreCase("work") || args[1].equalsIgnoreCase("w"))
	    	{
	    		try {
	    			Player p = dbh.getPlayer(user.getId());
	    			//System.out.println(p.toString());
	    		
	    			Date lastWorked = new Date(p.getLastWorked());
	    			Calendar nextWork = Calendar.getInstance(); 
	    			Calendar timeNow = Calendar.getInstance();
	    			Calendar lastWorkedDate = Calendar.getInstance();
	    			lastWorkedDate.setTime(lastWorked);
	    			timeNow.setTime(new Date(System.currentTimeMillis()));
		    		nextWork.setTime(lastWorked);               
		    		nextWork.add(Calendar.HOUR_OF_DAY, 1);      
		    		nextWork.getTime();    
		    		System.out.println("Last work: " + lastWorkedDate.getTime());
		    		System.out.println("Next work: " + nextWork.getTime());
		    		int randomWage = ThreadLocalRandom.current().nextInt(250, 500 + 1);
		    		
		    		
		    		//Work for the first time regardless of time.
		    		if(p.getLastWorked() == 0)
		    		{
		    			p.setCredits(p.getCredits() + randomWage);
		    			p.setLastWorked(System.currentTimeMillis());
		    			System.out.println(p.toString());
		    			dbh.updateUser(p);
		    		}
	    			if(timeNow.after(nextWork))
		    		{
		    			p.setCredits(p.getCredits() + randomWage);
		    			p.setLastWorked(System.currentTimeMillis());
		    			System.out.println(p.toString());
		    			event.getChannel().sendMessage("You earned: **"+randomWage + "** Your new credit balance: **" + p.getCredits()+"**").queue();
		    		
		    			dbh.updateUser(p);
		    		}
	    			if(timeNow.before(nextWork)){
		    			long remaining = nextWork.getTimeInMillis() - System.currentTimeMillis();
		    			event.getChannel().sendMessage(String.format(event.getAuthor().getAsMention() + " You can work again in: \n**%d Hours, %d Minutes** \n Your Balance: **%d**", 
		    				TimeUnit.MILLISECONDS.toHours(remaining),
		    				TimeUnit.MILLISECONDS.toMinutes(remaining),p.getCredits())).queue();
		    		}
	    		}catch(Exception e)
	    		{
	    			event.getChannel().sendMessage("Unexpected error when attempting Work command, try again!");
	    		}
	    			
	    	}
	    	
	    	if(args[1].equalsIgnoreCase("profile") || args[1].equalsIgnoreCase("p"))
	    	{
	    		
	    		
	    	}
	    	
	    	if(args[1].equalsIgnoreCase("shops"))
    		{
	    		
	    		List<Shop> shops = dbh.getShops();
	    		System.out.println(shops.size());
	    			
	    			for(int i=0; i<shops.size();i++)
	    			{
	    				eb.clear();
	    				eb.setColor(Color.green);
	    				//TODO: use the iterator function instead?
	    				List<Component> components = shops.get(i).getComponentsForSale().getItems();
	    				System.out.println(components.size());
	    				
	    				
	    				eb.setTitle(shops.get(i).getName());
	    				eb.setDescription(shops.get(i).getDescription());
	    				
	    				
	    				for(int c=0; c<components.size();c++)
	    				{
	    					Field field = new Field(components.get(c).getName(), components.get(c).toString(), false);
	    					eb.addField(field);
	    					
	    					//event.getChannel().sendMessage(components.get(c).toString()).queue();
	    					
	    				}
	    				event.getChannel().sendMessage(eb.build()).queue();
	    			}
    		}
	    	
	    	if(args[1].equalsIgnoreCase("shop") || args[1].equalsIgnoreCase("s"))
    		{
	    		
	    		System.out.println("Shop");
	    		
	    		
	    		if(args[2].equalsIgnoreCase("chopshop") || args[2].equalsIgnoreCase("c"))
	    		{
	    			Shop shop = dbh.getShop(0);
		    		//System.out.println(shop.size());
		    		eb.clear();
	    			eb.setColor(Color.green);
	    			List<Component> components = shop.getComponentsForSale().getItems();
	    			eb.setTitle(shop.getName());
	    			eb.setDescription(shop.getDescription());
	    			
		    		for(int i=0; i<components.size();i++)
		    		{
		    			
		    			//TODO: use the iterator function instead?
		    			System.out.println(components.size());
		    					
		    			Field field = new Field(components.get(i).getName(), components.get(i).toString(), true);
    					eb.addField(field);
		    					
		    		}
		    		event.getChannel().sendMessage(eb.build()).queue();
	    		}
	    		
	    		if(args[2].equalsIgnoreCase("junkyard") || args[2].equalsIgnoreCase("j"))
	    		{
	    			Shop shop = dbh.getShop(1);
		    		//System.out.println(shop.size());
		    		eb.clear();
	    			eb.setColor(Color.green);
	    			List<Component> components = shop.getComponentsForSale().getItems();
	    			eb.setTitle(shop.getName());
	    			eb.setDescription(shop.getDescription());
	    			
		    		for(int i=0; i<components.size();i++)
		    		{
		    			
		    			//TODO: use the iterator function instead?
		    			System.out.println(components.size());
		    					
		    			Field field = new Field(components.get(i).getName(), components.get(i).toString(), true);
    					eb.addField(field);
		    					
		    		}
		    		event.getChannel().sendMessage(eb.build()).queue();
	    		}
	    		if(args[2].equalsIgnoreCase("dealership") || args[2].equalsIgnoreCase("d"))
	    		{
	    			Shop shop = dbh.getShop(2);
		    		//System.out.println(shop.size());
		    		eb.clear();
	    			eb.setColor(Color.green);
	    			List<Component> components = shop.getComponentsForSale().getItems();
	    			eb.setTitle(shop.getName());
	    			eb.setDescription(shop.getDescription());
	    			
		    		for(int i=0; i<components.size();i++)
		    		{
		    			
		    			//TODO: use the iterator function instead?
		    			System.out.println(components.size());
		    					
		    			Field field = new Field(components.get(i).getName(), components.get(i).toString(), true);
    					eb.addField(field);
		    					
		    		}
		    		event.getChannel().sendMessage(eb.build()).queue();
	    		}
	    		if(args[2].equalsIgnoreCase("importer") || args[2].equalsIgnoreCase("i"))
	    		{
	    			Shop shop = dbh.getShop(3);
		    		//System.out.println(shop.size());
		    		eb.clear();
	    			eb.setColor(Color.green);
	    			List<Component> components = shop.getComponentsForSale().getItems();
	    			eb.setTitle(shop.getName());
	    			eb.setDescription(shop.getDescription());
	    			
		    		for(int i=0; i<components.size();i++)
		    		{
		    			
		    			//TODO: use the iterator function instead?
		    			System.out.println(components.size());
		    					
		    			Field field = new Field(components.get(i).getName(), components.get(i).toString(), true);
    					eb.addField(field);
		    					
		    		}
		    		event.getChannel().sendMessage(eb.build()).queue();
	    		}
	    		
    		}
	    	
	    	/*
				 ____      _           
				|    \ ___| |_ _ _ ___ 
				|  |  | -_| . | | | . |
				|____/|___|___|___|_  |
				                  |___|
				                  
				                                     
               TODO: Remove before final release, DEBUG ONLY FUNCTIONS any relationships with other classes in this case are not to be represented in the UML.                                                                                        
	    	 */

	    	if(args[1].equalsIgnoreCase("debug"))
	    	{
	    		if(args[2].equalsIgnoreCase("event"))
	    		{
	    			if(args[3].equalsIgnoreCase("generate"))
	    			{
	    				int totalNodes = 2; // initialize totalNodes
<<<<<<< HEAD
	    				if(args.length > 4 && args[4] != null)
=======
	    				if(args[4] != null)
>>>>>>> development
	    				{
	    					totalNodes = Integer.parseInt(args[4]);
	    				}
	    				else {
	    					totalNodes = ThreadLocalRandom.current().nextInt(5, 20);
<<<<<<< HEAD
=======
	    				}
	    				raceEvent.getRaceTrack().setTrackNodes(raceEvent.getRaceTrack().generateRaceTrack(totalNodes)); // Create the new track
	    				event.getChannel().sendMessage("Number of Nodes in Track: "+totalNodes).queue();
	    			}
	    			if(args[3].equalsIgnoreCase("register")) {
	    				// Register a user to an event
	    				if (raceEvent.getRaceTrack().getFirstNode() == null) {
	    					event.getChannel().sendMessage("Event does not yet exist! Create a random one by performing the command: !iracer debug event generate").queue();
	    				}
	    				else if (raceEvent.getTimeElapsed() != 0) {
	    					event.getChannel().sendMessage("Event is currently in progress. Unable to join the race.").queue();
	    				}
	    				else {
	    					Player p = dbh.getPlayer(user.getId());
	    					if (p.getActiveDriver() == null) {
	    						event.getChannel().sendMessage("User does not have an active driver. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveCar() == null) {
	    						event.getChannel().sendMessage("User does not have an active car. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveCar().getDurability() == 0) {
	    						event.getChannel().sendMessage("User's car is currently totaled. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof Training) {
	    						event.getChannel().sendMessage("User's driver is currently training. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof RacePending) {
	    						event.getChannel().sendMessage("User's driver is currently signed up for an event. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof Completed) {
	    						event.getChannel().sendMessage("User's driver needs to collect reward from previous event. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof Racing) {
	    						event.getChannel().sendMessage("User's driver is currently racing. Cannot sign up for race.").queue();
	    					}
	    					else {
	    						p.getActiveDriver().signUpForRace(p.getActiveCar(), raceEvent);
	    						event.getChannel().sendMessage("User now registered for the event").queue();
	    					}
	    				}
	    				if (raceEvent.getRaceTrack().getFirstNode() != null) {
	    					if (raceEvent.getTimeElapsed() == 0) {
	    						Player p = dbh.getPlayer(user.getId());
	    						if (p.getActiveDriver() != null) {
	    							if (p.getActiveCar() != null) {
	    								event.getChannel().sendMessage("User now registered for the event").queue();
	    							}
	    							else {
	    								event.getChannel().sendMessage("User does not have an active car. Cannot sign up for race.").queue();
	    							}
	    						}
	    						else {
	    							event.getChannel().sendMessage("User does not have an active driver. Cannot sign up for race.").queue();
	    						}
	    					}
	    					else {
	    						event.getChannel().sendMessage("Event is currently in progress. Unable to join the race.").queue();
	    					}
	    				}
	    				else {
	    					event.getChannel().sendMessage("Event does not yet exist! Create a random one by performing the command: !iracer debug event generate").queue();
>>>>>>> development
	    				}
	    				this.raceEvent.getRaceTrack().setTrackNodes(raceEvent.getRaceTrack().generateRaceTrack(totalNodes)); // Create the new track
	    				event.getChannel().sendMessage("Number of Nodes in Track: "+totalNodes).queue();
	    			}
<<<<<<< HEAD
	    			if(args[3].equalsIgnoreCase("register")) {
	    				// Register a user to an event
	    				if (raceEvent.getRaceTrack().getFirstNode() == null) {
	    					event.getChannel().sendMessage("Event does not yet exist! Create a random one by performing the command: !iracer debug event generate").queue();
	    				}
	    				else if (raceEvent.getTimeElapsed() != 0) {
	    					event.getChannel().sendMessage("Event is currently in progress. Unable to join the race.").queue();
	    				}
	    				else {
	    					Player p = dbh.getPlayer(user.getId());
	    					if (p.getActiveDriver() == null) {
	    						event.getChannel().sendMessage("User does not have an active driver. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveCar() == null) {
	    						event.getChannel().sendMessage("User does not have an active car. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveCar().getDurability() == 0) {
	    						event.getChannel().sendMessage("User's car is currently totaled. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof Training) {
	    						event.getChannel().sendMessage("User's driver is currently training. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof RacePending) {
	    						event.getChannel().sendMessage("User's driver is currently signed up for an event. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof Completed) {
	    						event.getChannel().sendMessage("User's driver needs to collect reward from previous event. Cannot sign up for race.").queue();
	    					}
	    					else if (p.getActiveDriver().getState() instanceof Racing) {
	    						event.getChannel().sendMessage("User's driver is currently racing. Cannot sign up for race.").queue();
	    					}
	    					else {
	    						p.getActiveDriver().signUpForRace(p.getActiveCar(), raceEvent);
	    						event.getChannel().sendMessage("User now registered for the event").queue();
	    					}
	    				}
	    				if (raceEvent.getRaceTrack().getFirstNode() != null) {
	    					if (raceEvent.getTimeElapsed() == 0) {
	    						Player p = dbh.getPlayer(user.getId());
	    						if (p.getActiveDriver() != null) {
	    							if (p.getActiveCar() != null) {
	    								event.getChannel().sendMessage("User now registered for the event").queue();
	    							}
	    							else {
	    								event.getChannel().sendMessage("User does not have an active car. Cannot sign up for race.").queue();
	    							}
	    						}
	    						else {
	    							event.getChannel().sendMessage("User does not have an active driver. Cannot sign up for race.").queue();
	    						}
	    					}
	    					else {
	    						event.getChannel().sendMessage("Event is currently in progress. Unable to join the race.").queue();
	    					}
	    				}
	    				else {
	    					event.getChannel().sendMessage("Event does not yet exist! Create a random one by performing the command: !iracer debug event generate").queue();
	    				}
	    			}
	    			if(args[3].equalsIgnoreCase("begin"))
	    			{
	    				// The event has started! Move every registered driver into a racing state then begin moving the drivers.
	    				Iterator<Driver> driverIterator = raceEvent.getDrivers().iterator();
	    				while (driverIterator.hasNext()) {
	    					driverIterator.next().beginRace();
	    				}
=======
	    			if(args[3].equalsIgnoreCase("begin"))
	    			{
	    				// The event has started! Move every registered driver into a racing state then begin moving the drivers.
	    				//Iterator<Driver> driverIterator = raceEvent.getDrivers().iterator();
	    				//while (driverIterator.hasNext()) {
	    					//driverIterator.next().
	    				//}
>>>>>>> development
	    			}
	    		}
	    		
	    		if(args[2].equalsIgnoreCase("shop"))
	    		{
	    			if(args[3].equalsIgnoreCase("update"))
	    			{
	    				gph.debug();
	    			}
	    			
	    		}
	    		if(args[2].equalsIgnoreCase("driver"))
	    		{
	    			Player p = dbh.getPlayer(user.getId());
	    			if(args[3].equalsIgnoreCase("create"))
		    		{
	    				String driverName = "Dude";
	    				if(args.length > 4 && args[4] != null)
	    				{
	    					driverName = args[4];
	    				}
	    				
	    				// Add the new driver
	    				Driver createdDriver = new Driver(driverName);
	    				createdDriver.setPlayer(p);
	    				createdDriver.setPlayerId(user.getId());
	    				p.getOwnedDrivers().add(createdDriver);
	    				dbh.updateUser(p);
	    				
	    				String capitalizedDriverName = driverName.substring(0, 1).toUpperCase() + driverName.substring(1);
	    				event.getChannel().sendMessage("Driver created! " + capitalizedDriverName + " is now a part of your team.").queue();
		    		}
	    			if(args[3].equalsIgnoreCase("set"))
		    		{
	    				if (args.length > 4 && args[4] != null) {
	    					// User enters name of the driver they wish to use.
	    					String driverName = args[4];
	    					boolean driverFound = false;
	    					Iterator<Driver> ownedDrivers = p.getOwnedDrivers().iterator();
	    					while (ownedDrivers.hasNext()) {
	    						// Searches the player's owned drivers one by one by name.
	    						// If found, set the player's active driver to the one found.
	    						// If not found, send a message saying that driver does not exist.
	    						Driver ownedDriver = ownedDrivers.next();
	    						if (ownedDriver.getName().equals(driverName)) {
	    							driverFound = true;
	    							p.setActiveDriver(ownedDriver);
	    							break;
	    						}
	    					}
	    					if (!driverFound) {
	    						event.getChannel().sendMessage("Could not find that driver. Did not set an active driver. Look at the drivers you own by the command: !iracer debug driver view").queue();
	    					}
	    				}
	    				else {
	    					event.getChannel().sendMessage("No driver name specified! Set an active driver using the command: !iracer debug driver set [driver's name]").queue();
	    				}
		    		}
	    			if(args[3].equalsIgnoreCase("view"))
		    		{
	    				String message = "";
	    				Iterator<Driver> ownedDrivers = p.getOwnedDrivers().iterator();
	    				while (ownedDrivers.hasNext()) {
	    					message += ownedDrivers.next() + "\n_____________________\n";
	    				}
	    				event.getChannel().sendMessage(message).queue();
		    		}
	    			if(args[3].equalsIgnoreCase("active"))
		    		{
	    				event.getChannel().sendMessage(p.getActiveDriver().toString()).queue();
		    		}
	    		}
	    	}
	    	
		    	// A test for filtering an inventory of cars.
		    	/*if(args[1].equalsIgnoreCase("inventory")) {
		    		/*Player somePlayer = new Player();
		    		
		    		int randomNum = ThreadLocalRandom.current().nextInt(0, 49);
		    		
		    		Car carA = new Car(20, randomNum*2, "OEM", randomNum*3);
		    		Car carB = new Car(40, randomNum, "Junkyard", randomNum*4);
		    		Car carC = new Car(60, randomNum, "Lemon", randomNum*2);
		    		Car carD = new Car(80, randomNum*5, "Racing", randomNum/3);
		    		
		    		somePlayer.getOwnedCars().add(carA);
		    		somePlayer.getOwnedCars().add(carB);
		    		somePlayer.getOwnedCars().add(carC);
		    		somePlayer.getOwnedCars().add(carD);
		    		
		    		//Inventory<Car> inventory = new CarInventory();
		    		InventoryIterator<Car> carIterator = somePlayer.getOwnedCars().iterator();
		    		
		    		QualityFilter<Car> filterA = new QualityFilter<Car>(carIterator, "Lemon");
		    		DurabilityFilter<Car> filterB = new DurabilityFilter<Car>(filterA, FilterOperation.IS_GREATER_THAN, 40);
		    		// Print all items with "Junkyard" quality
		    		String result = "Filtered search results:\n";
		    		int carCount = 1;
		    		while (filterB.hasNext()) {
		    			Car car = filterB.next();
		    			if (car != null) {
		    				result += "Car " + carCount + ": " + car + "\n";
		    				carCount++;
		    			}
		    		}
		    		eb.setDescription(result);
		    		event.getChannel().sendMessage(eb.build()).queue();
		    	}*/
					
				//TODO: for debugging only
				
				//Lemon: 0-150
				//Junkyard: 151 - 300
				//OEM: 301 - 750
				//Sports: 751 - 3000
				//Racing: 3001 - 20000
			

			if (args[1].equalsIgnoreCase("test")) {
				
				eb.clear();
				eb.setColor(Color.ORANGE);
				eb.setThumbnail("https://cliply.co/wp-content/uploads/2021/03/372103860_CHECK_MARK_400px.gif");
				eb.setTitle("Demonstration of Abstract Factory creating Components followed by CarBuilder creating the Car");
				
				Component engine = component.createComponent("engine", 5000);
				Component suspension = component.createComponent("suspension", 2999);
				Component wheel = component.createComponent("wheel", 700);
				Component transmission = component.createComponent("transmission", 299);
				Component chassis = component.createComponent("chassis", 99);
				
				CarBuilder car = new Car.CarBuilder();
				
				//suspension and transmission not added to car for testing
				
				car.addEngine(engine);
				//car.addSuspension(suspension);
				car.addWheels(wheel);
				//car.addTransmission(transmission);
				car.addChassis(chassis);
				
				car.build();
				
				//prints out all the generated components
				//eb.setDescription(engine.toString() + suspension.toString() + wheel.toString() + transmission.toString() + chassis.toString());
				
				//prints out the assembled car with ONLY added components
				eb.setDescription(car.toString());	

	
				event.getChannel().sendMessage(eb.build()).queue();
			}
	 }
	}

	 /**
	  * 
	  * @param gp sets the GameplayHandler object reference
	  */
	 public void setGameplayHandler(GameplayHandler gp)
	 {
		 gph = gp;
	 }
	 /**
	  * @return returns a short description of the object.
	  */
	@Override
	public String toString() {
		return "Handles the input Commands";
	}
	/**
	 * Calcualtes and returns object hashcode 
	 * @return calculated hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((dbh == null) ? 0 : dbh.hashCode());
		result = prime * result + ((eb == null) ? 0 : eb.hashCode());
		return result;
	}
	/**
	 * Checks whether two objects are the same or equal instances.
	 * @return true when two objects are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commands other = (Commands) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (dbh == null) {
			if (other.dbh != null)
				return false;
		} else if (!dbh.equals(other.dbh))
			return false;
		if (eb == null) {
			if (other.eb != null)
				return false;
		} else if (!eb.equals(other.eb))
			return false;
		return true;
	}
}