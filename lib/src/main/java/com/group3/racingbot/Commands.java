package com.group3.racingbot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * 
 * @author Maciej Bregisz
 *
 */

public class Commands extends ListenerAdapter {

	private DBHandler dbh;
	private EmbedBuilder eb;

	public Commands(DBHandler db) {
		eb = new EmbedBuilder();
		dbh = db;
		//Component createdComponent;

	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split(" ");
		Member user = event.getMember(); // Gets the id of the user who called the command.
		JDA client = event.getJDA(); // Gets the JDA object for later manipulation.

		MongoDatabase userDB = dbh.getUserDatabase();
		MongoCollection<CustomUser> users = userDB.getCollection("Users", CustomUser.class);

		if (args[0].equalsIgnoreCase(RacingBot.prefix + "iracer")) {
			if (args[1].equalsIgnoreCase("help")) {
				// Embed example
				eb.setColor(Color.red);
				eb.setDescription(
						"RacingBot commands: \n" + "iracer help \n" + "iracer register | Register with the bot \n"
								+ "iracer guess <number 0-50> | See if you can guess the number!");
				eb.setFooter("Text", "https://i.imgur.com/l0Dacqw.jpg");

				event.getChannel().sendMessage(eb.build()).queue();

			}
			// Handle User registering
			if (args[1].equalsIgnoreCase("register")) {
				// Example response, gets the name of the User which called the command and
				// returns a message with a @User mention in it's content.
				if (dbh.userExists(user.getId())) {
					System.out.println(dbh.getPlayer(user.getId()).toString());
					event.getChannel().sendMessage("You are already registered!");

				} else {
					event.getChannel().sendMessage("Registering User: " + user.getAsMention() + " with RacingBot!")
							.queue();
					dbh.insertUser(new Player(user.getId(), user.getUser().getName()));
				}
			}
			// Example command, simple guessing command
			if (args[1].equalsIgnoreCase("guess")) {
				if (args[2] != null) {
					int randomNum = ThreadLocalRandom.current().nextInt(0, 49);
					event.getChannel().sendMessage("Guessing: " + Integer.parseInt(args[2])).queue();
					if (Integer.parseInt(args[2]) == randomNum) {
						event.getChannel().sendMessage("You guessed right").queue();
					} else {
						event.getChannel().sendMessage("Not quite! Number was: " + randomNum).queue();
					}
				} else {
					event.getChannel().sendMessage("No bet amount parameter specified!").queue();
				}

			}
			// A test for filtering an inventory of cars.
			if (args[1].equalsIgnoreCase("inventory")) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, 49);

				Car carA = new Car(randomNum, randomNum * 2, "OEM", randomNum * 3);
				Car carB = new Car(randomNum * 2, randomNum, "Junkyard", randomNum * 4);
				Car carC = new Car(randomNum / 2, randomNum, "Lemon", randomNum * 2);
				Car carD = new Car(randomNum * 4, randomNum * 5, "Racing", randomNum / 3);

				List<Car> cars = new ArrayList<Car>();
				cars.add(carA);
				cars.add(carB);
				cars.add(carC);
				cars.add(carD);

				Inventory<Car> inventory = new CarInventory(cars);
				InventoryIterator<Car> carIterator = inventory.iterator();
				QualityFilter<Car> qualityFilter = new QualityFilter<Car>(carIterator, "Junkyard");

				// Print all items with "Junkyard" quality
				String result = "";
				int carCount = 1;
				while (qualityFilter.hasNext()) {
					Car car = qualityFilter.next();
					if (car != null) {
						result += "Car " + carCount + ": " + car + "\n";
					}
				}
				eb.setDescription(result);
				event.getChannel().sendMessage(eb.build()).queue();
			}
			if (args[1].equalsIgnoreCase("createpart")) {
				event.getChannel().sendMessage("Your part has been created!").queue();
			}
		}
	}
}
