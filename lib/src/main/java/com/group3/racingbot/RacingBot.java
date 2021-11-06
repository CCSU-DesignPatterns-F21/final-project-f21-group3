package com.group3.racingbot;

import java.util.ArrayList;

import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.Page;
import com.github.ygimenez.model.Paginator;
import com.github.ygimenez.model.PaginatorBuilder;
import com.group3.racingbot.gameservice.GameplayHandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;

/**
 * Main class of the Racing Discord Bot
 * @author Maciej Bregisz
 * @author Nick Sabia
 * @author Jack Gola
 * @author Kobe Onye
 *
 */

public class RacingBot {

	
	/**
	 * Reference to the command prefix.
	 */
	public static String prefix = "!";	
	
	/**
	 * Reference to the Gameplay Handler object, responsible for all gameplay related functionality.
	 * 
	 */
	public static GameplayHandler gameHandler;
	
	private static JDA jda;
	private static ConfigPropertiesHandler configProperties;
	private static DBHandler db;
	
	public static void main(String[] args) throws Exception{
		configProperties = ConfigPropertiesHandler.getInstance();
		
		jda = JDABuilder.createDefault(configProperties.getProperty("discordChannelToken")).build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("for participants!"));
	
		db = new DBHandler();

		Commands commandHandler = new Commands(db);
		jda.addEventListener(commandHandler);
		Thread.sleep(3000); //TODO: find a better way to do this, this is temporary. Making sure the DB connection is established before passing it to GameplayHandler
		gameHandler = new GameplayHandler(jda,db);
		
		commandHandler.setGameplayHandler(gameHandler);
		
	}
}