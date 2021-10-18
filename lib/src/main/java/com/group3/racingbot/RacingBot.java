package com.group3.racingbot;

import com.group3.racingbot.gameservice.GameplayHandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class RacingBot {
	//
	public static JDA jda;
	public static String prefix = "!";	
	
	public static GameplayHandler gameHandler;
	
	private static ConfigPropertiesHandler configProperties;
	private static DBHandler db;
	
	public static void main(String[] args) throws Exception{
		configProperties = ConfigPropertiesHandler.getInstance();
		db = new DBHandler();
		gameHandler = new GameplayHandler();
		
		//dbController.hashCode();
		jda = JDABuilder.createDefault(configProperties.getProperty("discordChannelToken")).build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("for participants!"));
		jda.addEventListener(new Commands(db));
	}
}