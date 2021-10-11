package com.group3.racingbot;


import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
	
	private DBHandler dbh;
	public Commands(DBHandler db) {
		dbh = db;
		
	}

	 @Override
	  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
	    String[] args = event.getMessage().getContentRaw().split(" ");
	    EmbedBuilder eb = new EmbedBuilder();
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
	    		event.getChannel().sendMessage("Registering User: " + user.getAsMention() + " with RacingBot!").queue();
	    		System.out.println(user.getId());
	    		
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
	    	
	    }
	  }
}
