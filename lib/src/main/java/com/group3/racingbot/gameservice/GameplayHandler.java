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

import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.InteractPage;
import com.github.ygimenez.model.Page;
import com.group3.racingbot.DBHandler;
import com.group3.racingbot.Driver;
import com.group3.racingbot.Player;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.ComponentFactory.ComponentFactory;
import com.group3.racingbot.ComponentFactory.ConcreteComponentFactory;
import com.group3.racingbot.inventory.Iterator;
import com.group3.racingbot.shop.ChopShop;
import com.group3.racingbot.shop.CustomObserver;
import com.group3.racingbot.shop.Dealership;
import com.group3.racingbot.shop.Importer;
import com.group3.racingbot.shop.Junkyard;
import com.group3.racingbot.shop.Shop;
import com.group3.racingbot.standings.DriverStanding;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

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

	// TODO: Might need to be changed into generic?
	private List<CustomObserver> listeners = new ArrayList<CustomObserver>();
	private ComponentFactory componentFactory;
	private RaceEvent raceEvent;

	public GameplayHandler(JDA j, DBHandler dbh) {
		db = dbh;
		jda = j;
		componentFactory = new ConcreteComponentFactory();
		Shop junkyard, chopshop, dealership, importer;

		// System.out.println(dbh.getShop(0));

//			for(int i =0; i<shops.size(); i++) {
//				subscribe(shops.get(i));
//				shops.get(i).toString();
//			}

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
			chopshop = new ChopShop();
			chopshop.setFactory(componentFactory);
			chopshop.update();
			dbh.insertShop(chopshop);
			this.subscribe(chopshop);
		}

		if (dbh.getShop(1) != null) {
			junkyard = dbh.getShop(1);
			junkyard.setFactory(componentFactory);
			this.subscribe(junkyard);
		} else {
			junkyard = new Junkyard();
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
			dealership = new Dealership();
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
			importer = new Importer();
			importer.setFactory(componentFactory);
			importer.update();
			dbh.insertShop(importer);
			this.subscribe(importer);
		}

		// Instantiate the stores, racetrack generator, etc. This is responsible for
		// handling gameplay related tasks.

		Timer timer = new Timer();

		TimerTask hourlyTask = new TimerTask() {

			EmbedBuilder eb = new EmbedBuilder();

			@Override
			public void run() {
				System.out.println("Running Hourly scheduled task...");

				Iterator<DriverStanding> driverIterator = raceEvent.getStandings().iterator();
				boolean raceCanBegin = true;
				Player p = null;
				while (driverIterator.hasNext()) {
					Driver currentDriver = driverIterator.next().getDriver();
					p = dbh.getPlayer(currentDriver.getPlayerId());
					currentDriver.beginRace();
					if (p.getOwnedDrivers().update(currentDriver)) {
						dbh.updateUser(p);
					} else {
						raceCanBegin = false;
						// raceEvent.getChannel().sendMessage("Unable to update driver with id: " +
						// currentDriver.getId() +". Unable to begin race.").queue();
					}

				}

				Timer raceTimer = new Timer();

				ArrayList<Page> raceTrackNodes = new ArrayList<>();
				raceTrackNodes.clear();
				while (!raceEvent.isFinished()) {
					System.out.println("Race Step");
					Field trackNode = new Field("Race Event History", raceEvent.stepAllDrivers().toString(), false);
					eb.addField(trackNode);
					raceTrackNodes.add(new InteractPage(eb.build()));
					// event.getChannel().sendMessage(raceEvent.stepAllDrivers()).queue();
				}
				List<Guild> guilds = jda.getGuilds();
				for (int i = 0; i < guilds.size(); i++) {
					Guild guild = guilds.get(i);
					guild.getSystemChannel().sendMessageEmbeds((MessageEmbed) raceTrackNodes.get(0).getContent())
							.queue(success -> {
								Pages.paginate(success, raceTrackNodes, /* Use buttons? */ true);
							});
				}

				

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
				eb.addField("Race Track Update", "New race event available!", false);
				eb.addField("Race Results available",
						"If you registered for a race, you can now check the race results!", false);
				sendEmbedChannelMessage(eb);
			}
		};

		LocalDateTime timeNow = LocalDateTime.now();
		System.out.println("Time Now: " + timeNow);
		//LocalDateTime nextHour = timeNow.plusHours(1).truncatedTo(ChronoUnit.HOURS);
		LocalDateTime nextHour = timeNow.plusMinutes(1).truncatedTo(ChronoUnit.MINUTES);
		System.out.println("Next Hour: " + nextHour);
		Date firstScheduledTask = Date.from(nextHour.atZone(ZoneId.systemDefault()).toInstant());
		System.out.println("Next Hour (Date Converted): " + firstScheduledTask);

		timer.schedule(hourlyTask, firstScheduledTask, 1000 * 60 );
	}

	public void debug() {
		notifyObservers();
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

	private String printRaceResults() {
		return "RaceEvent " + this.raceEvent.getId() + " is complete!\n_____\n"
				+ this.raceEvent.getStandings().toString();
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