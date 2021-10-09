package com.group3.racingbot;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class RacingBot {

	public static JDA jda;
	public static String prefix = "!";	
	public static void main(String[] args) throws Exception{
		
		jda = JDABuilder.createDefault("ODk1MTM5NDEzNjk1Mjk1NTI4.YV0Niw.mqD9hwGMXTWwl5_GykHizA1LVGI").build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("for participants!"));
		jda.addEventListener(new Commands());
	}

}
