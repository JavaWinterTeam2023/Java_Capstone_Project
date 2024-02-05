package services;

import java.util.ArrayList;
import energy.EnergySource;
import models.Weather;

public class EnergyManagement implements Runnable{
	private ArrayList<EnergySource> energySource = new ArrayList<>();
	private int totalCapacity;
	public int getTotalCapacity() {
		return totalCapacity;
	}

	private Weather weather;
	
	public Weather getWeather() {
		return weather;
	}

	public EnergyManagement (ArrayList<EnergySource> eSource, Weather wCond) {
		this.weather = wCond;
		for (EnergySource i : eSource) {
			this.energySource.add(i);
		}
	}
	
	public int updateTotalCapacity() {
		this.totalCapacity = 0;
		for (EnergySource i : energySource) {
			i.updateCapacity();
			this.totalCapacity += i.currentCapacity;
		}
		return totalCapacity;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			synchronized(weather) {
				weather.setWeatherCondition();
				updateTotalCapacity();
	//			System.out.println("Total: " + this.totalCapacity);
			}
			try {
                // Sleep for 3 seconds before the next update
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}
}
