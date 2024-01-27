package energy;

import models.Weather;

public class Solar extends EnergySource{

	public Solar(int capacity, Weather wCond) {
		super(capacity, wCond);
		this.name = EnergyType.SOLAR;
		// TODO Auto-generated constructor stub
	}
	
	public void updateCapacity() {
		switch(this.weather.currentWeather) {
			case "Cloudy":
				this.efficiency = 0.8;
				break;
			case "Rainy":
				this.efficiency = 0.7;
				break;
			case "Snowy":
				this.efficiency = 0.6;
				break;
			default:
				this.efficiency = 1;
				break;
		}
		this.currentCapacity = this.maxCapacity * this.efficiency;
	}
}
