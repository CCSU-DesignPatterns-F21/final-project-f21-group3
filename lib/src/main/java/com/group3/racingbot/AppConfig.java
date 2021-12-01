package com.group3.racingbot;

import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Object used to keep track of the configuration properties, used with the DesignPatterns.Utils package and in DBHandler.
 * @author Maciej Bregisz
 *
 */

@XmlRootElement(name="appConfig")
public class AppConfig {

	private String discordChannelToken;
	private String mongoDBUsername; 
	private String mongoDBPass;
	private String mongoDBDatabase;
	/**
	 * Returns the Discord Channel Token property value
	 * @return the discordChannelToken
	 */
	public String getDiscordChannelToken() {
		return discordChannelToken;
	}
	/**
	 * Sets the Discord Channel Token property value
	 * @param discordChannelToken the discordChannelToken to set
	 */
	public void setDiscordChannelToken(String discordChannelToken) {
		this.discordChannelToken = discordChannelToken;
	}
	/**
	 * Returns the Mongod DB Username property value
	 * @return the mongoDBUsername
	 */
	public String getMongoDBUsername() {
		return mongoDBUsername;
	}
	/**
	 * Sets the Mongod DB Username property value
	 * @param mongoDBUsername the mongoDBUsername to set
	 */
	public void setMongoDBUsername(String mongoDBUsername) {
		this.mongoDBUsername = mongoDBUsername;
	}
	/**
	 * Returns the Mongod DB Password property value
	 * @return the mongoDBPass
	 */
	public String getMongoDBPass() {
		return mongoDBPass;
	}
	/**
	 * Sets the Mongod DB Password property value
	 * @param mongoDBPass the mongoDBPass to set
	 */
	public void setMongoDBPass(String mongoDBPass) {
		this.mongoDBPass = mongoDBPass;
	}
	/**
	 * Returns the Mongod DB Database name property value
	 * @return the mongoDBDatabase
	 */
	public String getMongoDBDatabase() {
		return mongoDBDatabase;
	}
	/**
	 * Sets the Mongod DB Database name property value
	 * @param mongoDBDatabase the mongoDBDatabase to set
	 */
	public void setMongoDBDatabase(String mongoDBDatabase) {
		this.mongoDBDatabase = mongoDBDatabase;
	}
	
	/**
	 * Calculates the object hash code value
	 * @return int calculated hashcode.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(discordChannelToken, mongoDBDatabase, mongoDBPass, mongoDBUsername);
	}
	/**
	 * Returns whether or not two objects are equal
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppConfig other = (AppConfig) obj;
		return Objects.equals(discordChannelToken, other.discordChannelToken)
				&& Objects.equals(mongoDBDatabase, other.mongoDBDatabase)
				&& Objects.equals(mongoDBPass, other.mongoDBPass)
				&& Objects.equals(mongoDBUsername, other.mongoDBUsername);
	}
	/**
	 * Retrns Short description of the object.
	 * @return string short description of the 
	 */
	@Override
	public String toString() {
		return "Object containing critical configuration properties.";
	} 
	
	
}
