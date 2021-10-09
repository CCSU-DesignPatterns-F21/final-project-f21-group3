package com.group3.racingbot;

import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

	 @Override
	  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
	    String[] args = event.getMessage().getContentRaw().split(" ");
	    if(args[0].equalsIgnoreCase(RacingBot.prefix+"iracer"))
	    {
	    	//event.getChannel().sendMessage("I don't have friends, I got family.").queue();
	    	
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
