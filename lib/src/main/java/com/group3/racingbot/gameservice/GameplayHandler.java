package com.group3.racingbot.gameservice;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.shop.ChopShop;
import com.group3.racingbot.shop.CustomObserver;
import com.group3.racingbot.shop.Dealership;
import com.group3.racingbot.shop.Importer;
import com.group3.racingbot.shop.Junkyard;
import com.group3.racingbot.shop.Shop;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

/**
 * Repsponsible for handling the game simulation itself and any underlying
 * features. Keeps track of the Store, Car and Component creation.
 * 
 * @author Maciej Bregisz
 *
 */
public class GameplayHandler {

	private JDA jda;
	private DBHandler db;

	private List<CustomObserver> listeners = new ArrayList<CustomObserver>();
	private ComponentFactory componentFactory;
	private RaceEvent raceEvent;

	/**
	 * GamaplayHandler object constructor
	 * @param j reference to the JDA object
	 * @param dbh reference to the DBHandler object
	 */
	public GameplayHandler(JDA j, DBHandler dbh) {
		db = dbh;
		jda = j;
		componentFactory = new ConcreteComponentFactory();
		Shop junkyard, chopshop, dealership, importer;

		raceEvent = new RaceEvent();
		raceEvent.initialize();
		dbh.insertRaceEvent(raceEvent);

		// Check if shop is in DB, if not create, store and subscribe it to the
		// listeners list.
		if (dbh.getShop(0) != null) {
			chopshop = dbh.getShop(0);
			// chopshop.setFactory(componentFactory);
			// System.out.println(chopshop);
			this.subscribe(chopshop);
		} else {
			chopshop = new ChopShop(this);
			chopshop.setFactory(componentFactory);
			chopshop.update();
			dbh.insertShop(chopshop);
			//this.subscribe(chopshop);
		}

		if (dbh.getShop(1) != null) {
			junkyard = dbh.getShop(1);
			junkyard.setFactory(componentFactory);
			this.subscribe(junkyard);
		} else {
			junkyard = new Junkyard(this);
			junkyard.setFactory(componentFactory);
			junkyard.update();
			dbh.insertShop(junkyard);
			this.subscribe(junkyard);
		}

		if (dbh.getShop(2) != null) {
			dealership = dbh.getShop(2);
			dealership.setFactory(componentFactory);
			this.subscribe(dealership);
		} else {
			dealership = new Dealership(this);
			dealership.setFactory(componentFactory);
			dealership.update();
			dbh.insertShop(dealership);
			this.subscribe(dealership);
		}

		if (dbh.getShop(3) != null) {
			importer = dbh.getShop(3);
			importer.setFactory(componentFactory);
			this.subscribe(importer);
		} else {
			importer = new Importer(this);
			importer.setFactory(componentFactory);
			importer.update();
			dbh.insertShop(importer);
			this.subscribe(importer);
		}

		// Instantiate the stores, racetrack generator, etc. This is responsible for
		// handling gameplay related tasks.

		Timer timer = new Timer();

		TimerTask hourlyTask = new TimerTask() {

			

			@Override
			public void run() {
				System.out.println("Running Hourly scheduled task...");


				// Create an event with a randomized id.
				raceEvent = new RaceEvent();
				raceEvent.initialize();

				dbh.insertRaceEvent(raceEvent);


				notifyObservers();
				EmbedBuilder eb = new EmbedBuilder();
				eb.clear();
				eb.setTitle("Hourly Update!");
				eb.setColor(Color.red);

				eb.addField("Store Update", "New Components and Cars for sale at the Stores!", false);
				//eb.addField("Race Track Update", "New race event available!", false);
				//eb.addField("Race Results available",	"If you registered for a race, you can now check the race results!", false);
				sendEmbedChannelMessage(eb);
			}
		};

		LocalDateTime timeNow = LocalDateTime.now();
		System.out.println("Time Now: " + timeNow);
		LocalDateTime nextHour = timeNow.plusHours(1).truncatedTo(ChronoUnit.HOURS);
		System.out.println("Next Hour: " + nextHour);
		Date firstScheduledTask = Date.from(nextHour.atZone(ZoneId.systemDefault()).toInstant());
		System.out.println("Next Hour (Date Converted): " + firstScheduledTask);

		timer.schedule(hourlyTask, firstScheduledTask, 1000 * 60 *60);
	}

	/**
	 * Adds observer to a list which will be notified when specified event takes
	 * place.
	 * 
	 * @param o observer which is subscribing.
	 */
	public void subscribe(CustomObserver o) {
		// System.out.println(o);
		listeners.add(o);

	}

	/**
	 * Removes observer from the list of subscribers.
	 * 
	 * @param o observer which is being ubsubscribed.
	 */
	public void unsubscribe(CustomObserver observer) {
		listeners.remove(observer);
	}

	/**
	 * Loops through each subscribed observer and call it's update function.
	 */
	public void notifyObservers() {
		System.out.println("Notifying observers...");

		if (listeners.size() != 0) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).update();
				db.updateShop((Shop) listeners.get(i));
			}
		}
	}

	/**
	 * Returns the list of CustomObservers
	 * 
	 * @return
	 */
	public List<CustomObserver> getObservers() {
		return listeners;
	}

	/**
	 * @return the raceEvent
	 */
	public RaceEvent getRaceEvent() {
		return raceEvent;
	}

	/**
	 * @param raceEvent the raceEvent to set
	 */
	public void setRaceEvent(RaceEvent raceEvent) {
		this.raceEvent = raceEvent;
	}

	/**
	 * Sends an embed message to every discord channel.
	 * 
	 * @param eb Embed Builder reference.
	 */
	public void sendEmbedChannelMessage(EmbedBuilder eb) {
		List<Guild> guilds = jda.getGuilds();
		for (int i = 0; i < guilds.size(); i++) {
			Guild guild = guilds.get(i);
			guild.getSystemChannel().sendMessageEmbeds(eb.build()).queue();

		}
	}

	/**
	 * Custom hashCode method for GameplayHandler object
	 * 
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
	 * 
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