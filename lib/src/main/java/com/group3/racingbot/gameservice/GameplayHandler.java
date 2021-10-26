package com.group3.racingbot.gameservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.JDA;

/**
 *  Repsponsible for handling the game simulation itself and any underlying features. Keeps track of the Store, Car and Component creation.
 * @author Maciej Bregisz
 *
 */
public class GameplayHandler {

	private JDA jda;
	public GameplayHandler(JDA j) {
		//Instanciate the stores, racetrack generator, etc. This is responsible for handling gameplay related tasks.
		jda = j;
		Timer timer = new Timer ();
		TimerTask hourlyTask = new TimerTask () {
		    @Override
		    public void run () {
		        System.out.println("Running Hourly scheduled task...");
		       
		    }
		};
		
		LocalDateTime timeNow = LocalDateTime.now();
		System.out.println("Time Now: "+timeNow);
		LocalDateTime nextHour = timeNow.plusHours(1).truncatedTo(ChronoUnit.HOURS);
		System.out.println("Next Hour: "+nextHour);
		Date firstScheduledTask = Date.from(nextHour.atZone(ZoneId.systemDefault()).toInstant());
		System.out.println("Next Hour (Date Converted): "+firstScheduledTask);
		
		timer.schedule(hourlyTask, firstScheduledTask, 1000*60*60);
		
	}
	
	/**
	 * Custom hashCode method for GameplayHandler object
	 * @return calculated hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jda == null) ? 0 : jda.hashCode());
		return result;
	}
	
	/**
	 * Custom equals method for DBHandler
	 * @return whether or not 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameplayHandler other = (GameplayHandler) obj;
		if (jda == null) {
			if (other.jda != null)
				return false;
		} else if (!jda.equals(other.jda))
			return false;
		return true;
	}
}
