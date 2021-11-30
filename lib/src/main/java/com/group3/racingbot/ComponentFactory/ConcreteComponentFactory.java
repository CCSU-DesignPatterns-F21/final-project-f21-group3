package com.group3.racingbot.ComponentFactory;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.DBHandler;
import com.group3.racingbot.inventory.filter.Quality;

/**
 * @author Jack Gola Defines the ConcreteComponentFactory used for creating
 *         components
 * @return
 */

public class ConcreteComponentFactory extends ComponentFactory {

	/**
	 * Creates actual components based on specified parameters
	 */
	@BsonCreator
	public Component createComponent(String type, int cost) {
		DBHandler dbh = DBHandler.getInstance();
		Component createdComponent = null;

		// ===== Prices =====

		// Lemon: 0-150
		// Junkyard: 151 - 300
		// OEM: 301 - 750
		// Sports: 751 - 3000
		// Racing: 3001 - 20000

		// ===== Ranges =====

		// CHASSIS Modifiers Range (acceleration, braking, handling, popularity, speed)
		// LEMON: 1.0x - 1.25x
		// JUNKYARD: 1.25x - 1.5x
		// OEM: 1.5x - 2.0x
		// SPORTS: 2.0x - 2.5x
		// RACING: 2.5x - 4.0x

		if (type == "engine" && (cost >= 0 && cost <= 150)) {
			createdComponent = new EngineComponent();
			((EngineComponent) createdComponent).setQuality(Quality.LEMON);
			((EngineComponent) createdComponent).setValue(0 + (int) (Math.random() * ((100 - 0) + 1)));
			((EngineComponent) createdComponent).setWeight(800 + (int) (Math.random() * ((1000 - 800) + 1)));
			((EngineComponent) createdComponent).setSpeed(25 + (int) (Math.random() * ((50 - 25) + 1)));
			((EngineComponent) createdComponent).setMaxDurability(50);
			((EngineComponent) createdComponent).setDurability(25 + (int) (Math.random() * ((50 - 25) + 1)));

			((EngineComponent) createdComponent).setThumbnailURL("https://i.imgur.com/05MDTfJ.gif");
		} else if (type == "engine" && (cost >= 151 && cost <= 300)) {
			createdComponent = new EngineComponent();
			((EngineComponent) createdComponent).setQuality((Quality.JUNKYARD));
			((EngineComponent) createdComponent).setValue(100 + (int) (Math.random() * ((250 - 100) + 1)));
			((EngineComponent) createdComponent).setWeight(600 + (int) (Math.random() * ((800 - 600) + 1)));
			((EngineComponent) createdComponent).setSpeed(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((EngineComponent) createdComponent).setMaxDurability(75);
			((EngineComponent) createdComponent).setDurability(50 + (int) (Math.random() * ((75 - 50) + 1)));

			((EngineComponent) createdComponent).setThumbnailURL("https://i.imgur.com/MvTEeFx.gif");
		}

		else if (type == "engine" && (cost >= 301 && cost <= 750)) {
			createdComponent = new EngineComponent();
			((EngineComponent) createdComponent).setQuality(Quality.OEM);
			((EngineComponent) createdComponent).setValue(250 + (int) (Math.random() * ((500 - 250) + 1)));
			((EngineComponent) createdComponent).setWeight(400 + (int) (Math.random() * ((600 - 400) + 1)));
			((EngineComponent) createdComponent).setSpeed(100 + (int) (Math.random() * ((150 - 100) + 1)));
			((EngineComponent) createdComponent).setMaxDurability(100);
			((EngineComponent) createdComponent).setDurability(75 + (int) (Math.random() * ((100 - 75) + 1)));

			((EngineComponent) createdComponent).setThumbnailURL("https://i.imgur.com/icdEzy8.gif");
		}

		else if (type == "engine" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new EngineComponent();
			((EngineComponent) createdComponent).setQuality(Quality.SPORTS);
			((EngineComponent) createdComponent).setValue(500 + (int) (Math.random() * ((1000 - 500) + 1)));
			((EngineComponent) createdComponent).setWeight(300 + (int) (Math.random() * ((400 - 300) + 1)));
			((EngineComponent) createdComponent).setSpeed(150 + (int) (Math.random() * ((250 - 150) + 1)));
			((EngineComponent) createdComponent).setMaxDurability(150);
			((EngineComponent) createdComponent).setDurability(100 + (int) (Math.random() * ((150 - 100) + 1)));

			((EngineComponent) createdComponent).setThumbnailURL("https://i.imgur.com/2ReaUsE.gif");
		} else if (type == "engine" && cost >= 3001) {
			createdComponent = new EngineComponent();
			((EngineComponent) createdComponent).setQuality(Quality.RACING);
			((EngineComponent) createdComponent).setValue(1000 + (int) (Math.random() * ((4000 - 1000) + 1)));
			((EngineComponent) createdComponent).setWeight(200 + (int) (Math.random() * ((300 - 200) + 1)));
			((EngineComponent) createdComponent).setSpeed(250 + (int) (Math.random() * ((350 - 250) + 1)));
			((EngineComponent) createdComponent).setMaxDurability(200);
			((EngineComponent) createdComponent).setDurability(150 + (int) (Math.random() * ((250 - 150) + 1)));

			((EngineComponent) createdComponent).setThumbnailURL("https://i.imgur.com/RvNggMb.gif");
		}

		// returns Wheel with random stats depending on cost
		// quality, value, durability, braking

		if (type == "wheel" && (cost >= 0 && cost <= 150)) {
			createdComponent = new WheelComponent();
			((WheelComponent) createdComponent).setQuality(Quality.LEMON);
			((WheelComponent) createdComponent).setValue(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((WheelComponent) createdComponent).setWeight(800 + (int) (Math.random() * ((1000 - 800) + 1)));
			((WheelComponent) createdComponent).setBraking(25 + (int) (Math.random() * ((50 - 25) + 1)));
			((WheelComponent) createdComponent).setMaxDurability(50);
			((WheelComponent) createdComponent).setDurability(25 + (int) (Math.random() * ((50 - 25) + 1)));

			((WheelComponent) createdComponent).setThumbnailURL("https://i.imgur.com/EUfjaRO.gif");
		} else if (type == "wheel" && (cost >= 151 && cost <= 300)) {
			createdComponent = new WheelComponent();
			((WheelComponent) createdComponent).setQuality(Quality.JUNKYARD);
			((WheelComponent) createdComponent).setValue(100 + (int) (Math.random() * ((250 - 100) + 1)));
			((WheelComponent) createdComponent).setWeight(600 + (int) (Math.random() * ((800 - 600) + 1)));
			((WheelComponent) createdComponent).setBraking(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((WheelComponent) createdComponent).setMaxDurability(75);
			((WheelComponent) createdComponent).setDurability(50 + (int) (Math.random() * ((75 - 50) + 1)));

			((WheelComponent) createdComponent).setThumbnailURL("https://i.imgur.com/Cj4uzaG.gif");
		}

		else if (type == "wheel" && (cost >= 301 && cost <= 750)) {
			createdComponent = new WheelComponent();
			((WheelComponent) createdComponent).setQuality(Quality.OEM);
			((WheelComponent) createdComponent).setValue(250 + (int) (Math.random() * ((500 - 250) + 1)));
			((WheelComponent) createdComponent).setWeight(400 + (int) (Math.random() * ((600 - 400) + 1)));
			((WheelComponent) createdComponent).setBraking(100 + (int) (Math.random() * ((150 - 100) + 1)));
			((WheelComponent) createdComponent).setMaxDurability(100);
			((WheelComponent) createdComponent).setDurability(75 + (int) (Math.random() * ((100 - 75) + 1)));

			((WheelComponent) createdComponent).setThumbnailURL("https://i.imgur.com/CL9YO3r.gif");
		}

		else if (type == "wheel" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new WheelComponent();
			((WheelComponent) createdComponent).setQuality(Quality.SPORTS);
			((WheelComponent) createdComponent).setValue(500 + (int) (Math.random() * ((1000 - 500) + 1)));
			((WheelComponent) createdComponent).setWeight(300 + (int) (Math.random() * ((400 - 300) + 1)));
			((WheelComponent) createdComponent).setBraking(150 + (int) (Math.random() * ((250 - 150) + 1)));
			((WheelComponent) createdComponent).setMaxDurability(150);
			((WheelComponent) createdComponent).setDurability(100 + (int) (Math.random() * ((150 - 100) + 1)));

			((WheelComponent) createdComponent).setThumbnailURL("https://i.imgur.com/vf8TLae.gif");
		} else if (type == "wheel" && cost >= 3001) {
			createdComponent = new WheelComponent();
			((WheelComponent) createdComponent).setQuality(Quality.RACING);
			((WheelComponent) createdComponent).setValue(1000 + (int) (Math.random() * ((4000 - 1000) + 1)));
			((WheelComponent) createdComponent).setWeight(200 + (int) (Math.random() * ((300 - 200) + 1)));
			((WheelComponent) createdComponent).setBraking(250 + (int) (Math.random() * ((350 - 250) + 1)));
			((WheelComponent) createdComponent).setMaxDurability(200);
			((WheelComponent) createdComponent).setDurability(150 + (int) (Math.random() * ((200 - 150) + 1)));

			((WheelComponent) createdComponent).setThumbnailURL("https://i.imgur.com/PXNATz9.gif");
		}

		// returns Suspension with random stats depending on cost
		// quality, value, durability, handling

		if (type == "suspension" && (cost >= 0 && cost <= 150)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent) createdComponent).setQuality(Quality.LEMON);
			((SuspensionComponent) createdComponent).setValue(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((SuspensionComponent) createdComponent).setWeight(800 + (int) (Math.random() * ((1000 - 800) + 1)));
			((SuspensionComponent) createdComponent).setHandling(25 + (int) (Math.random() * ((50 - 25) + 1)));
			((SuspensionComponent) createdComponent).setMaxDurability(50);
			((SuspensionComponent) createdComponent).setDurability(25 + (int) (Math.random() * ((50 - 25) + 1)));

			((SuspensionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/8CcL7lZ.gif");
		} else if (type == "suspension" && (cost >= 151 && cost <= 300)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent) createdComponent).setQuality(Quality.JUNKYARD);
			((SuspensionComponent) createdComponent).setValue(100 + (int) (Math.random() * ((250 - 100) + 1)));
			((SuspensionComponent) createdComponent).setWeight(600 + (int) (Math.random() * ((800 - 600) + 1)));
			((SuspensionComponent) createdComponent).setHandling(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((SuspensionComponent) createdComponent).setMaxDurability(75);
			((SuspensionComponent) createdComponent).setDurability(50 + (int) (Math.random() * ((75 - 50) + 1)));

			((SuspensionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/7fPDxsL.gif");
		} else if (type == "suspension" && (cost >= 301 && cost <= 750)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent) createdComponent).setQuality(Quality.OEM);
			((SuspensionComponent) createdComponent).setValue(250 + (int) (Math.random() * ((500 - 250) + 1)));
			((SuspensionComponent) createdComponent).setWeight(400 + (int) (Math.random() * ((600 - 400) + 1)));
			((SuspensionComponent) createdComponent).setHandling(100 + (int) (Math.random() * ((150 - 100) + 1)));
			((SuspensionComponent) createdComponent).setMaxDurability(100);
			((SuspensionComponent) createdComponent).setDurability(75 + (int) (Math.random() * ((100 - 75) + 1)));

			((SuspensionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/raznbJH.gif");
		} else if (type == "suspension" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent) createdComponent).setQuality(Quality.SPORTS);
			((SuspensionComponent) createdComponent).setValue(500 + (int) (Math.random() * ((1000 - 500) + 1)));
			((SuspensionComponent) createdComponent).setWeight(300 + (int) (Math.random() * ((400 - 300) + 1)));
			((SuspensionComponent) createdComponent).setHandling(150 + (int) (Math.random() * ((250 - 150) + 1)));
			((SuspensionComponent) createdComponent).setMaxDurability(150);
			((SuspensionComponent) createdComponent).setDurability(100 + (int) (Math.random() * ((150 - 100) + 1)));

			((SuspensionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/4cM3ye9.gif");
		} else if (type == "suspension" && cost >= 3001) {
			createdComponent = new SuspensionComponent();
			((SuspensionComponent) createdComponent).setQuality(Quality.RACING);
			((SuspensionComponent) createdComponent).setValue(1000 + (int) (Math.random() * ((4000 - 1000) + 1)));
			((SuspensionComponent) createdComponent).setWeight(200 + (int) (Math.random() * ((300 - 200) + 1)));
			((SuspensionComponent) createdComponent).setHandling(250 + (int) (Math.random() * ((350 - 250) + 1)));
			((SuspensionComponent) createdComponent).setMaxDurability(200);
			((SuspensionComponent) createdComponent).setDurability(150 + (int) (Math.random() * ((200 - 150) + 1)));

			((SuspensionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/trs3NPs.gif");
		}

		// returns Transmission with random stats depending on cost
		// quality, value, durability, acceleration

		if (type == "transmission" && (cost >= 0 && cost <= 150)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent) createdComponent).setQuality(Quality.LEMON);
			((TransmissionComponent) createdComponent).setValue(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((TransmissionComponent) createdComponent).setWeight(800 + (int) (Math.random() * ((1000 - 800) + 1)));
			((TransmissionComponent) createdComponent).setAcceleration(25 + (int) (Math.random() * ((50 - 25) + 1)));
			((TransmissionComponent) createdComponent).setMaxDurability(50);
			((TransmissionComponent) createdComponent).setDurability(25 + (int) (Math.random() * ((50 - 25) + 1)));

			((TransmissionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/8CcL7lZ.gif");
		} else if (type == "transmission" && (cost >= 151 && cost <= 300)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent) createdComponent).setQuality(Quality.JUNKYARD);
			((TransmissionComponent) createdComponent).setValue(100 + (int) (Math.random() * ((250 - 100) + 1)));
			((TransmissionComponent) createdComponent).setWeight(600 + (int) (Math.random() * ((800 - 600) + 1)));
			((TransmissionComponent) createdComponent).setAcceleration(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((TransmissionComponent) createdComponent).setMaxDurability(75);
			((TransmissionComponent) createdComponent).setDurability(50 + (int) (Math.random() * ((75 - 50) + 1)));

			((TransmissionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/7fPDxsL.gif");
		} else if (type == "transmission" && (cost >= 301 && cost <= 750)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent) createdComponent).setQuality(Quality.OEM);
			((TransmissionComponent) createdComponent).setValue(250 + (int) (Math.random() * ((500 - 250) + 1)));
			((TransmissionComponent) createdComponent).setWeight(400 + (int) (Math.random() * ((600 - 400) + 1)));
			((TransmissionComponent) createdComponent).setAcceleration(100 + (int) (Math.random() * ((150 - 100) + 1)));
			((TransmissionComponent) createdComponent).setMaxDurability(100);
			((TransmissionComponent) createdComponent).setDurability(75 + (int) (Math.random() * ((100 - 75) + 1)));

			((TransmissionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/raznbJH.gif");
		} else if (type == "transmission" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent) createdComponent).setQuality(Quality.SPORTS);
			((TransmissionComponent) createdComponent).setValue(500 + (int) (Math.random() * ((1000 - 500) + 1)));
			((TransmissionComponent) createdComponent).setWeight(300 + (int) (Math.random() * ((400 - 300) + 1)));
			((TransmissionComponent) createdComponent).setAcceleration(150 + (int) (Math.random() * ((250 - 150) + 1)));
			((TransmissionComponent) createdComponent).setMaxDurability(150);
			((TransmissionComponent) createdComponent).setDurability(100 + (int) (Math.random() * ((150 - 100) + 1)));

			((TransmissionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/4cM3ye9.gif");
		} else if (type == "transmission" && cost >= 3001) {
			createdComponent = new TransmissionComponent();
			((TransmissionComponent) createdComponent).setQuality(Quality.RACING);
			((TransmissionComponent) createdComponent).setValue(1000 + (int) (Math.random() * ((4000 - 1000) + 1)));
			((TransmissionComponent) createdComponent).setWeight(200 + (int) (Math.random() * ((300 - 200) + 1)));
			((TransmissionComponent) createdComponent).setAcceleration(250 + (int) (Math.random() * ((350 - 250) + 1)));
			((TransmissionComponent) createdComponent).setMaxDurability(200);
			((TransmissionComponent) createdComponent).setDurability(150 + (int) (Math.random() * ((200 - 150) + 1)));

			((TransmissionComponent) createdComponent).setThumbnailURL("https://i.imgur.com/trs3NPs.gif");
		}

		// returns Chassis with random stats depending on cost
		// quality, value, durability, popularity, accelerationModifier, speedModifier,
		// handlingModifier, brakingModifier

		if (type == "chassis" && (cost >= 0 && cost <= 150)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent) createdComponent).setQuality(Quality.LEMON);
			((ChassisComponent) createdComponent).setValue(50 + (int) (Math.random() * ((100 - 50) + 1)));
			((ChassisComponent) createdComponent).setWeight(800 + (int) (Math.random() * ((1000 - 800) + 1)));
			((ChassisComponent) createdComponent).setDurability(25 + (int) (Math.random() * ((50 - 25) + 1)));
			((ChassisComponent) createdComponent).setMaxDurability(50);

			((ChassisComponent) createdComponent).setAccelerationModifier(1.0 + (Math.random() * ((1.25 - 1.0) + 1)));
			((ChassisComponent) createdComponent).setBrakingModifier(1.0 + (Math.random() * ((1.25 - 1.0) + 1)));
			((ChassisComponent) createdComponent).setHandlingModifier(1.0 + (Math.random() * ((1.25 - 1.0) + 1)));
			((ChassisComponent) createdComponent).setPopularityModifier(1.0 + (Math.random() * ((1.25 - 1.0) + 1)));
			((ChassisComponent) createdComponent).setSpeedModifier(1.0 + (Math.random() * ((1.25 - 1.0) + 1)));

			((ChassisComponent) createdComponent).setThumbnailURL("https://i.imgur.com/zzj97p0.gif");
		} else if (type == "chassis" && (cost >= 151 && cost <= 300)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent) createdComponent).setQuality(Quality.JUNKYARD);
			((ChassisComponent) createdComponent).setValue(100 + (int) (Math.random() * ((250 - 100) + 1)));
			((ChassisComponent) createdComponent).setWeight(600 + (int) (Math.random() * ((800 - 600) + 1)));
			((ChassisComponent) createdComponent).setDurability(50 + (int) (Math.random() * ((75 - 50) + 1)));
			((ChassisComponent) createdComponent).setMaxDurability(75);

			((ChassisComponent) createdComponent).setAccelerationModifier(1.5 + (Math.random() * ((1.5 - 1.25) + 1)));
			((ChassisComponent) createdComponent).setBrakingModifier(1.5 + (Math.random() * ((1.5 - 1.25) + 1)));
			((ChassisComponent) createdComponent).setHandlingModifier(1.5 + (Math.random() * ((1.5 - 1.25) + 1)));
			((ChassisComponent) createdComponent).setPopularityModifier(1.5 + (Math.random() * ((1.5 - 1.25) + 1)));
			((ChassisComponent) createdComponent).setSpeedModifier(1.5 + (Math.random() * ((1.5 - 1.25) + 1)));

			((ChassisComponent) createdComponent).setThumbnailURL("https://i.imgur.com/aXBH7lN.gif");
		} else if (type == "chassis" && (cost >= 301 && cost <= 750)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent) createdComponent).setQuality(Quality.OEM);
			((ChassisComponent) createdComponent).setValue(250 + (int) (Math.random() * ((500 - 250) + 1)));
			((ChassisComponent) createdComponent).setWeight(400 + (int) (Math.random() * ((600 - 400) + 1)));
			((ChassisComponent) createdComponent).setDurability(75 + (int) (Math.random() * ((100 - 75) + 1)));
			((ChassisComponent) createdComponent).setMaxDurability(100);

			((ChassisComponent) createdComponent).setAccelerationModifier(1.5 + (Math.random() * ((2.0 - 1.5) + 1)));
			((ChassisComponent) createdComponent).setBrakingModifier(1.5 + (Math.random() * ((2.0 - 1.5) + 1)));
			((ChassisComponent) createdComponent).setHandlingModifier(1.5 + (Math.random() * ((2.0 - 1.5) + 1)));
			((ChassisComponent) createdComponent).setPopularityModifier(1.5 + (Math.random() * ((2.0 - 1.5) + 1)));
			((ChassisComponent) createdComponent).setSpeedModifier(1.5 + (Math.random() * ((2.0 - 1.5) + 1)));

			((ChassisComponent) createdComponent).setThumbnailURL("https://i.imgur.com/xdX3WUG.gif");
		} else if (type == "chassis" && (cost >= 751 && cost <= 3000)) {
			createdComponent = new ChassisComponent();
			((ChassisComponent) createdComponent).setQuality(Quality.SPORTS);
			((ChassisComponent) createdComponent).setValue(500 + (int) (Math.random() * ((1000 - 500) + 1)));
			((ChassisComponent) createdComponent).setWeight(300 + (int) (Math.random() * ((400 - 300) + 1)));
			((ChassisComponent) createdComponent).setDurability(75 + (int) (Math.random() * ((150 - 75) + 1)));
			((ChassisComponent) createdComponent).setMaxDurability(150);

			((ChassisComponent) createdComponent).setAccelerationModifier(2.0 + (Math.random() * ((2.5 - 2.0) + 1)));
			((ChassisComponent) createdComponent).setBrakingModifier(2.0 + (Math.random() * ((2.5 - 2.0) + 1)));
			((ChassisComponent) createdComponent).setHandlingModifier(2.0 + (Math.random() * ((2.5 - 2.0) + 1)));
			((ChassisComponent) createdComponent).setPopularityModifier(2.0 + (Math.random() * ((2.5 - 2.0) + 1)));
			((ChassisComponent) createdComponent).setSpeedModifier(2.0 + (Math.random() * ((2.5 - 2.0) + 1)));

			((ChassisComponent) createdComponent).setThumbnailURL("https://i.imgur.com/DSEcXJL.gif");
		} else if (type == "chassis" && cost >= 3001) {
			createdComponent = new ChassisComponent();
			((ChassisComponent) createdComponent).setQuality(Quality.RACING);
			((ChassisComponent) createdComponent).setValue(1000 + (int) (Math.random() * ((4000 - 1000) + 1)));
			((ChassisComponent) createdComponent).setWeight(200 + (int) (Math.random() * ((300 - 200) + 1)));
			((ChassisComponent) createdComponent).setDurability(150 + (int) (Math.random() * ((200 - 150) + 1)));
			((ChassisComponent) createdComponent).setMaxDurability(200);

			((ChassisComponent) createdComponent).setAccelerationModifier(2.5 + (Math.random() * ((4.0 - 2.5) + 1)));
			((ChassisComponent) createdComponent).setBrakingModifier(2.5 + (Math.random() * ((4.0 - 2.5) + 1)));
			((ChassisComponent) createdComponent).setHandlingModifier(2.5 + (Math.random() * ((4.0 - 2.5) + 1)));
			((ChassisComponent) createdComponent).setPopularityModifier(2.5 + (Math.random() * ((4.0 - 2.5) + 1)));
			((ChassisComponent) createdComponent).setSpeedModifier(2.5 + (Math.random() * ((4.0 - 2.5) + 1)));

			((ChassisComponent) createdComponent).setThumbnailURL("https://i.imgur.com/LDCvKV2.gif");
		}

		createdComponent.setId(dbh.generateId(6)); // Give the component a random id.
		return createdComponent;

	}

	@Override
	public String toString() {
		return "This is the concrete component factory";
	}
}
