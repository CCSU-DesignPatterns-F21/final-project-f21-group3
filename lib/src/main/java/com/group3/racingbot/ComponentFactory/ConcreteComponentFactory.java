package com.group3.racingbot.ComponentFactory;

import org.bson.codecs.pojo.annotations.BsonCreator;

/**
 * @author Jack Gola
 * Defines the ConcreteComponentFactory
 * used for creating components
 * @return 
 */

public class ConcreteComponentFactory extends ComponentFactory{
	
	/**
	 * Creates actual components based on specified parameters
	 */
	@BsonCreator
	public Component createComponent(String type,int cost) {
	
		Component createdComponent = null;
		
		//Store refreshes stock with parts of a varying price range.
		//Lemon: 0-150
		//Junkyard: 151 - 300
		//OEM: 301 - 750
		//Sports: 751 - 3000
		//Racing: 3001 - 20000
			
		//TODO: implement cost based stats
		
		//returns Engine with placeholder stats depending on cost
		//quality, value, durability, speed
		
		if(type == "engine" && (cost >= 0 && cost <= 150)) 
			createdComponent = new EngineComponent("Lemon",100, 100, 50);
		else if(type == "engine" && (cost >= 151 && cost <= 300)) 
			createdComponent = new EngineComponent("Junkyard", 500, 100, 100);
		else if(type == "engine" && (cost >= 301 && cost <= 750)) 
			createdComponent = new EngineComponent("OEM", 2500, 100, 200);
		else if(type == "engine" && (cost >= 751 && cost <= 3000)) 
			createdComponent = new EngineComponent("Sports",5000, 100, 300);
		else if(type == "engine" && cost >= 3001) 
			createdComponent = new EngineComponent("Racing", 10000, 100, 400);
		
		//returns Wheel with placeholder stats depending on cost
		//quality, value, durability, braking
		
		if(type == "wheel" && (cost >= 0 && cost <= 150)) 
			createdComponent = new WheelComponent("Lemon",100, 100, 50);
		else if(type == "wheel" && (cost >= 151 && cost <= 300)) 
			createdComponent = new WheelComponent("Junkyard", 500, 100, 100);
		else if(type == "wheel" && (cost >= 301 && cost <= 750)) 
			createdComponent = new WheelComponent("OEM", 2500, 100, 200);
		else if(type == "wheel" && (cost >= 751 && cost <= 3000)) 
			createdComponent = new WheelComponent("Sports",5000, 100, 300);
		else if(type == "wheel" && cost >= 3001) 
			createdComponent = new WheelComponent("Racing", 10000, 100, 400);
		
		//returns Suspension with placeholder stats depending on cost
		//quality, value, durability, handling
		
		if(type == "suspension" && (cost >= 0 && cost <= 150))
			createdComponent = new SuspensionComponent("Lemon",100, 100, 50);
		else if(type == "suspension" && (cost >= 151 && cost <= 300)) 
			createdComponent = new SuspensionComponent("Junkyard", 500, 100, 100);
		else if(type == "suspension" && (cost >= 301 && cost <= 750)) 
			createdComponent = new SuspensionComponent("OEM", 2500, 100, 200);
		else if(type == "suspension" && (cost >= 751 && cost <= 3000)) 
			createdComponent = new SuspensionComponent("Sports",5000, 100, 300);
		else if(type == "suspension" && cost >= 3001) 
			createdComponent = new SuspensionComponent("Racing", 10000, 100, 400);
		
		//returns Chassis with placeholder stats depending on cost
		//quality, value, durability, popularity, accelerationModifier, speedModifier, handlingModifier, brakingModifier
		
		if(type == "chassis" && (cost >= 0 && cost <= 150))
			createdComponent = new ChassisComponent("Lemon",100, 100, 0, 0, 1, 1, 1, 1);
		else if(type == "chassis" && (cost >= 151 && cost <= 300)) 
			createdComponent = new ChassisComponent("Junkyard",500, 100, 0, 2, 2, 2, 2, 2);
		else if(type == "chassis" && (cost >= 301 && cost <= 750)) 
			createdComponent = new ChassisComponent("OEM",2500, 100, 0, 3, 3, 3, 3, 3);
		else if(type == "chassis" && (cost >= 751 && cost <= 3000)) 
			createdComponent = new ChassisComponent("Sports",5000, 100, 0, 4, 4, 4, 4, 4);
		else if(type == "chassis" && cost >= 3001) 
			createdComponent = new ChassisComponent("Racing",10000, 100, 0, 5, 5, 5, 5, 5);
		
		//returns Transmission with placeholder stats depending on cost
		//quality, value, durability, acceleration
		
		if(type == "transmission" && (cost >= 0 && cost <= 150))
			createdComponent = new TransmissionComponent("Lemon",100, 100, 50);
		else if(type == "transmission" && (cost >= 151 && cost <= 300)) 
			createdComponent = new TransmissionComponent("Junkyard", 500, 100, 100);
		else if(type == "transmission" && (cost >= 301 && cost <= 750)) 
			createdComponent = new TransmissionComponent("OEM", 2500, 100, 200);
		else if(type == "transmission" && (cost >= 751 && cost <= 3000)) 
			createdComponent = new TransmissionComponent("Sports",5000, 100, 300);
		else if(type == "transmission" && cost >= 3001) 
			createdComponent = new TransmissionComponent("Racing", 10000, 100, 400);
		
		return createdComponent;
		
	}
}



