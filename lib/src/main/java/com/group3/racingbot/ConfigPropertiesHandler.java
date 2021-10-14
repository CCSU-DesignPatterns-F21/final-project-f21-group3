package com.group3.racingbot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
	
	public static ConfigPropertiesHandler getInstance()
    {
        if (configPropertiesHandler == null)
        	configPropertiesHandler = new ConfigPropertiesHandler();
 
        return configPropertiesHandler;
    }
	public String getProperty(String propReq) {
		return prop.getProperty(propReq);
		
	}

}
