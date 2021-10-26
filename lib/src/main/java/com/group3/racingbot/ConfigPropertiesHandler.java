package com.group3.racingbot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Responsible for opening and parsing the Config.Properties file. Singleton design.
 * @author Maciej Bregisz
 *
 */

public class ConfigPropertiesHandler {
	private static ConfigPropertiesHandler configPropertiesHandler = null;
	private Properties prop;
	private FileInputStream ip;
	
	
	private ConfigPropertiesHandler() {
		
		prop =new Properties();
		
		try {
			ip= new FileInputStream("./src/main/resources/config.properties");
			try {
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	/*
	 *@return Returns the instance of the ConfigPropertiesHandler, it's created if no instance exists.
	 */
	public static ConfigPropertiesHandler getInstance()
    {
        if (configPropertiesHandler == null)
        	configPropertiesHandler = new ConfigPropertiesHandler();
 
        return configPropertiesHandler;
    }
	
	/**
	 * @param propReq Properties 
	 * @return returns property value parsed out of the config.properties file.
	 */
	public String getProperty(String propReq) {
		return prop.getProperty(propReq);
	}
	
	@Override
	public String toString() {
		if (configPropertiesHandler == null)
			return "Singleton not present";
		return "Singleton present";
	}

}
