package com.group3.racingbot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.ComponentFactory.EngineComponent;
import com.group3.racingbot.inventory.CarInventory;
import com.group3.racingbot.inventory.DurabilityFilter;
import com.group3.racingbot.inventory.FilterOperation;
import com.group3.racingbot.inventory.Inventory;
import com.group3.racingbot.inventory.InventoryIterator;
import com.group3.racingbot.inventory.QualityFilter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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

	public Commands(DBHandler db) {
		eb = new EmbedBuilder();
		dbh = db;
		component = new ConcreteComponentFactory();
	}

	/**
	 * Handles the commands sent by the Discord User. Player command is parsed by
	 * spaces, ex. !iracer help. !iracer is required followed by a desired command.
	 */
	
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
	    		try {
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
	    	if(args[1].equalsIgnoreCase("work"))
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
		    		
		    		//Allow Player t
		    		if(p.getLastWorked() == 0)
		    		{
		    			p.setCredits(p.getCredits() + 500);
		    			p.setLastWorked(System.currentTimeMillis());
		    			System.out.println(p.toString());
		    			dbh.updateUser(p);
		    		}
	    			if(timeNow.after(nextWork))
		    		{
		    			p.setCredits(p.getCredits() + 500);
		    			p.setLastWorked(System.currentTimeMillis());
		    			System.out.println(p.toString());
		    		
		    			dbh.updateUser(p);
		    		}
	    			if(timeNow.before(nextWork)){
		    			long remaining = nextWork.getTimeInMillis() - System.currentTimeMillis();
		    			event.getChannel().sendMessage(String.format("You can work again in:  %d Hours, %d Minutes", 
		    				TimeUnit.MILLISECONDS.toHours(remaining),
		    				TimeUnit.MILLISECONDS.toMinutes(remaining))).queue();
		    		}
	    		}catch(Exception e)
	    		{
	    			event.getChannel().sendMessage("Unexpected error when attempting Work command, try again!");
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
