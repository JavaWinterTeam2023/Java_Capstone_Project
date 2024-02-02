package energy;

import models.Weather;

public class Storage extends EnergySource{
	public Storage(double maxCapacity, Weather wCond) {
		super(maxCapacity, wCond);
		// TODO Auto-generated constructor stub
		this.name = EnergyType.STORAGE;
	}

	public void updateCapacity() {
		switch(this.weather.currentWeather) {
		case "Cloudy":
			this.efficiency = 1;
			break;
		case "Rainy":
			this.efficiency = 0.7;
			break;
		case "Snowy":
			this.efficiency = 0.5;
			break;
		case "Sunny": 
			this.efficiency = 0.9;
			break;
		default:
			this.efficiency = 1;
			break;
	}
	this.currentCapacity = this.maxCapacity * this.efficiency;
	}
}
