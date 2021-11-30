package com.group3.racingbot;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.ChassisComponent;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ComponentType;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.ComponentFactory.EngineComponent;
import com.group3.racingbot.ComponentFactory.SuspensionComponent;
import com.group3.racingbot.ComponentFactory.TransmissionComponent;
import com.group3.racingbot.ComponentFactory.WheelComponent;
import com.group3.racingbot.driverstate.Intensity;
import com.group3.racingbot.driverstate.Resting;
import com.group3.racingbot.driverstate.Skill;
import com.group3.racingbot.driverstate.Training;
import com.group3.racingbot.gameservice.GameplayHandler;
import com.group3.racingbot.inventory.Inventory;
import com.group3.racingbot.inventory.Iterator;
import com.group3.racingbot.inventory.NotFoundException;
import com.group3.racingbot.inventory.filter.ComponentTypeFilter;
import com.group3.racingbot.inventory.filter.ComposureFilter;
import com.group3.racingbot.inventory.filter.CorneringFilter;
import com.group3.racingbot.inventory.filter.DraftingFilter;
import com.group3.racingbot.inventory.filter.DurabilityFilter;
import com.group3.racingbot.inventory.filter.FilterManager;
import com.group3.racingbot.inventory.filter.FilterOperation;
import com.group3.racingbot.inventory.filter.IteratorDecorator;
import com.group3.racingbot.inventory.filter.Quality;
import com.group3.racingbot.inventory.filter.QualityFilter;
import com.group3.racingbot.inventory.filter.RecoveryFilter;
import com.group3.racingbot.inventory.filter.StraightsFilter;
import com.group3.racingbot.inventory.filter.ValueFilter;
import com.group3.racingbot.inventory.filter.WeightFilter;
import com.group3.racingbot.shop.Shop;
import com.group3.racingbot.standings.DriverStanding;
import com.group3.racingbot.standings.Standings;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Handles Discord user command inputs and interacts with the gameplay handler
 * and DB Handler
 * 
 * @author Maciej Bregisz
 * @author Jack Gola - "factorymethod" command
 * @author Nick Sabia
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
		this.raceEvent = dbh.obtainRecentRaceEvent();
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
	    		/*eb.setDescription("**RacingBot commands:** \n"
	    				+ "**!iracer help or !r ?**\n"
	    				+ "**!iracer register or !r r** | Register with the bot, should be done automaticaly.\n"
	    				+ "**!iracer guess <number 1-50> or !r g <number 1-50** | Bet a certain amount of your Credits, if you win, you double your bet!\n"
	    				+ "**!iracer work or !r w** | Earn credits by performing work every hour! \n"
	    				+ "**!iracer profile *<Optional @mention>* or !r p** | Display your profile or someone elses profile by using @ mentions \n"
	    				+ "**!iracer shops** | Lists the items for sale of all stores. \n"
	    				+ "**!iracer shop (chopshop, junkyard, dealership, importer) or !r s (c,j,d,i)** | Lists the items for sale of a specific store.\n"
	    				+ "**!iracer event register or !r event r** | Register for the upcoming race");*/
	    		eb.setDescription("**RacingBot commands:** \n"
	    				+ "**!r help or !r ?**\n"
	    				+ "**!r register or !r r** | Register with the bot, should be done automaticaly.\n"
	    				+ "**!r guess <number 1-50> or !r g <number 1-50** | Bet a certain amount of your Credits, if you win, you double your bet!\n"
	    				+ "**!r work or !r w** | Earn credits by performing work every hour! \n"
	    				+ "**!r profile *<Optional @mention>* or !r p** | Display your profile or someone elses profile by using @ mentions \n"
	    				+ "\n**Shops**\n"
	    				+ "**!r shops** | Lists the items for sale of all stores. \n"
	    				+ "**!r shop (chopshop, junkyard, dealership, importer) or !r s (c,j,d,i)** | Lists the items for sale of a specific store.\n"
	    				+ "**!r shop (chopshop, junkyard, dealership, importer) buy [id]** | Buys the item by the id listed in the shop.\n"
	    				+ "\n**Events**\n"
	    				+ "**!r event register or !r event r** | Register for the upcoming race"
	    				+ "**!r event generate** | Generate a new race event"
	    				+ "**!r event view** | View the details of a race event, including the race track"
	    				+ "**!r event begin** | Starts a race event"
	    				+ "\n**Claim Rewards**\n"
	    				+ "**!r claim** | If there is a reward for the user's active driver to claim, then collect that reward"
	    				+ "\n**Withdraw From Event or Training**\n"
	    				+ "**!r withdraw** | If your active driver is in the middle of something, this pulls them out of it. The only exception being that you cannot withdraw a driver from a race once they're already racing."
	    				+ "\n**Driver**\n"
	    				+ "**!r driver create [name]** | Create a new driver with the given name."
	    				+ "**!r driver active** | View details about the current active driver"
	    				+ "**!r driver active [driver name]** | Set the current active driver by supplying a driver's name"
	    				+ "**!r driver view** | See all of the drivers which you own"
	    				+ "**!r driver status** | See what your driver is currently up to"
	    				+ "\n**Driver Filtering**\n"
	    				+ "**!r driver filterBy (composure | awareness | drafting | straights | cornering | recovery) (= | != | > | <) (number | String) [(composure | awareness | drafting | straights | cornering | recovery) (= | != | > | <) (number | String)] ** | Retrieve drivers from your inventory which meet a specific criteria. Can use multiple filters at once."
	    				+ "\n**Driver Training**\n"
	    				+ "**!r driver train (awareness | cornering | composure | drafting | straights | recovery) (light | medium | intense)** | Have your driver work on a particular skill. This takes time! The more intense the workout, the longer the training session, but the better the rewards."
	    				+ "\n**Cars**\n"
	    				+ "**!r car blueprint** | For 2000 credits, lets you buy a new blank slate of a car. This car has nothing, it's up to you to build it up."
	    				+ "**!r car disassemble [car id]** | Disassemble a car and store all of its components into your component inventory. WARNING: This will remove the car from your car inventory."
	    				+ "**!r car equip [car id]** | Take a component from your inventory and install it onto your active car."
	    				+ "**!r car unequip (engine | chassis | suspension | wheels | transmission)** | Removes a component from your active car and puts it into your component inventory."
	    				+ "**!r car view** | Look at all the cars within your car inventory."
	    				+ "**!r car active** | View details about your active car."
	    				+ "**!r car filterBy (quality | durability | value | weight) (= | != | > | <) (number | String) [(quality | durability | value | weight) (= | != | > | <) (number | String)]** | Filter for certain cars which meet the given criteria."
	    				+ "\n**Components**\n"
	    				+ "**!r component view** | View all of the components you own."
	    				+ "**!r component filterBy (quality | durability | value | weight) (= | != | > | <) (number | String) [(quality | durability | value | weight) (= | != | > | <) (number | String)]** | Filter for certain components which meet the given criteria."
	    				);
	    		eb.setFooter("Text", "https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/zekroBot_Logo_-_round_small.png?raw=true");
	    		
	    	event.getChannel().sendMessage(eb.build()).queue();
	    		
	    	}
	    	//Handle User registering for bot and other 
	    	if(args[1].equalsIgnoreCase("register") || args[1].equalsIgnoreCase("r"))
	    	{
	    		eb.clear();
	    		try {
	    			if(dbh.userExists(user.getId())){
	    				event.getChannel().sendMessage("User Already Registered!").queue();
		    			Player p = dbh.getPlayer(user.getId());
		    			EmbedBuilder playereb = printPlayer(p,event);
	    				playereb.setThumbnail(user.getUser().getAvatarUrl());
			    		event.getChannel().sendMessage(playereb.build()).queue();
		    			
		    		}else {
		    			event.getChannel().sendMessage("Registering User: " + user.getAsMention() + " with RacingBot!").queue();
		    			
		    			// Create the new player
		    			Player p = new Player();
		    			p.setId(user.getId());
		    			p.setUsername(user.getUser().getName());
		    			p.setLastWorked(0);
		    			Inventory<Driver> driverInventory = p.getOwnedDrivers();
		    			Driver defaultDriver = driverInventory.getItems().get(0);
		    			defaultDriver.setPlayer(p);
		    			defaultDriver.setPlayerId(p.getId());
		    			driverInventory.getItems().set(0, defaultDriver);
		    			p.setOwnedDrivers(driverInventory);
		    			dbh.insertUser(p);
		    			EmbedBuilder playereb = printPlayer(p,event);
		    			playereb.setThumbnail(user.getUser().getAvatarUrl());
			    		event.getChannel().sendMessage(playereb.build()).queue();
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
//		    		if(p.getLastWorked() == 0)
//		    		{
//		    			p.setCredits(p.getCredits() + randomWage);
//		    			p.setLastWorked(System.currentTimeMillis());
//		    			System.out.println(p.toString());
//		    			dbh.updateUser(p);
//		    		}
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
	    		if(args.length >= 3)
	    		{
	    			Member m = event.getMessage().getMentionedMembers().get(0);
	    			if(dbh.userExists(m.getId()))
	    			{
	    				try
	    				{
	    					Player p = dbh.getPlayer(m.getId());
		    				EmbedBuilder playereb = printPlayer(p,event);
		    				playereb.setThumbnail(m.getUser().getAvatarUrl());
				    		event.getChannel().sendMessage(playereb.build()).queue();
	    				}
	    				catch(Exception e) {
	    					event.getChannel().sendMessage("Error!, Issues getting player profile").queue();
	    				}
	    				
	    			}
	    			else {
	    				System.out.println("Could not find that player in database.");
	    				event.getChannel().sendMessage("Could not find that player in database.").queue();
	    				
	    			}
	    			
	    		}else {
	    			try {
	    				String id = event.getAuthor().getId();
	    				if(dbh.userExists(id))
	    				{
	    					Player p = dbh.getPlayer(id);
	    					EmbedBuilder playereb = printPlayer(p,event);
				    		event.getChannel().sendMessage(playereb.build()).queue();
	    				}else {
	    					
	    					Player p = new Player();
	    					p.setId(user.getId());
			    			p.setUsername(user.getUser().getName());
			    			p.setLastWorked(0);
			    			dbh.insertUser(p);
			    			
			    			EmbedBuilder playereb = printPlayer(p,event);
				    		event.getChannel().sendMessage(playereb.build()).queue();
	    				}
	    				Player p = dbh.getPlayer(event.getAuthor().getId());
	    				
		    		}catch(Exception e)
		    		{	
		    			event.getChannel().sendMessage("Error retrieving profile!").queue();
		    			System.out.println("Error retrieving profile: " + e.getMessage());
		    		}	 
	    		}
	    		   		
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
	    					Field field = new Field(components.get(c).getComponentType().toString(), components.get(c).toString(), false);
	    					eb.addField(field);
	    					
	    					//event.getChannel().sendMessage(components.get(c).toString()).queue();
	    					
	    				}
	    				event.getChannel().sendMessage(eb.build()).queue();
	    			}
    		}
	    	
	    	if(args[1].equalsIgnoreCase("shop") || args[1].equalsIgnoreCase("s"))
    		{
	    		if(args.length == 2)
				{
	    			event.getChannel().sendMessage("Shop commands:\n**Chop shop**\n!r shop chopshop\n**Junkyard**\n!r shop junkyard\n**Dealership**\n!r shop dealership\n**Importer**\n!r shop importer").queue();
	    			return;
				}
	    		if(args[2].equalsIgnoreCase("chopshop") || args[2].equalsIgnoreCase("c"))
	    		{
    				if(args.length > 3)
    				{
    					if(args[3].equalsIgnoreCase("buy") || args[3].equalsIgnoreCase("b")) {
    						if(args.length > 4)
    	    				{
    							int id = Integer.parseInt(args[4]);
    							Shop shop = dbh.getShop(0);
    							List<Component> components = shop.getComponentsForSale().getItems();
    							try
    							{
    								Player player = dbh.getPlayer(event.getAuthor().getId());
	    							if(player.getCredits() >= components.get(id).getValue())
	    							{
	    								
	    								Component component = components.get(id);
	    								player.setCredits(player.getCredits()-component.getValue());
	    								player.getOwnedComponents().add(component);
	    								dbh.updateUser(player);
	    								
	    								event.getChannel().sendMessage("Transaction complete! New Credit balance: " + player.getCredits()).queue();
	    								event.getChannel().sendMessage(printComponent(component,event).build()).queue();

	    							}else {
	    								event.getChannel().sendMessage("Transaction failed! Insufficient credits! ").queue();
	    							}
    								
    							}catch(Exception e){
    								System.out.println("Error performing transaction: "+ e.getMessage());
    							}
    							
    	    				}else {
    	    					event.getChannel().sendMessage("Please try again with an id.").queue();
    	    				}
    					}
    				}else {
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
			    			//System.out.println(components.size());
			    					
			    			Field field = new Field(components.get(i).getComponentType().toString(),"#: "+ i +"\n"+ components.get(i).toString(), true);
	    					eb.addField(field);
			    					
			    		}
			    		event.getChannel().sendMessage(eb.build()).queue();
    				}
	    		}
	    		
	    		if(args[2].equalsIgnoreCase("junkyard") || args[2].equalsIgnoreCase("j"))
	    		{
	    			if(args.length > 3)
    				{
    					if(args[3].equalsIgnoreCase("buy") || args[3].equalsIgnoreCase("b")) {
    						if(args.length > 4)
    	    				{
    							int id = Integer.parseInt(args[4]);
    							Shop shop = dbh.getShop(0);
    							List<Component> components = shop.getComponentsForSale().getItems();
    							try
    							{
    								Player player = dbh.getPlayer(event.getAuthor().getId());
	    							if(player.getCredits() >= components.get(id).getValue())
	    							{
	    								Component component = components.get(id);
	    								player.setCredits(player.getCredits()-component.getValue());
	    								player.getOwnedComponents().add(component);
	    								dbh.updateUser(player);
	    								eb.clear();
	    								eb.setTitle(component.getComponentType().toString());
	    								eb.setThumbnail(component.getThumbnailURL());
	    								event.getChannel().sendMessage("Transaction complete! New Credit balance: " + player.getCredits()).queue();
	    								event.getChannel().sendMessage(eb.build()).queue();
	    							}else {
	    								event.getChannel().sendMessage("Transaction failed! Insufficient credits! ").queue();
	    							}
    								
    							}catch(Exception e){
    								System.out.println("Error performing transaction: "+ e.getMessage());
    							}
    							
    							event.getChannel().sendMessage(""+id).queue();
    							
    	    				}else {
    	    					event.getChannel().sendMessage("Please try again with an id.").queue();
    	    				}
    					}
    				}else {
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
			    			//System.out.println(components.size());
			    					
			    			Field field = new Field(components.get(i).getComponentType().toString(),"#: "+ i +"\n"+ components.get(i).toString(), true);
	    					eb.addField(field);
			    					
			    		}
			    		event.getChannel().sendMessage(eb.build()).queue();
    				}
    					
	    		}
	    		if(args[2].equalsIgnoreCase("dealership") || args[2].equalsIgnoreCase("d"))
	    		{
	    			if(args.length > 3)
    				{
    					if(args[3].equalsIgnoreCase("buy") || args[3].equalsIgnoreCase("b")) {
    						if(args.length > 4)
    	    				{
    							int id = Integer.parseInt(args[4]);
    							Shop shop = dbh.getShop(0);
    							List<Component> components = shop.getComponentsForSale().getItems();
    							try
    							{
    								Player player = dbh.getPlayer(event.getAuthor().getId());
	    							if(player.getCredits() >= components.get(id).getValue())
	    							{
	    								player.setCredits(player.getCredits()-components.get(id).getValue());
	    								player.getOwnedComponents().add(components.get(id));
	    								dbh.updateUser(player);
	    								event.getChannel().sendMessage("Transaction complete! New Credit balance: " + player.getCredits()).queue();
	    							}else {
	    								event.getChannel().sendMessage("Transaction failed! Insufficient credits! ").queue();
	    							}
    								
    							}catch(Exception e){
    								System.out.println("Error performing transaction: "+ e.getMessage());
    							}
    							
    							event.getChannel().sendMessage(""+id).queue();
    							
    	    				}else {
    	    					event.getChannel().sendMessage("Please try again with an id.").queue();
    	    				}
    					}
    				}else {
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
			    			//System.out.println(components.size());
			    					
			    			Field field = new Field(components.get(i).getComponentType().toString(),"#: "+ i +"\n"+ components.get(i).toString(), true);
	    					eb.addField(field);
			    					
			    		}
			    		event.getChannel().sendMessage(eb.build()).queue();
    				}
    					
	    		}
	    		if(args[2].equalsIgnoreCase("importer") || args[2].equalsIgnoreCase("i"))
	    		{
	    			if(args.length > 3)
    				{
    					if(args[3].equalsIgnoreCase("buy") || args[3].equalsIgnoreCase("b")) {
    						if(args.length > 4)
    	    				{
    							int id = Integer.parseInt(args[4]);
    							Shop shop = dbh.getShop(0);
    							List<Component> components = shop.getComponentsForSale().getItems();
    							try
    							{
    								Player player = dbh.getPlayer(event.getAuthor().getId());
	    							if(player.getCredits() >= components.get(id).getValue())
	    							{
	    								player.setCredits(player.getCredits()-components.get(id).getValue());
	    								player.getOwnedComponents().add(components.get(id));
	    								dbh.updateUser(player);
	    								event.getChannel().sendMessage("Transaction complete! New Credit balance: " + player.getCredits()).queue();
	    							}else {
	    								event.getChannel().sendMessage("Transaction failed! Insufficient credits! ").queue();
	    							}
    								
    							}catch(Exception e){
    								System.out.println("Error performing transaction: "+ e.getMessage());
    							}
    							
    							event.getChannel().sendMessage(""+id).queue();
    							
    	    				}else {
    	    					event.getChannel().sendMessage("Please try again with an id.").queue();
    	    				}
    					}
    				}else {
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
			    			//System.out.println(components.size());
			    					
			    			Field field = new Field(components.get(i).getComponentType().toString(),"#: "+ i +"\n"+ components.get(i).toString(), true);
	    					eb.addField(field);
			    					
			    		}
			    		event.getChannel().sendMessage(eb.build()).queue();
    				}
    					
	    		}
    		}
	    	if(args[1].equalsIgnoreCase("event") || args[1].equalsIgnoreCase("e"))
    		{
    			if(args[2].equalsIgnoreCase("generate"))
    			{
    				if(args.length > 3 && args[3] != null)
    				{
    					// Create an event using an id the user specifies.
    					String customEventId = args[3];
    					customEventId = customEventId.toLowerCase();
    					
    					if (customEventId.length() > 6) {
    						customEventId = customEventId.substring(0, 5);
    					}
    					
    					this.raceEvent = new RaceEvent();
						this.raceEvent.setId(customEventId);
						this.raceEvent.setStandings(new Standings(customEventId));
						this.raceEvent.setRaceTrack(this.raceEvent.generateRaceTrackFromId());
						this.raceEvent.setGrandPrize(((this.raceEvent.getRaceTrack().calculateTrackLength() + 99) / 100) * 100); // Uses the distance of the track to calculate the grand prize. Rounds to nearest hundred.;
						if (dbh.raceEventExists(customEventId)) {
    						// Overwrite the old event and insert the new one.
							dbh.updateRaceEvent(this.raceEvent);
    					}
						else {
							// Insert new event.
							dbh.insertRaceEvent(this.raceEvent);
						}
						
						event.getChannel().sendMessage("New Event Created: " + customEventId + " | Total Nodes: " + this.raceEvent.getRaceTrack().size() + " | Total Distance: " + this.raceEvent.getRaceTrack().calculateTrackLength()).queue();
    				}
    				else {
    					// Create an event with a randomized id.
    					this.raceEvent = new RaceEvent();
	    				this.raceEvent.initialize();
	    				if (dbh.raceEventExists(this.raceEvent.getId())) {
    						// Overwrite the old event and insert the new one.
							dbh.updateRaceEvent(this.raceEvent);
    					}
						else {
							// Insert new event.
							dbh.insertRaceEvent(this.raceEvent);
						}
	    				event.getChannel().sendMessage("New Event Created: " + this.raceEvent.getId() + " | Total Nodes: " + this.raceEvent.getRaceTrack().size() + " | Total Distance: " + this.raceEvent.getRaceTrack().calculateTrackLength()).queue();
    				}
    				
    			}
    			if(args[2].equalsIgnoreCase("register")) {
    				// Register a user to an event	
					Player p = dbh.getPlayer(user.getId());
					Driver activeDriver = null;
					Car activeCar = null;
					if (p == null) {
						event.getChannel().sendMessage("User does not exist. Cannot sign up for race.").queue();
						return;
					}
					
					activeDriver = p.obtainActiveDriver();
					activeCar = p.obtainActiveCar();
					
					if (activeDriver == null) {
						event.getChannel().sendMessage("User does not have an active driver or the driver doesn't exist in the inventory. Cannot sign up for race.").queue();
						return;
					}
					else if (activeCar == null) {
						event.getChannel().sendMessage("User does not have an active car or the car doesn't exist in the inventory. Cannot sign up for race.").queue();
						return;
					}
					
					if (activeCar.getDurability() == 0) {
						event.getChannel().sendMessage("User's car is currently totaled. Cannot sign up for race.").queue();
					}
					else if (!(activeDriver.getState() instanceof Resting)) {
						event.getChannel().sendMessage(activeDriver.driverStatus()).queue();
					}
					else {
						boolean specifiedRaceEvent = false;
						RaceEvent eventToRegisterFor = this.raceEvent;
						if (args.length > 4 && args[4] != null) {
							// Race event ID specified! Check it to see if there is an event associated with that ID
							String raceEventId = args[4];
							if (dbh.raceEventExists(raceEventId)) {
								eventToRegisterFor = dbh.getRaceEvent(raceEventId);
								specifiedRaceEvent = true;
							}
							else {
								event.getChannel().sendMessage("Event does not yet exist! Create a new one by performing the command: !iracer debug event generate").queue();
								return;
							}
						}
						// Register driver for the given race event.
	    				if (eventToRegisterFor.getTimeElapsed() != 0) {
	    					event.getChannel().sendMessage("Event is currently in progress. Unable to join the race.").queue();
	    				}
	    				else {
	    					// Update the driver
    						activeDriver.signUpForRace(activeCar, eventToRegisterFor);
    						if (p.getOwnedDrivers().update(activeDriver)) {
    							dbh.updateUser(p);
    						}
    						else {
    							event.getChannel().sendMessage("Unable to register user for the event").queue();
    						}
    						
    						// Update the race event
    						eventToRegisterFor.getStandings().addDriver(p.getId(), activeDriver.getId());
    						dbh.updateRaceEvent(eventToRegisterFor);
    						if (!specifiedRaceEvent) {
    							// Update local race event
    							raceEvent = eventToRegisterFor;
    						}
    	    				event.getChannel().sendMessage("User is now registered for the event: " + eventToRegisterFor.getId()).queue();
	    				}
					}
    			}
    			if (args[2].equalsIgnoreCase("view")) {
    				event.getChannel().sendMessage(this.raceEvent.toString()).queue();
    			}
    			if(args[2].equalsIgnoreCase("begin"))
    			{
    				// The event has started! Move every registered driver into a racing state then begin moving the drivers.
    				Iterator<DriverStanding> driverIterator = raceEvent.getStandings().iterator();
    				boolean raceCanBegin = true;
    				Player p = null;
    				while (driverIterator.hasNext()) {
    					Driver currentDriver = driverIterator.next().getDriver();
    					p = dbh.getPlayer(currentDriver.getPlayerId());
    					currentDriver.beginRace();
    					if (p.getOwnedDrivers().update(currentDriver)) {
							dbh.updateUser(p);
						}
						else {
							raceCanBegin = false;
							event.getChannel().sendMessage("Unable to update driver with id: " + currentDriver.getId() +". Unable to begin race.").queue();
						}
    				}
    				
    				// If there were any issues updating the states of any of the drivers, don't begin the race.
    				if (!raceCanBegin) {
    					return;
    				}
    				
    				// This timer will go off every 2 seconds until the race is done.
    				Timer timer = new Timer();
    				TimerTask raceStepAllTask = new TimerTask () {
    				    @Override
    				    public void run () {
    				    	if (!raceEvent.isFinished()) {
	    						System.out.println("Race Step");
	    						event.getChannel().sendMessage(raceEvent.stepAllDrivers()).queue();
    				    	}
    				    	else {
    				    		event.getChannel().sendMessage(printRaceResults()).queue();
    				    		this.cancel(); // End the task so that it doesn't keep running.
    				    	}
    				    }
    				};

    				final int TWO_SECONDS = 1000 * 2;
    				timer.scheduleAtFixedRate(raceStepAllTask, 1000, TWO_SECONDS);
    			}
    		}
	    	if(args[1].equalsIgnoreCase("claim")) {
    			Player p = dbh.getPlayer(user.getId());
    			try {
    				Driver activeDriver = p.getOwnedDrivers().getById(p.getActiveDriverId());
    				event.getChannel().sendMessage(activeDriver.collectReward()).queue();
    			}
    			catch(NotFoundException e) {
    				event.getChannel().sendMessage("Unable to retrieve Driver " + p.getActiveDriverId() + ". Cannot claim a reward.").queue();
    			}
    		}
	    	if(args[1].equalsIgnoreCase("component") || args[1].equalsIgnoreCase("components")) {
	    		Player p = dbh.getPlayer(user.getId());
	    		if (args[2].equalsIgnoreCase("view")) {
    				String results = "";
    				Iterator<Component> iterator = p.getOwnedComponents().iterator();
    				
    				int index = 1;
					while (iterator.hasNext()) {
						results += index + ". | " + iterator.next() + "\n";
						index++;
					}
    				
    				if (results != "") {
    					event.getChannel().sendMessage(results).queue();
    				}
    				else {
    					event.getChannel().sendMessage("Inventory is empty.").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("filterBy")) {
    				String errorMsg = "Invalid command. The syntax for the add filter command is as follows:\n!r driver filterBy filterType operator (number | String) [filterType operator (number | String)]";
    				Player updatedPlayer = p;
    				
    				// If there are enough args for one filter or if there are a correct amount of args for more filters, then continue.
    				boolean validAmountOfArgs = args.length >= 6 && args.length % 3 == 0;
    				if (validAmountOfArgs) {
    					int totalFilters = (args.length - 3) / 3;
    					for (int i = 0; i < totalFilters; i++) {
    						int referenceArg = (i+1)*3;
    						updatedPlayer = addFilter(p, event, referenceArg, errorMsg);
    					}
    				}
    				Iterator<Component> originalIterator = updatedPlayer.getOwnedComponents().iterator();
    				IteratorDecorator<Component> filteredIterator = updatedPlayer.getOwnedComponents().getFilterManager().applyFilters(originalIterator);
    				
    				// There were no results.
    				if (filteredIterator == null) {
    					event.getChannel().sendMessage("No results for that filtered query.").queue();
    					return;
    				}
    				
    				String results = "";
    				
    				while (filteredIterator.hasNext()) {
    					Component current = filteredIterator.next();
    					if (current != null) {
    						results += current.toString() + "\n";
    					}
    				}
    				
    				if (results != "") {
    					event.getChannel().sendMessage(results).queue();
    				}
    				else {
    					event.getChannel().sendMessage("No results for that filtered query.").queue();
    				}
    			}
	    	}
	    	if(args[1].equalsIgnoreCase("driver") || args[1].equalsIgnoreCase("drivers"))
    		{
    			Player p = dbh.getPlayer(user.getId());
    			if(args[2].equalsIgnoreCase("create"))
	    		{
    				String driverName = "Dude";
    				if(args.length > 3 && args[3] != null)
    				{
    					driverName = args[3];
    				}
    				
    				// Check that a driver with the same name doesn't already exist.
    				//try {
    					// If this doesn't throw anything, the driver already exists.
    				//	p.getOwnedDrivers().getByName(driverName);
    				//}
    				//catch (NotFoundException e) {
    					// That driver does not yet exist.
    					// Add the new driver
    				Driver createdDriver = new Driver(driverName);
    				createdDriver.setId(dbh.generateId(6));
    				createdDriver.setPlayer(p);
    				createdDriver.setPlayerId(p.getId());
    				p.getOwnedDrivers().add(createdDriver);
    				dbh.updateUser(p);
    				
    				String capitalizedDriverName = driverName.substring(0, 1).toUpperCase() + driverName.substring(1);
    				event.getChannel().sendMessage("Driver created! " + capitalizedDriverName + " is now a part of your team.").queue();
    				//}
	    		}
    			if(args[2].equalsIgnoreCase("active"))
	    		{
    				if (args.length > 3 && args[3] != null) {
    					// User enters name of the driver they wish to use.
    					String driverName = args[3];
    					boolean driverFound = false;
    					Iterator<Driver> ownedDrivers = p.getOwnedDrivers().iterator();
    					while (ownedDrivers.hasNext()) {
    						// Searches the player's owned drivers one by one by name.
    						// If found, set the player's active driver to the one found.
    						// If not found, send a message saying that driver does not exist.
    						Driver ownedDriver = ownedDrivers.next();
    						if (ownedDriver.getName().equals(driverName)) {
    							driverFound = true;
    							p.setActiveDriverId(ownedDriver.getId());
    							dbh.updateUser(p);
    							event.getChannel().sendMessage("Driver set. \n" + ownedDriver.toString()).queue();
    							break;
    						}
    					}
    					if (!driverFound) {
    						event.getChannel().sendMessage("Could not find that driver. Did not set an active driver. Look at the drivers you own by the command: !iracer debug driver view").queue();
    					}
    				}
    				else {
    					event.getChannel().sendMessage(p.getActiveDriverId().toString()).queue();
    				}
	    		}
    			if (args[2].equalsIgnoreCase("view")) {
    				String results = "";
    				Iterator<Driver> iterator = p.getOwnedDrivers().iterator();
    				
    				int index = 1;
					while (iterator.hasNext()) {
						results += index + ". | " + iterator.next() + "\n";
						index++;
					}
    				
    				if (results != "") {
    					event.getChannel().sendMessage(results).queue();
    				}
    				else {
    					event.getChannel().sendMessage("Inventory is empty.").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("filterBy")) {
    				String errorMsg = "Invalid command. The syntax for the add filter command is as follows:\n!r driver filterBy filterType operator (number | String) [filterType operator (number | String)]";
    				Player updatedPlayer = p;
    				
    				// If there are enough args for one filter or if there are a correct amount of args for more filters, then continue.
    				boolean validAmountOfArgs = args.length >= 6 && args.length % 3 == 0;
    				if (validAmountOfArgs) {
    					int totalFilters = (args.length - 3) / 3;
    					for (int i = 0; i < totalFilters; i++) {
    						int referenceArg = (i+1)*3;
    						updatedPlayer = addFilter(p, event, referenceArg, errorMsg);
    					}
    				}
    				Iterator<Driver> originalIterator = updatedPlayer.getOwnedDrivers().iterator();
    				IteratorDecorator<Driver> filteredIterator = updatedPlayer.getOwnedDrivers().getFilterManager().applyFilters(originalIterator);
    				
    				// There were no results.
    				if (filteredIterator == null) {
    					event.getChannel().sendMessage("No results for that filtered query.").queue();
    					return;
    				}
    				
    				String results = "";
    				
    				while (filteredIterator.hasNext()) {
    					Driver current = filteredIterator.next();
    					if (current != null) {
    						results += current.toString() + "\n";
    					}
    				}
    				
    				if (results != "") {
    					event.getChannel().sendMessage(results).queue();
    				}
    				else {
    					event.getChannel().sendMessage("No results for that filtered query.").queue();
    				}
    			}
    			if(args[2].equalsIgnoreCase("status")) {
    				// Gets the current state of the active driver.
    				try {
    					Driver activeDriver = p.getOwnedDrivers().getById(p.getActiveDriverId());
    					event.getChannel().sendMessage(activeDriver.driverStatus()).queue();
    				}
    				catch (NotFoundException e) {
    					event.getChannel().sendMessage("Unable to retrieve Driver " + p.getActiveDriverId() + ". Cannot check their status.").queue();
    					e.printStackTrace();
    				}
    			}
    			if (args[2].equalsIgnoreCase("train")) {
    				String inputErrorHelpText = "Invalid input. The syntax for training is as follows:\n!r driver train (awareness | cornering | composure | drafting | straights | recovery) (light | medium | intense)\n!r driver train (a | cor | com | d | s | r) (l | m | i)";
    				// Check to make sure there are enough args in the command. If not, print the instructions for how to properly use the command.
    				if (args.length <= 4) { 
    					event.getChannel().sendMessage(inputErrorHelpText).queue();
    					return;
    				}
    				
    				Driver activeDriver = null;
    				try {
    					activeDriver = p.getOwnedDrivers().getById(p.getActiveDriverId());
    				}
    				catch (NotFoundException e) {
    					event.getChannel().sendMessage("Unable to retrieve Driver " + p.getActiveDriverId() + ". Cannot start training.").queue();
    					e.printStackTrace();
    				}
					
					boolean isAwareness = args[3].equals("a") || args[3].equals("awa") || args[3].equals("awareness"),
							isCornering = args[3].equals("cor") || args[3].equals("cornering"),
							isComposure = args[3].equals("com") || args[3].equals("composure"),
							isDrafting  = args[3].equals("d") || args[3].equals("dra") || args[3].equals("drafting"),
							isStraights = args[3].equals("s") || args[3].equals("str") || args[3].equals("straights"),
							isRecovery  = args[3].equals("r") || args[3].equals("rec") || args[3].equals("recovery"),
							isValidSkill = isAwareness || isCornering || isComposure || isDrafting || isStraights || isRecovery;
					if (isValidSkill) {					
						boolean isLight   = args[4].equals("l") || args[4].equals("light"),
    							isMedium  = args[4].equals("m") || args[4].equals("medium"),
    							isIntense = args[4].equals("i") || args[4].equals("intense"),
    							isValidIntensity = isLight || isMedium || isIntense;
						if (isValidIntensity) {
							Skill skillToTrain = Skill.AWARENESS;
							if (isCornering) {
								skillToTrain = Skill.CORNERING;
							}
							else if (isComposure) {
								skillToTrain = Skill.COMPOSURE;
							}
							else if (isDrafting) {
								skillToTrain = Skill.DRAFTING;
							}
							else if (isStraights) {
								skillToTrain = Skill.STRAIGHTS;
							}
							else if (isRecovery) {
								skillToTrain = Skill.RECOVERY;
							}
							
							Intensity trainingIntensity = Intensity.LIGHT;
							if (isMedium) {
								trainingIntensity = Intensity.MEDIUM;
							}
							else if (isIntense) {
								trainingIntensity = Intensity.INTENSE;
							}
							
							// Set the driver into a training state
							activeDriver.beginTraining(skillToTrain, trainingIntensity);
							((Training) activeDriver.getState()).activateTimer(event); // Starts the clock for how long the user should wait until training completes. The user will be notified once training is complete.
							event.getChannel().sendMessage(activeDriver.getName() + " is now performing " + trainingIntensity.toString().toLowerCase() + " training for their " + skillToTrain.toString().toLowerCase() + " skill. " + activeDriver.getName() + " will complete training in " + ((Training) activeDriver.getState()).printTimeRemaining() + ".").queue();
							p.getOwnedDrivers().update(activeDriver);
							dbh.updateUser(p);
						}
						else {
							event.getChannel().sendMessage(inputErrorHelpText).queue();
						}
					}
					else {
						event.getChannel().sendMessage(inputErrorHelpText).queue();
					}
    			}
    		}
	    	if (args[1].equalsIgnoreCase("withdraw")) {
				Player p = dbh.getPlayer(user.getId());
				if (p.obtainActiveDriver() != null) {
					Driver activeDriver = p.obtainActiveDriver();
					String successMsg = activeDriver.rest();
					
					if (p.getOwnedDrivers().update(activeDriver)) {
						dbh.updateUser(p);
						event.getChannel().sendMessage(successMsg).queue();
					}
					else {
						event.getChannel().sendMessage("Unable to withdraw user from the event").queue();
					}
				}
				else {
					event.getChannel().sendMessage("Player does not have an active driver").queue();
				}
			}
	    	if(args[1].equalsIgnoreCase("car") || args[1].equalsIgnoreCase("cars")) 
    		{
    			Player p = dbh.getPlayer(user.getId());
    			if (args[2].equalsIgnoreCase("free"))
	    		{
    				int value = 100;
    				if (args.length > 3 && args[3] != null) {
    					value = Integer.parseInt(args[3]);
    				}
    				// Create the car
    				Car freeCar = new Car();
    				String carId = dbh.generateId(6);
    				freeCar.setId(carId);
    				ConcreteComponentFactory componentFactory = new ConcreteComponentFactory();
    				EngineComponent engine = (EngineComponent) componentFactory.createComponent("engine", value);
    				TransmissionComponent transmission = (TransmissionComponent) componentFactory.createComponent("transmission", value);
    				SuspensionComponent suspension = (SuspensionComponent) componentFactory.createComponent("suspension", value);
    				ChassisComponent chassis = (ChassisComponent) componentFactory.createComponent("chassis", value);
    				WheelComponent wheels = (WheelComponent) componentFactory.createComponent("wheel", value);
    				freeCar.setEngine(engine);
    				freeCar.setChassis(chassis);
    				freeCar.setSuspension(suspension);
    				freeCar.setTransmission(transmission);
    				freeCar.setWheels(wheels);
    				p.getOwnedCars().add(freeCar);
    				dbh.updateUser(p);
    				event.getChannel().sendMessage("A free car (" + carId + ") was added to your garage.").queue();
	    		}
    			if (args[2].equalsIgnoreCase("blueprint")) {
    				if (p.getCredits() >= 2000) {
    					Inventory<Car> updatedCars = p.getOwnedCars();
    					Car newCar = new Car();
    					newCar.setId(dbh.generateId(6));
    					updatedCars.add(newCar);
    					p.setOwnedCars(updatedCars);
    					p.setCredits(p.getCredits() - 2000);
    					dbh.updateUser(p);
    					event.getChannel().sendMessage("Car blueprint purchased for 2000 credits. You now have " + p.getCredits() + " credits.").queue();
    				}
    				else {
    					event.getChannel().sendMessage("You don't have enough credits to purchase a car blueprint. A car blueprint costs 2000 credits, you have " + p.getCredits() + " credits.").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("disassemble")) {
    				if(args.length > 3 && args[3] != null)
    				{
    					try {
    						String carId = args[3].toString();
    						//int index = Integer.parseInt(args[4].toString());
    						Inventory<Car> updatedCarInventory = p.getOwnedCars();
    						Inventory<Component> updatedComponentInventory = p.getOwnedComponents();
    						Car carToRemove = updatedCarInventory.getById(carId);
    						
    						// Extract all components from the car into the player's component inventory.
    						if (carToRemove.getEngine() != null) {
    							updatedComponentInventory.add(carToRemove.getEngine());
    						}
    						if (carToRemove.getChassis() != null) {
    							updatedComponentInventory.add(carToRemove.getChassis());
    						}
    						if (carToRemove.getSuspension() != null) {
    							updatedComponentInventory.add(carToRemove.getSuspension());
    						}
    						if (carToRemove.getTransmission() != null) {
    							updatedComponentInventory.add(carToRemove.getTransmission());
    						}
    						if (carToRemove.getWheels() != null) {
    							updatedComponentInventory.add(carToRemove.getWheels());
    						}
    						
    						updatedCarInventory.remove(carToRemove);
    						p.setOwnedCars(updatedCarInventory);
    						p.setOwnedComponents(updatedComponentInventory);
    						dbh.updateUser(p);
    						event.getChannel().sendMessage("Car disassembled! All parts which were in the car are now in your component inventory.\n**View Component Inventory**\n!r component view").queue();
    					}
    					catch (Exception e) {
    						event.getChannel().sendMessage("A car with that id could not be found. Did not disassemble a car.\n**View Car Inventory**\n!r car view").queue();
    					}
    				}
    				else {
    					event.getChannel().sendMessage("Invalid syntax. Below shows how you use this command:\n**Equip component to a car**\n!r car disassemble [car id]").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("equip")) {
    				if(args.length > 3 && args[3] != null)
    				{
    					try {
    						String componentId = args[3].toString();
    						//int index = Integer.parseInt(args[4].toString());
    						Inventory<Car> updatedCarInventory = p.getOwnedCars();
    						Inventory<Component> updatedComponentInventory = p.getOwnedComponents();
    						Car activeCar = updatedCarInventory.getById(p.getActiveCarId());
    						Component componentToEquip = updatedComponentInventory.getById(componentId);
    						Component componentToUnequip = null;
    						// Replace the component of the car with the same type as the component we're trying to equip.
    						switch (componentToEquip.getComponentType()) {
    							case ENGINE:
    								componentToUnequip = activeCar.getEngine();
    								if (componentToUnequip != null) {
    	    							activeCar.setEngine((EngineComponent) componentToEquip);
    	    						}
    								break;
    							case CHASSIS:
    								componentToUnequip = activeCar.getChassis();
    								if (componentToUnequip != null) {
    	    							activeCar.setChassis((ChassisComponent) componentToEquip);
    	    						}
    								break;
    							case SUSPENSION:
    								componentToUnequip = activeCar.getSuspension();
    								if (componentToUnequip != null) {
    	    							activeCar.setSuspension((SuspensionComponent) componentToEquip);
    	    						}
    								break;
    							case TRANSMISSION:
    								componentToUnequip = activeCar.getTransmission();
    								if (componentToUnequip != null) {
    	    							activeCar.setTransmission((TransmissionComponent) componentToEquip);
    	    						}
    								break;
    							case WHEELS:
    								componentToUnequip = activeCar.getWheels();
    								if (componentToUnequip != null) {
    	    							activeCar.setWheels((WheelComponent) componentToEquip);
    	    						}
    								break;
    							default:
    								event.getChannel().sendMessage("At least one component holds an unknown component type. Unable to equip component " + componentId + ".").queue();
    								return;
    						}
    						// Store the component into the player's component inventory
    						if (componentToUnequip != null) {
    							updatedComponentInventory.add(componentToUnequip);
    						}
    						updatedComponentInventory.remove(componentToEquip);
    						
    						p.setOwnedCars(updatedCarInventory);
    						p.setOwnedComponents(updatedComponentInventory);
    						dbh.updateUser(p);
    						event.getChannel().sendMessage("Successfully equipped " + componentToEquip.getComponentType().toString().toLowerCase() + " " + componentId + " to car " + activeCar.getId() + "!\n**View Component Inventory**\n!r component view\n**View Car Inventory**\n!r car view").queue();
    					}
    					catch (Exception e) {
    						event.getChannel().sendMessage("A component with that id could not be found. Did not equip component to active car.\n**View Component Inventory**\n!r component view").queue();
    					}
    				}
    				else {
    					event.getChannel().sendMessage("Invalid syntax. This command allows you to equip components to your active car. Below shows how you use this command:\n**Equip component to active car**\n!r car equip [component id]").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("unequip")) {
    				if(args.length > 3 && args[3] != null)
    				{
    					try {
    						String partTypeToUnequip = args[3].toString().toLowerCase(); // This holds the string engine, transmission, chassis, etc.
    						Component partToUnequip = null; // This holds an instance of component from the active car (if the active car actually has a component already equipped)
    						Inventory<Car> updatedCarInventory = p.getOwnedCars();
    						Inventory<Component> updatedComponentInventory = p.getOwnedComponents();
    						Car activeCar = updatedCarInventory.getById(p.getActiveCarId());
    						switch(partTypeToUnequip) {
    							case "engine": case "e":
    								partToUnequip = activeCar.getEngine();
    								activeCar.setEngine(null);
    								break;
    							case "transmission": case "t":
    								partToUnequip = activeCar.getTransmission();
    								activeCar.setTransmission(null);
    								break;
    							case "suspension": case "s":
    								partToUnequip = activeCar.getSuspension();
    								activeCar.setSuspension(null);
    								break;
    							case "wheels": case "wheel": case "w":
    								partToUnequip = activeCar.getWheels();
    								activeCar.setWheels(null);
    								break;
    							case "chassis": case "c":
    								partToUnequip = activeCar.getChassis();
    								activeCar.setChassis(null);
    								break;
    							default:
    								event.getChannel().sendMessage("Invalid syntax. This command allows you to unequip components from your active car. Below shows how you use this command:\n**Unequip component from active car**\n!r car unequip (engine | transmission | chassis | suspension | wheels)\n**Shorthand version of unequip command**\n!r car unequip (e | t | c | s | w)").queue();
    								break;
    						}
    						if (partToUnequip != null) {
    							updatedComponentInventory.add(partToUnequip);
    							// Update the active car so that the unequip action persists in the database.
    							updatedCarInventory.update(activeCar);
    							p.setOwnedCars(updatedCarInventory);
    							// Make sure the newly unequipped component gets stored into the player's component inventory.
        						p.setOwnedComponents(updatedComponentInventory);
        						dbh.updateUser(p);
        						event.getChannel().sendMessage("Successfully unequipped " + partToUnequip.getComponentType().toString().toLowerCase() + " " + partToUnequip.getId() + " from car " + activeCar.getId() + "!\n**View Component Inventory**\n!r component view\n**View Car Inventory**\n!r car view").queue();
    						}
    						else {
    							event.getChannel().sendMessage("Unable to unequip component from car " + activeCar.getId() + " because the car is missing that component already.\n**View active car components**\n!r car active detail").queue();
    						}
    					}
    					catch (Exception e) {
    						event.getChannel().sendMessage("A component with that id could not be found. Did not unequip component from active car.\n**View active car's components**\n!r car active detail").queue();
    					}
    				}
    				else {
    					event.getChannel().sendMessage("Invalid syntax. This command allows you to unequip components from your active car. Below shows how you use this command:\n**Unequip component from active car**\n!r car unequip [component id]").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("view")) {
    				String results = "";
    				Iterator<Car> iterator = p.getOwnedCars().iterator();
    				
    				int index = 1;
					while (iterator.hasNext()) {
						results += index + ". | " + iterator.next() + "\n";
						index++;
					}
    				
    				if (results != "") {
    					event.getChannel().sendMessage(results).queue();
    				}
    				else {
    					event.getChannel().sendMessage("Inventory is empty.").queue();
    				}
    			}
    			if (args[2].equalsIgnoreCase("active")) {
    				if(args.length > 3 && args[3] != null)
    				{
    					if (args[3].equalsIgnoreCase("detail")) {
    						// View the car's individual components
    						try {
    							Car activeCar = p.getOwnedCars().getById(p.getActiveCarId());
    							String result = "";
    							result += activeCar.getChassis() + "\n";
    							result += activeCar.getSuspension() + "\n";
    							result += activeCar.getWheels() + "\n";
    							result += activeCar.getTransmission() + "\n";
    							result += activeCar.getEngine();
    							event.getChannel().sendMessage("**Your Active Car - Detailed**\n" + result + "\nTo change cars, use the command below:\n**Switch active car**\n!r car active [car id]").queue();
    						}
    						catch (NotFoundException e) {
        						//e.printStackTrace();
        						event.getChannel().sendMessage("A car with that id could not be found. Cannot view active car.\n**View Car Inventory**\n!r car view").queue();
        					}
    					}
    					else {
    						// Attempt to set an active car
    						try {
        						String carId = args[3].toString();
        						Car activeCar = p.getOwnedCars().getById(carId);
        						p.setActiveCarId(activeCar.getId());
        						dbh.updateUser(p);
        						event.getChannel().sendMessage("Active car set!\n" + activeCar).queue();
        					}
        					catch (NotFoundException e) {
        						//e.printStackTrace();
        						event.getChannel().sendMessage("A car with that id could not be found. Did not change active car.\n**View Car Inventory**\n!r car view").queue();
        					}
    					}
    				}
    				else {
    					Car activeCar = null;
    					try {
    						activeCar = p.getOwnedCars().getById(p.getActiveCarId());
    						event.getChannel().sendMessage("**Your Active Car**\n" + activeCar.toString() + "\nTo change cars, use the command below:\n**Switch active car**\n!r car active [car id]").queue();
    					}
    					catch (NotFoundException e) {
    						event.getChannel().sendMessage("**Your Active Car**\nYou currently don't have an active car. To change cars, use the command below:\n**Switch active car**\n!r car active [car id]").queue();
    					}
    				}
    				
    			}
    			if (args[2].equalsIgnoreCase("filterBy")) {
    				String errorMsg = "Invalid command. The syntax for the add filter command is as follows:\n!r car filterBy filterType operator (number | String) [filterType operator (number | String)]";
    				Player updatedPlayer = p;
    				
    				// If there are enough args for one filter or if there are a correct amount of args for more filters, then continue.
    				boolean validAmountOfArgs = args.length >= 6 && args.length % 3 == 0;
    				if (validAmountOfArgs) {
    					int totalFilters = (args.length - 3) / 3;
    					for (int i = 0; i < totalFilters; i++) {
    						int referenceArg = (i+1)*3;
    						updatedPlayer = addFilter(p, event, referenceArg, errorMsg);
    					}
    				}
    				Iterator<Car> originalIterator = updatedPlayer.getOwnedCars().iterator();
    				IteratorDecorator<Car> filteredIterator = updatedPlayer.getOwnedCars().getFilterManager().applyFilters(originalIterator);
    				
    				// There were no results.
    				if (filteredIterator == null) {
    					event.getChannel().sendMessage("No results for that filtered query.").queue();
    					return;
    				}
    				
    				String results = "";
    				
    				while (filteredIterator.hasNext()) {
    					Car current = filteredIterator.next();
    					if (current != null) {
    						results += current.toString() + "\n";
    					}
    				}
    				
    				if (results != "") {
    					event.getChannel().sendMessage(results).queue();
    				}
    				else {
    					event.getChannel().sendMessage("No results for that filtered query.").queue();
    				}
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
	    		if(args[2].equalsIgnoreCase("shop"))
	    		{
	    			if(args[3].equalsIgnoreCase("update"))
	    			{
	    				gph.debug();
	    			}
	    			
	    		}
	    	}
	    	if (args[1].equalsIgnoreCase("test")) {
				
				eb.clear();
				eb.setColor(Color.ORANGE);
				eb.setThumbnail("https://cliply.co/wp-content/uploads/2021/03/372103860_CHECK_MARK_400px.gif");
				eb.setTitle("Demonstration of Abstract Factory creating Components followed by CarBuilder creating the Car");
				
				Component engine = component.createComponent("engine", 100);
				Component suspension = component.createComponent("suspension", 250);
				Component wheel = component.createComponent("wheel", 800);
				Component transmission = component.createComponent("transmission", 499);
				Component chassis = component.createComponent("chassis", 4500);
				
				CarBuilder car = new Car.CarBuilder();
				
				//suspension and transmission not added to car for testing
				
				car.addEngine(engine);
				car.addSuspension(suspension);
				car.addWheels(wheel);
				car.addTransmission(transmission);
				car.addChassis(chassis);
				
				car.build();
				
				//prints out all the generated components
				//eb.setDescription(engine.toString() + suspension.toString() + wheel.toString() + transmission.toString() + chassis.toString());
				
				//prints out the assembled car with ONLY added components
				eb.setDescription(car.toString());	

	
				event.getChannel().sendMessage(eb.build()).queue();
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
		    		Iterator<Car> carIterator = somePlayer.getOwnedCars().iterator();
		    		
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
	}
	 
	/**
	 * Retrieve a string which prints the results of a race. This is a helper function which lets the results get printed from within the race task.
	 * @return string containing info about the final standings of the race.
	 */
	private String printRaceResults() {
		return "RaceEvent " + this.raceEvent.getId() + " is complete!\n_____\n" + this.raceEvent.getStandings().toString();
	}
	
	/**
	 * Add a filter to a player's inventories
	 * @param player
	 * @param event
	 * @return
	 */
	private Player addFilter(Player p, GuildMessageReceivedEvent event, int referenceArg, String errorMsg) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		// !r filter add [filter] String
		// !r filter add String String_to_
		if (args[referenceArg] == null || args[referenceArg + 1] == null || args[referenceArg + 2] == null) {
			event.getChannel().sendMessage("arg null error: " + errorMsg).queue();
			return p;
		}
		
		// Grab the filter operation to perform.
		FilterOperation filterOp = null;
		switch (args[referenceArg + 1].toString()) {
			case ">":
				filterOp = FilterOperation.IS_GREATER_THAN;
				break;
			case "<":
				filterOp = FilterOperation.IS_LESS_THAN;
				break;
			case "=": case "==":
				filterOp = FilterOperation.IS_EQUAL;
				break;
			case "!=": case "<>":
				filterOp = FilterOperation.IS_NOT_EQUAL;
				break;
			default:
				break;
		}
		if (filterOp == null) {
			// If the filter operation wasn't successfully parsed, then return.
			event.getChannel().sendMessage("filterOp error: " + errorMsg).queue();
			return p;
		}
		
		// Grab the numeric (or string) criteria for the filtering
		String criteriaStr = args[referenceArg + 2].toString();
		int criteriaNum = -1;
		try {
			criteriaNum = Integer.parseInt(criteriaStr);
		}
		catch (NumberFormatException e) {
			// Do nothing
		}
		
		if (criteriaNum >= 0) {
			switch (args[referenceArg].toLowerCase()) {
				case "durability":
					p.getOwnedCars().getFilterManager().add(new DurabilityFilter<Car>(null, filterOp, criteriaNum));
					p.getOwnedComponents().getFilterManager().add(new DurabilityFilter<Component>(null, filterOp, criteriaNum));
					break;
				case "value":
					p.getOwnedCars().getFilterManager().add(new ValueFilter<Car>(null, filterOp, criteriaNum));
					p.getOwnedComponents().getFilterManager().add(new ValueFilter<Component>(null, filterOp, criteriaNum));
					break;
				case "weight":
					p.getOwnedCars().getFilterManager().add(new WeightFilter<Car>(null, filterOp, criteriaNum));
					p.getOwnedComponents().getFilterManager().add(new WeightFilter<Component>(null, filterOp, criteriaNum));
					break;
				case "composure":
					p.getOwnedDrivers().getFilterManager().add(new ComposureFilter<Driver>(null, filterOp, criteriaNum));
					break;
				case "awareness":
					p.getOwnedDrivers().getFilterManager().add(new ComposureFilter<Driver>(null, filterOp, criteriaNum));
					break;
				case "drafting":
					p.getOwnedDrivers().getFilterManager().add(new DraftingFilter<Driver>(null, filterOp, criteriaNum));
					break;
				case "straights": case "straight":
					p.getOwnedDrivers().getFilterManager().add(new StraightsFilter<Driver>(null, filterOp, criteriaNum));
					break;
				case "cornering": case "corner":
					p.getOwnedDrivers().getFilterManager().add(new CorneringFilter<Driver>(null, filterOp, criteriaNum));
					break;
				case "recovery":
					p.getOwnedDrivers().getFilterManager().add(new RecoveryFilter<Driver>(null, filterOp, criteriaNum));
					break;
				default:
					event.getChannel().sendMessage("invalid string for numeric criteria error: " + errorMsg).queue();
					break;
			}
			return p;
		}
		else {
			// Assume the user has entered a string
			boolean hasError = false;
			if (args[referenceArg].toLowerCase().equals("quality")) {
				// The user is filtering by quality.
				switch (args[referenceArg + 2].toLowerCase()) {
					case "lemon": case "l": 
						p.getOwnedCars().getFilterManager().add(new QualityFilter<Car>(null, filterOp, Quality.LEMON));
						p.getOwnedComponents().getFilterManager().add(new QualityFilter<Component>(null, filterOp, Quality.LEMON));
						break;
					case "junkyard": case "j": case "junk": 
						p.getOwnedCars().getFilterManager().add(new QualityFilter<Car>(null, filterOp, Quality.JUNKYARD));
						p.getOwnedComponents().getFilterManager().add(new QualityFilter<Component>(null, filterOp, Quality.JUNKYARD));
						break;
					case "oem": case "o": 
						FilterManager<Car> filterManager = p.getOwnedCars().getFilterManager();
						filterManager.add(new QualityFilter<Car>(null, filterOp, Quality.OEM));
						p.getOwnedCars().setFilterManager(filterManager);
						//p.getOwnedCars().getFilterManager().add(new QualityFilter<Car>(null, filterOp, Quality.OEM));
						p.getOwnedComponents().getFilterManager().add(new QualityFilter<Component>(null, filterOp, Quality.OEM));
						break;
					case "sports": case "s": case "sport":
						p.getOwnedCars().getFilterManager().add(new QualityFilter<Car>(null, filterOp, Quality.SPORTS));
						p.getOwnedComponents().getFilterManager().add(new QualityFilter<Component>(null, filterOp, Quality.SPORTS));
						break;
					case "racing": case "r":
						p.getOwnedCars().getFilterManager().add(new QualityFilter<Car>(null, filterOp, Quality.RACING));
						p.getOwnedComponents().getFilterManager().add(new QualityFilter<Component>(null, filterOp, Quality.RACING));
						break;
					default:
						break;
				}
			}
			else if (args[referenceArg].toLowerCase().equals("component")) {
				// The user is filtering by component type.
				switch(args[referenceArg + 2].toLowerCase()) {
					case "engine":
						if (filterOp == FilterOperation.IS_EQUAL || filterOp == FilterOperation.IS_NOT_EQUAL) {
							p.getOwnedComponents().getFilterManager().add(new ComponentTypeFilter<Component>(null, filterOp, ComponentType.ENGINE));
						}
						else {
							hasError = true;
						}
						break;
					case "transmission":
						if (filterOp == FilterOperation.IS_EQUAL || filterOp == FilterOperation.IS_NOT_EQUAL) {
							p.getOwnedComponents().getFilterManager().add(new ComponentTypeFilter<Component>(null, filterOp, ComponentType.TRANSMISSION));
						}
						else {
							hasError = true;
						}
						break;
					case "chassis":
						if (filterOp == FilterOperation.IS_EQUAL || filterOp == FilterOperation.IS_NOT_EQUAL) {
							p.getOwnedComponents().getFilterManager().add(new ComponentTypeFilter<Component>(null, filterOp, ComponentType.CHASSIS));
						}
						else {
							hasError = true;
						}
						break;
					case "suspension":
						if (filterOp == FilterOperation.IS_EQUAL || filterOp == FilterOperation.IS_NOT_EQUAL) {
							p.getOwnedComponents().getFilterManager().add(new ComponentTypeFilter<Component>(null, filterOp, ComponentType.SUSPENSION));
						}
						else {
							hasError = true;
						}
						break;
					case "wheel": case "wheels":
						if (filterOp == FilterOperation.IS_EQUAL || filterOp == FilterOperation.IS_NOT_EQUAL) {
							p.getOwnedComponents().getFilterManager().add(new ComponentTypeFilter<Component>(null, filterOp, ComponentType.WHEELS));
						}
						else {
							hasError = true;
						}
						break;
				}
			}
			
			
			if (hasError) {
				event.getChannel().sendMessage("invalid string for string criteria error: " + errorMsg).queue();
			}
			else {
				event.getChannel().sendMessage("Filter has been added.").queue();
			}
			
			//dbh.updateUser(p);
			return p;
		}
	}
	 
	 /*
	  * Fomat String using Markup language.
	  */
	 public String formatText(String style,String text)
	 {
		 String styledText = text;
		 //Italic
		 if(style == "i")
		 {
			 styledText = "*"+ text + "*";
		 }
		 //Bold
		 if(style == "b")
		 {
			 styledText = "**"+ text + "**";
		 }
		 //Underline
		 if(style == "u")
		 {
			 styledText = "__"+ text + "__";
		 }
		 //Strikethrough
		 if(style == "s")
		 {
			 styledText = "~~"+ text + "~~";
		 }
		 //Bold Italics
		 if(style == "bi")
		 {
			 styledText = "***"+ text + "***";
		 }
		 //Underline Italics
		 if(style == "ui")
		 {
			 styledText = "___*"+ text + "*__";
		 }
		 //Underline Bold
		 if(style == "ub")
		 {
			 styledText = "___**"+ text + "**__";
		 }
		 //Underline Bold Italics
		 if(style == "ubi")
		 {
			 styledText = "___***"+ text + "***__";
		 }
		 //Code Text
		 if(style == "c")
		 {
			 styledText = "`"+ text + "`";
		 }
		 //Code Block
		 if(style == "cb")
		 {
			 styledText = "```"+ text + "```";
		 }
		 return styledText;
	 }

	 /**
	  * 
	  * @param gp sets the GameplayHandler object reference
	  */
	 public void setGameplayHandler(GameplayHandler gp)
	 {
		 gph = gp;
	 }
	 
	 
	 /*
	  * Convert a Player class into a formatted embed.
	  */
	 public EmbedBuilder printPlayer(Player p,GuildMessageReceivedEvent event) throws NotFoundException
	 {
		 eb.clear();
			eb.setTitle(p.getUsername()+"'s Profile: ");
			eb.setColor(Color.green);
			eb.setThumbnail(event.getAuthor().getAvatarUrl());
			eb.addField(formatText("b","Credits: "),formatText("cb",p.getCredits()+""),false);
			
			if(p.getActiveDriverId() != null) {
				eb.addField(formatText("b","Active Driver: "),
						formatText("cb",p.getOwnedDrivers().getById(p.getActiveDriverId()).getName()),true);
			}
			eb.addBlankField(true);
			if(p.getActiveDriverId() != null) {
				eb.addField(formatText("b","Active Car: "),
						formatText("cb","Active Car Set"),true);
			}else {
				eb.addField(formatText("b","Active Car: "),
						formatText("cb","No Active Car Set"),true);
			}
			
			eb.addField(formatText("b","Racing Stats: "),
					formatText("cb","Total Wins: "+ p.getTotalWins()
			+ "\nTotal Losses: " + p.getTotalLosses()),true);
			
			eb.addField(formatText("b","Number of items owned: "),
					formatText("cb","# of Components: "+ p.getOwnedComponents().getItems().size() +
							"\n# of Cars: "+p.getOwnedCars().getItems().size() +
							"\n# of Drivers: "+p.getOwnedDrivers().getItems().size()),true);
			

 		//eb.addField("Title of field", "test of field", false);
 		eb.setFooter("\u3000".repeat(50));
		return eb; 
	 }
	 
	 public EmbedBuilder printComponent(Component c, GuildMessageReceivedEvent event)
	 {
		eb.clear();
		eb.setTitle(c.getComponentType().toString());
		eb.setThumbnail(c.getThumbnailURL());
		eb.addField("Quality: ", formatText("cb",c.getQuality().toString().toLowerCase()),false);
		eb.addField("Durability: ",formatText("cb",c.getDurability() +"/"+c.getMaxDurability()) ,true);
		eb.addField("Value: ", formatText("cb", c.getValue()+""),true);
		eb.addField("Weight: ",formatText("cb", c.getWeight()+""),true);
		if(c instanceof EngineComponent)
		{
			eb.addField("Speed: ",formatText("cb", ((EngineComponent) c).getSpeed()+""),true);	
		}
		if(c instanceof ChassisComponent)
		{
			eb.addField("Popularity Modifier: ",formatText("cb", ((ChassisComponent) c).getPopularityModifier()+""),true);
			eb.addField("Acceleration Modifier: ",formatText("cb", ((ChassisComponent) c).getAccelerationModifier()+""),true);	
			eb.addField("Speed Modifier: ",formatText("cb", ((ChassisComponent) c).getSpeedModifier()+""),true);	
			eb.addField("Handling Modifier: ",formatText("cb", ((ChassisComponent) c).getHandlingModifier()+""),true);
			eb.addField("Breaking Modifier: ",formatText("cb", ((ChassisComponent) c).getBrakingModifier()+""),true);	
		}
		if(c instanceof SuspensionComponent)
		{
			eb.addField("Handling: ",formatText("cb", ((SuspensionComponent) c).getHandling()+""),true);	
		}
		if(c instanceof TransmissionComponent)
		{
			eb.addField("Acceleration: ",formatText("cb", ((TransmissionComponent) c).getAcceleration()+""),true);	
		}
		if(c instanceof WheelComponent)
		{
			eb.addField("Breaking: ",formatText("cb", ((WheelComponent) c).getBraking()+""),true);	
		}
			
		return eb;
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