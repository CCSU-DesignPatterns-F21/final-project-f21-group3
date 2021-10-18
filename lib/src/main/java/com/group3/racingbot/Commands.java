package com.group3.racingbot;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.Inventory;
import com.group3.racingbot.inventory.InventoryIterator;
import com.group3.racingbot.inventory.QualityFilter;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * 
 * @author Maciej Bregisz
 *
 */

public class Commands extends ListenerAdapter {
	
	
	private DBHandler dbh;
	private EmbedBuilder eb;
	public Commands(DBHandler db) {
		eb = new EmbedBuilder();
		dbh = db;
		
	}

	 @Override
	  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		 
	    String[] args = event.getMessage().getContentRaw().split(" ");
	    Member user = event.getMember(); //Gets the id of the user who called the command.
	    JDA client = event.getJDA(); //Gets the JDA object for later manipulation.
	    
	    if(args[0].equalsIgnoreCase(RacingBot.prefix+"iracer"))
	    {
	    	if(args[1].equalsIgnoreCase("help"))
	    	{
	    		//Embed example
	    		eb.setColor(Color.red);
	    		eb.setDescription("RacingBot commands: \n"
	    				+ "iracer help \n"
	    				+ "iracer register | Register with the bot \n"
	    				+ "iracer guess <number 0-50> | See if you can guess the number!");
	    		eb.setFooter("Text", "https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/zekroBot_Logo_-_round_small.png?raw=true");
	    		
	    	event.getChannel().sendMessage(eb.build()).queue();
	    		
	    	}
	    	//Handle User registering
	    	if(args[1].equalsIgnoreCase("register"))
	    	{
	    		//Example response, gets the name of the User which called the command and returns a message with a @User mention in it's content.
	    		if(dbh.userExists(user.getId())){
	    			Player p = dbh.getPlayer(user.getId());
	    			eb.setImage(user.getUser().getAvatarUrl());
	    			eb.setTitle("User Already Exists!");
	    			eb.setColor(Color.green);
	    			eb.setThumbnail(user.getUser().getAvatarUrl());
	    			
		    		eb.setDescription("Total Wins: "+ p.getTotalWins()
		    				+ "\n Total Losses: " + p.getTotalLosses()
		    				+ "\n Credits: " + p.getCredits());
		    		//eb.addField("Title of field", "test of field", false);
		    		event.getChannel().sendMessage(eb.build()).queue();
	    			
	    		}else {
	    			event.getChannel().sendMessage("Registering User: " + user.getAsMention() + " with RacingBot!").queue();
	    			Player p = new Player();
	    			p.setId(user.getId());
	    			p.setUsername(user.getUser().getName());
	    			p.setLastWorked(new Date(100));
	    			dbh.insertUser(p);
	    			eb.setThumbnail(user.getUser().getAvatarUrl());
	    			eb.setTitle("User Already Exists!");
	    			eb.setColor(Color.green);
		    		eb.setDescription("Total Wins: "+ p.getTotalWins()
		    				+ "\n Total Losses: " + p.getTotalLosses()
		    				+ "\n Credits: " + p.getCredits());
		    		//eb.addField("Title of field", "test of field", false);
	    		}	    		
	    	}
	    	//Example command, simple guessing command
	    	if(args[1].equalsIgnoreCase("guess"))
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
	    	if(args[1].equalsIgnoreCase("work")){
	    		Player p = dbh.getPlayer(user.getId());
	    		System.out.println(p.getLastWorked());
	    		Calendar nextWork = Calendar.getInstance(); 
	    		nextWork.setTime(p.getLastWorked());               
	    		nextWork.add(Calendar.HOUR_OF_DAY, 1);      
	    		nextWork.getTime();    
	    		
	    		if(p.getLastWorked() != null) {
	    			if(p.getLastWorked().after(nextWork.getTime()))
		    		{
		    			p.setCredits(p.getCredits() + 500);
		    			p.setLastWorked(new Date(System.currentTimeMillis()));
		    		
		    			dbh.updateUser(p);
		    		}else {
		    			long remaining = System.currentTimeMillis() - nextWork.getTimeInMillis();
		    			event.getChannel().sendMessage(String.format("You can work again in:  %d Hours, %d Minutes, %d Seconds", 
		    					TimeUnit.MILLISECONDS.toHours(remaining),
		    					TimeUnit.MILLISECONDS.toMinutes(remaining),
		    					TimeUnit.MILLISECONDS.toSeconds(remaining)));
		    		}
	    			
	    		}
	    		
	    	}
	    	
	    	
	    	// A test for filtering an inventory of cars.
	    	if(args[1].equalsIgnoreCase("inventory")) {
	    		int randomNum = ThreadLocalRandom.current().nextInt(0, 49);
	    		
	    		Car carA = new Car(randomNum, randomNum*2, "OEM", randomNum*3);
	    		Car carB = new Car(randomNum*2, randomNum, "Junkyard", randomNum*4);
	    		Car carC = new Car(randomNum/2, randomNum, "Lemon", randomNum*2);
	    		Car carD = new Car(randomNum*4, randomNum*5, "Racing", randomNum/3);
	    		
	    		List<Car> cars = new ArrayList<Car>();
	    		cars.add(carA);
	    		cars.add(carB);
	    		cars.add(carC);
	    		cars.add(carD);
	    		
	    		Inventory<Car> inventory = new CarInventory(cars);
	    		InventoryIterator<Car> carIterator = inventory.iterator();
	    		QualityFilter<Car> qualityFilter = new QualityFilter<Car>(carIterator, "Junkyard");
	    		
	    		// Print all items with "Junkyard" quality
	    		String result = "";
	    		int carCount = 1;
	    		while (qualityFilter.hasNext()) {
	    			Car car = qualityFilter.next();
	    			if (car != null) {
	    				result += "Car " + carCount + ": " + car + "\n";
	    			}
	    		}
	    		eb.setDescription(result);
	    		event.getChannel().sendMessage(eb.build()).queue();
	    	}
	    }
	  }
}
