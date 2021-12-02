package com.group3.racingbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import designpatterns.utils.ConfigReader;
import designpatterns.utils.ConfigReaderFactory;
import designpatterns.utils.FileType;

/**
 * Responsible for opening, decrypting and parsing the configuration file. Singleton design.
 * @author Maciej Bregisz
 */

public class ConfigPropertiesHandler {
	private static ConfigPropertiesHandler configPropertiesHandler = null;
	private Properties prop; //No public getters and setter on purpose, for internal use only.
	private AppConfig appConfig; 
	private boolean isEncrypted;
	
	private ConfigPropertiesHandler() {
		boolean production = false; // Production code is being used
		isEncrypted = false;
		try {
			// Try to find the encrypted files
			ConfigReaderFactory<AppConfig> secureAppConfigReaderFactory = new ConfigReaderFactory<AppConfig>();
			File secureConfigFile = new File("./secureConfig.txt");
			File keyFile = new File("./key.txt");

			byte[] key = Files.readAllBytes(keyFile.toPath());
			ConfigReader<AppConfig> secureXmlAppConfigReader = secureAppConfigReaderFactory.createSecureReader(FileType.XML, secureConfigFile.toURI().toURL(), key);
			appConfig = secureXmlAppConfigReader.GetConfig(AppConfig.class);
			System.out.println("Production: Found secureConfig.txt and key.txt.");
			production = true;
			isEncrypted = true;
		} catch (Exception e) {
			isEncrypted = false;
			// Try to find the config.properties
			e.printStackTrace();
			System.out.println("Production: Unable to locate secureConfig.txt or key.txt. Attempting to find config.properties");
			prop =new Properties();
			
			try {
				FileInputStream ip= new FileInputStream("./config.properties");
				System.out.println("Production: Found config.properties.");
				try {
					prop.load(ip);
				} catch (IOException f) {
					f.printStackTrace();
				}
				production = true;
				
			} catch (FileNotFoundException g) {
				g.printStackTrace();
				System.out.println("Production: Unable to find config.properties.");
			}
			
			
		}
		
		if (!production) {
			try {		
				ConfigReaderFactory<AppConfig> secureAppConfigReaderFactory = new ConfigReaderFactory<AppConfig>();
				File secureConfigFile = new File("src/main/resources/secureConfig.txt");
				File keyFile = new File("src/main/resources/key.txt");
	
				byte[] key = Files.readAllBytes(keyFile.toPath());
				ConfigReader<AppConfig> secureXmlAppConfigReader = secureAppConfigReaderFactory.createSecureReader(FileType.XML, secureConfigFile.toURI().toURL(), key);
				appConfig = secureXmlAppConfigReader.GetConfig(AppConfig.class);
				System.out.println("Development: Found secureConfig.txt and key.txt.");
				isEncrypted = true;
	
			} catch (Exception e) {
				isEncrypted = false;
				e.printStackTrace();
				prop =new Properties();
				
				try {
					FileInputStream ip= new FileInputStream("./src/main/resources/config.properties");
					System.out.println("Development: Found config.properties.");
					try {
						prop.load(ip);
					} catch (IOException f) {
						f.printStackTrace();
					}
					
				} catch (FileNotFoundException g) {
					
					//g.printStackTrace();
					System.out.println("Development: Unable to find config.properties.");
				}
			}
		}
		
	}
	
	/**
	 * Returns the instance of the ConfigPropertiesHandler, it's created if no instance exists.
	 *@return Returns the object reference of the singleton.
	 */
	public static ConfigPropertiesHandler getInstance()
    {
        if (configPropertiesHandler == null)
        	configPropertiesHandler = new ConfigPropertiesHandler();
 
        return configPropertiesHandler;
    }
	
	/**
	 * Returns object reference of AppConfig which contains all of the config properties.
	 * @return Instance of the AppConfig object.
	 */
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	/**
	 * @param propReq Properties 
	 * @return returns property value parsed out of the config.properties file.
	 */
	public String getProperty(String propReq) {
		return prop.getProperty(propReq);
	}
	
	/**
	 * @return whether or not config handler is using the encrypted files
	 */
	public boolean getEncrypted() {
		return isEncrypted;
	}
	
	
	/** 
	 * Returns status of the singleton.
	 * @return Status of the singleton.
	 */
	@Override
	public String toString() {
		if (configPropertiesHandler == null)
			return "Singleton not present";
		return "Singleton present";
	}

}
