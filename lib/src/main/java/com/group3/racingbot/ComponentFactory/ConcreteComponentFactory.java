package com.group3.racingbot.ComponentFactory;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.inventory.filter.Quality;

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
		
		if(type == "engine" && (cost >= 0 && cost <= 150)) {
			createdComponent = new EngineComponent();
			((EngineComponent)createdComponent).setQuality(Quality.LEMON);
			((EngineComponent)createdComponent).setValue(100);
			((EngineComponent)createdComponent).setWeight(800);
			((EngineComponent)createdComponent).setSpeed(25);
			((EngineComponent)createdComponent).setMaxDurability(50);
			((EngineComponent)createdComponent).setDurability(50);
			((EngineComponent)createdComponent).setThumbnailURL("https://i.imgur.com/05MDTfJ.gif");
		}
		else if(type == "engine" && (cost >= 151 && cost <= 300)) {
			createdComponent = new EngineComponent();
			((EngineComponent)createdComponent).setQuality((Quality.JUNKYARD));
			((EngineComponent)createdComponent).setValue(250);
			((EngineComponent)createdComponent).setWeight(600);
			((EngineComponent)createdComponent).setSpeed(50);
			((EngineComponent)createdComponent).setMaxDurability(75);
			((EngineComponent)createdComponent).setDurability(75);
			((EngineComponent)createdComponent).setThumbnailURL("https://i.imgur.com/MvTEeFx.gif");
		}
			
		else if(type == "engine" && (cost >= 301 && cost <= 750)) {
			createdComponent = new EngineComponent();
			((EngineComponent)createdComponent).setQuality(Quality.OEM);
			((EngineComponent)createdComponent).setValue(500);
			((EngineComponent)createdComponent).setWeight(400);
			((EngineComponent)createdComponent).setSpeed(100);
			((EngineComponent)createdComponent).setMaxDurability(100);
			((EngineComponent)createdComponent).setDurability(100);
			((EngineComponent)createdComponent).setThumbnailURL("https://i.imgur.com/icdEzy8.gif");
		}
			
		else if(type == "engine" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new EngineComponent();
			((EngineComponent)createdComponent).setQuality(Quality.SPORTS);
			((EngineComponent)createdComponent).setValue(1000);
			((EngineComponent)createdComponent).setWeight(300);
			((EngineComponent)createdComponent).setSpeed(150);
			((EngineComponent)createdComponent).setMaxDurability(150);
			((EngineComponent)createdComponent).setDurability(150);
			((EngineComponent)createdComponent).setThumbnailURL("https://i.imgur.com/2ReaUsE.gif");
		}
		else if(type == "engine" && cost >= 3001) {
			createdComponent = new EngineComponent();
			((EngineComponent)createdComponent).setQuality(Quality.RACING);
			((EngineComponent)createdComponent).setValue(4000);
			((EngineComponent)createdComponent).setWeight(200);
			((EngineComponent)createdComponent).setSpeed(250);
			((EngineComponent)createdComponent).setMaxDurability(200);
			((EngineComponent)createdComponent).setDurability(200);
			((EngineComponent)createdComponent).setThumbnailURL("https://i.imgur.com/RvNggMb.gif");
		}
			
		
		//returns Wheel with placeholder stats depending on cost
		//quality, value, durability, braking
		
		if(type == "wheel" && (cost >= 0 && cost <= 150)) {
			createdComponent = new WheelComponent();
			((WheelComponent)createdComponent).setQuality(Quality.LEMON);
			((WheelComponent)createdComponent).setValue(100);
			((WheelComponent)createdComponent).setWeight(800);
			((WheelComponent)createdComponent).setBraking(25);
			((WheelComponent)createdComponent).setMaxDurability(50);
			((WheelComponent)createdComponent).setDurability(50);
			((WheelComponent)createdComponent).setThumbnailURL("https://i.imgur.com/EUfjaRO.gif");
		}
		else if(type == "wheel" && (cost >= 151 && cost <= 300)) {
			createdComponent = new WheelComponent();
			((WheelComponent)createdComponent).setQuality(Quality.JUNKYARD);
			((WheelComponent)createdComponent).setValue(250);
			((WheelComponent)createdComponent).setWeight(600);
			((WheelComponent)createdComponent).setBraking(50);
			((WheelComponent)createdComponent).setMaxDurability(75);
			((WheelComponent)createdComponent).setDurability(75);
			((WheelComponent)createdComponent).setThumbnailURL("https://i.imgur.com/Cj4uzaG.gif");
		}
			
		else if(type == "wheel" && (cost >= 301 && cost <= 750)) {
			createdComponent = new WheelComponent();
			((WheelComponent)createdComponent).setQuality(Quality.OEM);
			((WheelComponent)createdComponent).setValue(500);
			((WheelComponent)createdComponent).setWeight(400);
			((WheelComponent)createdComponent).setBraking(100);
			((WheelComponent)createdComponent).setMaxDurability(100);
			((WheelComponent)createdComponent).setDurability(100);
			((WheelComponent)createdComponent).setThumbnailURL("https://i.imgur.com/CL9YO3r.gif");
		}
			
		else if(type == "wheel" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new WheelComponent();
			((WheelComponent)createdComponent).setQuality(Quality.SPORTS);
			((WheelComponent)createdComponent).setValue(1000);
			((WheelComponent)createdComponent).setWeight(300);
			((WheelComponent)createdComponent).setBraking(150);
			((WheelComponent)createdComponent).setMaxDurability(150);
			((WheelComponent)createdComponent).setDurability(150);
			((WheelComponent)createdComponent).setThumbnailURL("https://i.imgur.com/vf8TLae.gif");
		}
		else if(type == "wheel" && cost >= 3001) {
			createdComponent = new WheelComponent();
			((WheelComponent)createdComponent).setQuality(Quality.RACING);
			((WheelComponent)createdComponent).setValue(4000);
			((WheelComponent)createdComponent).setWeight(200);
			((WheelComponent)createdComponent).setBraking(250);
			((WheelComponent)createdComponent).setMaxDurability(200);
			((WheelComponent)createdComponent).setDurability(200);
			((WheelComponent)createdComponent).setThumbnailURL("https://i.imgur.com/PXNATz9.gif");
		}
			
		
		//returns Suspension with placeholder stats depending on cost
		//quality, value, durability, handling
		
		if(type == "suspension" && (cost >= 0 && cost <= 150)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent)createdComponent).setQuality(Quality.LEMON);
			((SuspensionComponent)createdComponent).setValue(100);
			((SuspensionComponent)createdComponent).setWeight(800);
			((SuspensionComponent)createdComponent).setHandling(25);
			((SuspensionComponent)createdComponent).setMaxDurability(50);
			((SuspensionComponent)createdComponent).setDurability(50);
			((SuspensionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/8CcL7lZ.gif");
		}
		else if(type == "suspension" && (cost >= 151 && cost <= 300)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent)createdComponent).setQuality(Quality.JUNKYARD);
			((SuspensionComponent)createdComponent).setValue(250);
			((SuspensionComponent)createdComponent).setWeight(600);
			((SuspensionComponent)createdComponent).setHandling(50);
			((SuspensionComponent)createdComponent).setMaxDurability(75);
			((SuspensionComponent)createdComponent).setDurability(75);
			((SuspensionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/7fPDxsL.gif");
		}
		else if(type == "suspension" && (cost >= 301 && cost <= 750)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent)createdComponent).setQuality(Quality.OEM);
			((SuspensionComponent)createdComponent).setValue(500);
			((SuspensionComponent)createdComponent).setWeight(400);
			((SuspensionComponent)createdComponent).setHandling(100);
			((SuspensionComponent)createdComponent).setMaxDurability(100);
			((SuspensionComponent)createdComponent).setDurability(100);
			((SuspensionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/raznbJH.gif");
		}
		else if(type == "suspension" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent)createdComponent).setQuality(Quality.SPORTS);
			((SuspensionComponent)createdComponent).setValue(1000);
			((SuspensionComponent)createdComponent).setWeight(300);
			((SuspensionComponent)createdComponent).setHandling(150);
			((SuspensionComponent)createdComponent).setMaxDurability(150);
			((SuspensionComponent)createdComponent).setDurability(150);
			((SuspensionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/4cM3ye9.gif");
		}
		else if(type == "suspension" && cost >= 3001) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent)createdComponent).setQuality(Quality.RACING);
			((SuspensionComponent)createdComponent).setValue(4000);
			((SuspensionComponent)createdComponent).setWeight(200);
			((SuspensionComponent)createdComponent).setHandling(250);
			((SuspensionComponent)createdComponent).setMaxDurability(200);
			((SuspensionComponent)createdComponent).setDurability(200);
			((SuspensionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/trs3NPs.gif");
		}
		
		//returns Chassis with placeholder stats depending on cost
		//quality, value, durability, popularity, accelerationModifier, speedModifier, handlingModifier, brakingModifier
		
		if(type == "chassis" && (cost >= 0 && cost <= 150)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent)createdComponent).setQuality(Quality.LEMON);
			((ChassisComponent)createdComponent).setValue(100);
			((ChassisComponent)createdComponent).setWeight(800);
			((ChassisComponent)createdComponent).setDurability(50);
			((ChassisComponent)createdComponent).setMaxDurability(50);
			((ChassisComponent)createdComponent).setAccelerationModifier(1.0);
			((ChassisComponent)createdComponent).setBrakingModifier(1.0);
			((ChassisComponent)createdComponent).setHandlingModifier(1.0);
			((ChassisComponent)createdComponent).setPopularityModifier(1.0);
			((ChassisComponent)createdComponent).setSpeedModifier(1.0);
			((ChassisComponent)createdComponent).setThumbnailURL("https://i.imgur.com/zzj97p0.gif");
		}
		else if(type == "chassis" && (cost >= 151 && cost <= 300)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent)createdComponent).setQuality(Quality.JUNKYARD);
			((ChassisComponent)createdComponent).setValue(250);
			((ChassisComponent)createdComponent).setWeight(600);
			((ChassisComponent)createdComponent).setDurability(75);
			((ChassisComponent)createdComponent).setMaxDurability(75);
			((ChassisComponent)createdComponent).setAccelerationModifier(2.0);
			((ChassisComponent)createdComponent).setBrakingModifier(2.0);
			((ChassisComponent)createdComponent).setHandlingModifier(2.0);
			((ChassisComponent)createdComponent).setPopularityModifier(2.0);
			((ChassisComponent)createdComponent).setSpeedModifier(2.0);
			((ChassisComponent)createdComponent).setThumbnailURL("https://i.imgur.com/aXBH7lN.gif");
		}
		else if(type == "chassis" && (cost >= 301 && cost <= 750)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent)createdComponent).setQuality(Quality.OEM);
			((ChassisComponent)createdComponent).setValue(500);
			((ChassisComponent)createdComponent).setWeight(400);
			((ChassisComponent)createdComponent).setDurability(100);
			((ChassisComponent)createdComponent).setMaxDurability(100);
			((ChassisComponent)createdComponent).setAccelerationModifier(3.0);
			((ChassisComponent)createdComponent).setBrakingModifier(3.0);
			((ChassisComponent)createdComponent).setHandlingModifier(3.0);
			((ChassisComponent)createdComponent).setPopularityModifier(3.0);
			((ChassisComponent)createdComponent).setSpeedModifier(3.0);
			((ChassisComponent)createdComponent).setThumbnailURL("https://i.imgur.com/xdX3WUG.gif");
		}
		else if(type == "chassis" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent)createdComponent).setQuality(Quality.SPORTS);
			((ChassisComponent)createdComponent).setValue(1000);
			((ChassisComponent)createdComponent).setWeight(300);
			((ChassisComponent)createdComponent).setDurability(150);
			((ChassisComponent)createdComponent).setMaxDurability(150);
			((ChassisComponent)createdComponent).setAccelerationModifier(4.0);
			((ChassisComponent)createdComponent).setBrakingModifier(4.0);
			((ChassisComponent)createdComponent).setHandlingModifier(4.0);
			((ChassisComponent)createdComponent).setPopularityModifier(4.0);
			((ChassisComponent)createdComponent).setSpeedModifier(4.0);
			((ChassisComponent)createdComponent).setThumbnailURL("https://i.imgur.com/DSEcXJL.gif");
		}
		else if(type == "chassis" && cost >= 3001) {
			createdComponent = new ChassisComponent();
			((ChassisComponent)createdComponent).setQuality(Quality.RACING);
			((ChassisComponent)createdComponent).setValue(4000);
			((ChassisComponent)createdComponent).setWeight(200);
			((ChassisComponent)createdComponent).setDurability(200);
			((ChassisComponent)createdComponent).setMaxDurability(200);
			((ChassisComponent)createdComponent).setAccelerationModifier(5.0);
			((ChassisComponent)createdComponent).setBrakingModifier(5.0);
			((ChassisComponent)createdComponent).setHandlingModifier(5.0);
			((ChassisComponent)createdComponent).setPopularityModifier(5.0);
			((ChassisComponent)createdComponent).setSpeedModifier(5.0);
			((ChassisComponent)createdComponent).setThumbnailURL("https://i.imgur.com/LDCvKV2.gif");
		}
		
		//returns Transmission with placeholder stats depending on cost
		//quality, value, durability, acceleration
		
		if(type == "transmission" && (cost >= 0 && cost <= 150)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent)createdComponent).setQuality(Quality.LEMON);
			((TransmissionComponent)createdComponent).setValue(100);
			((TransmissionComponent)createdComponent).setWeight(800);
			((TransmissionComponent)createdComponent).setAcceleration(25);
			((TransmissionComponent)createdComponent).setDurability(50);
			((TransmissionComponent)createdComponent).setMaxDurability(50);
			((TransmissionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/8CcL7lZ.gif");
		}
		else if(type == "transmission" && (cost >= 151 && cost <= 300)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent)createdComponent).setQuality(Quality.JUNKYARD);
			((TransmissionComponent)createdComponent).setValue(250);
			((TransmissionComponent)createdComponent).setWeight(600);
			((TransmissionComponent)createdComponent).setAcceleration(50);
			((TransmissionComponent)createdComponent).setDurability(75);
			((TransmissionComponent)createdComponent).setMaxDurability(75);
			((TransmissionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/7fPDxsL.gif");
		}
		else if(type == "transmission" && (cost >= 301 && cost <= 750)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent)createdComponent).setQuality(Quality.OEM);
			((TransmissionComponent)createdComponent).setValue(500);
			((TransmissionComponent)createdComponent).setWeight(400);
			((TransmissionComponent)createdComponent).setAcceleration(100);
			((TransmissionComponent)createdComponent).setDurability(100);
			((TransmissionComponent)createdComponent).setMaxDurability(100);
			((TransmissionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/raznbJH.gif");
		}
		else if(type == "transmission" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent)createdComponent).setQuality(Quality.SPORTS);
			((TransmissionComponent)createdComponent).setValue(1000);
			((TransmissionComponent)createdComponent).setWeight(300);
			((TransmissionComponent)createdComponent).setAcceleration(150);
			((TransmissionComponent)createdComponent).setDurability(150);
			((TransmissionComponent)createdComponent).setMaxDurability(150);
			((TransmissionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/4cM3ye9.gif");
		}
		else if(type == "transmission" && cost >= 3001) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent)createdComponent).setQuality(Quality.RACING);
			((TransmissionComponent)createdComponent).setValue(4000);
			((TransmissionComponent)createdComponent).setWeight(200);
			((TransmissionComponent)createdComponent).setAcceleration(250);
			((TransmissionComponent)createdComponent).setDurability(200);
			((TransmissionComponent)createdComponent).setMaxDurability(200);
			((TransmissionComponent)createdComponent).setThumbnailURL("https://i.imgur.com/trs3NPs.gif");
		}
		return createdComponent;
		
		//return createdComponent;
		
	}

	@Override
	public String toString() {
		return "This is the concrete component factory";
	}
}


