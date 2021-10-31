package com.group3.racingbot.gameservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.shop.ChopShop;
import com.group3.racingbot.shop.CustomObserver;
import com.group3.racingbot.shop.Dealership;
import com.group3.racingbot.shop.Importer;
import com.group3.racingbot.shop.Junkyard;
import com.group3.racingbot.shop.Shop;

import net.dv8tion.jda.api.JDA;

/**
 *  Repsponsible for handling the game simulation itself and any underlying features. Keeps track of the Store, Car and Component creation.
 * @author Maciej Bregisz
 *
 */
public class GameplayHandler {

	private JDA jda;
	//TODO: Might need to be canged into generics
	private List<CustomObserver> listeners = new ArrayList<CustomObserver>();
	//private List<Shop> shops;
	private ComponentFactory componentFactory;
	
	public GameplayHandler(JDA j, DBHandler dbh) {
		componentFactory = new ConcreteComponentFactory();
		Shop junkyard,chopshop,dealership,importer;
		
			//System.out.println(dbh.getShop(0));
			
//			for(int i =0; i<shops.size(); i++) {
//				subscribe(shops.get(i));
//				shops.get(i).toString();
//			}
		
		//Check if shop is in DB, if not create, store and subscribe it to the listeners list.
		
		
		if(dbh.getShop(0) != null) {
			chopshop = dbh.getShop(0);
			chopshop.setFactory(componentFactory);
			//System.out.println(chopshop);
			this.subscribe(chopshop);
		}else {
			chopshop = new ChopShop();
			chopshop.setFactory(componentFactory);
			chopshop.update();
			dbh.insertShop(chopshop);
			this.subscribe(chopshop);
		}
		
		if(dbh.getShop(1) != null) {
			junkyard = dbh.getShop(1);
			junkyard.setFactory(componentFactory);
			this.subscribe(junkyard);
		}else {
			 junkyard = new Junkyard();
			 junkyard.setFactory(componentFactory);
			 junkyard.update();
			 dbh.insertShop(junkyard);
			 this.subscribe(junkyard);
		}
		
		if(dbh.getShop(2) != null) {
			dealership = dbh.getShop(2);
			dealership.setFactory(componentFactory);
			this.subscribe(dealership);
		}else {
			dealership = new Dealership();
			dealership.setFactory(componentFactory);
			dealership.update();
			dbh.insertShop(dealership);
			this.subscribe(dealership);
		}
		
		if(dbh.getShop(3) != null) {
			importer = dbh.getShop(3);
			importer.setFactory(componentFactory);
			this.subscribe(importer);
		}else {
			importer = new Importer();
			importer.setFactory(componentFactory);
			importer.update();
			dbh.insertShop(importer);
			this.subscribe(importer);
		}

		
		 
		//System.out.println(junkyard);
		//System.out.println(chopshop);
		//System.out.println(dealership);
		//System.out.println(importer);
			
		
		//notifyObservers();
		
		//listeners.addAll(shops);
		
		//Instanciate the stores, racetrack generator, etc. This is responsible for handling gameplay related tasks.
		jda = j;
		Timer timer = new Timer ();
		
		TimerTask hourlyTask = new TimerTask () {
		    @Override
		    public void run () {
		        System.out.println("Running Hourly scheduled task...");
		        notifyObservers();
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
	
	public void subscribe(CustomObserver o) {
		//System.out.println(o);
		listeners.add(o);
	}
	
	public void unsubscribe(CustomObserver observer) {
		listeners.remove(observer);
	}
	public void notifyObservers() {
		System.out.println("Notifying observers...");
		if(listeners.size() != 0)
		{
			for(int i = 0; i < listeners.size(); i++)
			{
				listeners.get(i).update();
			}
		}
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
