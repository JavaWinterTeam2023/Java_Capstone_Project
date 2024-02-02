package energy;

import models.Weather;

public class Grid extends EnergySource{
	public Grid(int capacity, Weather wCond) {
		super(capacity, wCond);
		this.name = EnergyType.GRID;
		this.currentCapacity = this.maxCapacity;
	}
}
