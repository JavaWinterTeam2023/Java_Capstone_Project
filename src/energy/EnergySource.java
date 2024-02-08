package energy;

import java.lang.Runnable;

import models.Weather;

public class EnergySource implements Runnable{

	public enum EnergyType {
		SOLAR,
		STORAGE,
		GRID
	}
	
	
	public EnergyType name; 
	public double maxCapacity;
	public double currentCapacity;
	public Weather weather;
	protected double efficiency;
	
	public EnergySource(double maxCapacity, Weather wCond) {
		this.maxCapacity = maxCapacity;
		this.weather = wCond;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i = 0;
		while(i>10) {
			i++;
			this.updateCapacity();
//			System.out.println("Iteration: " + i + " Weather: " + this.weather.currentWeather + " Energy: " + this.name + " with capacity: " + this.maxCapacity);		
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double d) {
		this.efficiency = d;
	}	
	
	public void updateCapacity() {
		
	}
}
