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
	
	private ConfigPropertiesHandler() {
		try {		
			ConfigReaderFactory<AppConfig> secureAppConfigReaderFactory = new ConfigReaderFactory<AppConfig>();
			File secureConfigFile = new File("src/main/resources/secureConfig.txt");
			File keyFile = new File("src/main/resources/key.txt");

			byte[] key = Files.readAllBytes(keyFile.toPath());
			ConfigReader<AppConfig> secureXmlAppConfigReader = secureAppConfigReaderFactory.createSecureReader(FileType.XML, secureConfigFile.toURI().toURL(), key);
			appConfig = secureXmlAppConfigReader.GetConfig(AppConfig.class);

		} catch (Exception e) {
			e.printStackTrace();
			prop =new Properties();
			
			try {
				FileInputStream ip= new FileInputStream("./src/main/resources/config.properties");
				try {
					prop.load(ip);
				} catch (IOException f) {
					f.printStackTrace();
				}
				
			} catch (FileNotFoundException g) {
				
				g.printStackTrace();
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
